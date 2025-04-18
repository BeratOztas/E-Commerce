package com.beratoztas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beratoztas.entities.Address;
import com.beratoztas.entities.Cart;
import com.beratoztas.entities.CartItem;
import com.beratoztas.entities.Order;
import com.beratoztas.entities.OrderItem;
import com.beratoztas.entities.User;
import com.beratoztas.enums.CartStatus;
import com.beratoztas.enums.OrderStatus;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.message.OrderProducer;
import com.beratoztas.message.event.OrderCreatedEvent;
import com.beratoztas.message.event.OrderStatusChangedEvent;
import com.beratoztas.repository.AddressRepository;
import com.beratoztas.repository.CartItemRepository;
import com.beratoztas.repository.CartRepository;
import com.beratoztas.repository.OrderRepository;
import com.beratoztas.repository.UserRepository;
import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IOrderService;
import com.beratoztas.utils.PageUtil;

import io.micrometer.common.util.StringUtils;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

	private OrderRepository orderRepository;
	private CartRepository cartRepository;
	private UserRepository userRepository;
	private AddressRepository addressRepository;

	private OrderProducer orderProducer;

	public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
			CartItemRepository cartItemRepository, UserRepository userRepository, AddressRepository addressRepository,
			OrderProducer orderProducer) {
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.orderProducer = orderProducer;
	}

	@Override
	public OrderResponse getOrderById(Long orderId, JwtUserDetails userDetails) {
		Order order = getOrder(orderId);

		User userOrder = order.getUser();
		// If user not equals or not admin
		if (!userOrder.getId().equals(userDetails.getId())
				&& userDetails.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			throw new BaseException(new ErrorMessage(MessageType.ACCESS_DENIED, "User Id : " + userDetails.getId()));
		}

		return new OrderResponse(order);

	}

	@Override
	public PageResponse<OrderResponse> getMyOrders(JwtUserDetails userDetails, RestPageRequest request) {
		User user = getUser(userDetails.getId());

		Pageable pageable = PageUtil.toPageable(request);
		Page<Order> page = orderRepository.findByUser(user,pageable);
		
		if (page.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.ORDERS_NOT_FOUND, "You have no orders."));
	    }

		List<OrderResponse> content = page.getContent().stream()
	            .map(OrderResponse::new)
	            .collect(Collectors.toList());

	    return PageUtil.toPageResponse(page, content);
	}

	@Override
	public PageResponse<OrderResponse> getAllOrders(RestPageRequest request) {
		Pageable pageable = PageUtil.toPageable(request);
		Page<Order> page = orderRepository.findAll(pageable);

		if (page.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ORDERS_NOT_FOUND, "No orders found."));
		}

		List<OrderResponse> content = page.getContent().stream().map(OrderResponse::new).collect(Collectors.toList());

		return PageUtil.toPageResponse(page, content);

	}

	@Override
	public OrderResponse createOrderFromCart(JwtUserDetails userDetails, CreateOrderRequest request) {
		User user = getUser(userDetails.getId());
		Cart cart = getActiveCart(user);
		validateCart(cart);

		Order order = prepareOrderFromCart(cart, user);

		handleAddressForOrder(user, request, order);

		orderRepository.save(order);

		finalizeCart(cart, user);

		OrderResponse orderResponse = new OrderResponse(order);
		orderProducer.sendOrderCreatedEvent(new OrderCreatedEvent(orderResponse));

		return orderResponse;
	}

	@Override
	public OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
		Order order = getOrder(orderId);

		OrderStatus oldStatus = order.getStatus();
		OrderStatus newStatus = request.getStatus();

		order.setStatus(newStatus);
		orderRepository.save(order);

		OrderStatusChangedEvent event = createOrderStatusChangedEvent(order, oldStatus);

		orderProducer.sendOrderStatusChangedEvent(event);

		return new OrderResponse(order);
	}

	private OrderStatusChangedEvent createOrderStatusChangedEvent(Order order, OrderStatus oldStatus) {
		return new OrderStatusChangedEvent(order.getId(), order.getUser().getId(), order.getUser().getEmail(),
				oldStatus, order.getStatus());
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND, "User Id : " + userId)));
	}

	private Order getOrder(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.ORDER_NOT_FOUND, "Order Id : " + orderId)));
	}

	private Cart getActiveCart(User user) {
		return cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, "No active cart for user")));
	}

	private void validateCart(Cart cart) {
		if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CART_NULL_OR_EMPTY, ""));
		}
	}

	private Order prepareOrderFromCart(Cart cart, User user) {
		Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.PENDING);

		BigDecimal total = BigDecimal.ZERO;
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
			orderItem.setOrder(order);

			total = total.add(orderItem.getPrice());
			orderItems.add(orderItem);
		}

		order.setCart(cart);
		order.setTotalPrice(total);
		order.setOrderItems(orderItems);
		return order;
	}

	private void handleAddressForOrder(User user, CreateOrderRequest request, Order order) {
		boolean hasAddressId = request.getAddressId() != null;
		boolean hasNewAddress = Stream
				.of(request.getCity(), request.getDistrict(), request.getNeighborhood(), request.getStreet())
				.anyMatch(StringUtils::isNotBlank);

		// If neither addressId nor address fields are provided throw error
		if (!hasNewAddress && !hasAddressId) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_REQUIRED, "Address info is required."));
		}
		if (hasNewAddress) {
			validateAddressFields(request);

			Optional<Address> existingAddressOpt = addressRepository
					.findByUserAndCityAndDistrictAndNeighborhoodAndStreet(user, request.getCity().trim(),
							request.getDistrict().trim(), request.getNeighborhood().trim(), request.getStreet().trim());

			Address addressToUse;

			if (existingAddressOpt.isPresent()) {
				addressToUse = existingAddressOpt.get(); // eski adresi kullan
			} else {
				addressToUse = buildAddressFromRequest(request, user);
				addressRepository.save(addressToUse);
			}

			order.setAddress(addressToUse);
		} else {
			Address existingAddress = addressRepository.findById(request.getAddressId())
					.orElseThrow(() -> new BaseException(
							new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, "Address Id : " + request.getAddressId())));

			// Check if the address belongs to the authenticated user
			if (!existingAddress.getUser().getId().equals(user.getId())) {
				throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED, "You don't own this address."));
			}

			order.setAddress(existingAddress);
		}
	}

	// Ensure all address fields are filled
	private void validateAddressFields(CreateOrderRequest request) {
		if (Stream.of(request.getCity(), request.getDistrict(), request.getNeighborhood(), request.getStreet())
				.anyMatch(StringUtils::isBlank)) {
			throw new BaseException(
					new ErrorMessage(MessageType.INVALID_ADDRESS, "All address fields must be filled."));
		}
	}

	// Build an Address entity from the request
	private Address buildAddressFromRequest(CreateOrderRequest request, User user) {
		Address address = new Address();
		address.setUser(user);
		address.setCity(request.getCity());
		address.setDistrict(request.getDistrict());
		address.setNeighborhood(request.getNeighborhood());
		address.setStreet(request.getStreet());
		return address;
	}

	private void finalizeCart(Cart cart, User user) {

		cart.setStatus(CartStatus.COMPLETED);
		cartRepository.save(cart);
		cartRepository.flush();

		Cart newCart = new Cart();
		newCart.setUser(user);
		newCart.setStatus(CartStatus.ACTIVE);
		cartRepository.save(newCart);
	}

}
