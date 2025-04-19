package com.beratoztas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beratoztas.dto.request.AddCartItemRequest;
import com.beratoztas.dto.request.UpdateCartItemRequest;
import com.beratoztas.dto.response.CartResponse;
import com.beratoztas.entities.Cart;
import com.beratoztas.entities.CartItem;
import com.beratoztas.entities.Product;
import com.beratoztas.entities.User;
import com.beratoztas.enums.CartStatus;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.CartItemRepository;
import com.beratoztas.repository.CartRepository;
import com.beratoztas.repository.ProductRepository;
import com.beratoztas.repository.UserRepository;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.ICartService;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

	private UserRepository userRepository;
	private ProductRepository productRepository;
	private CartRepository cartRepository;
	private CartItemRepository cartItemRepository;

	public CartServiceImpl(UserRepository userRepository, ProductRepository productRepository,
			CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
	}

	private Cart getOrCreateCart(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND, "User Id : " + userId)));

		return cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE).orElseGet(() -> {
			Cart savedCart = new Cart();
			savedCart.setUser(user);
			savedCart.setStatus(CartStatus.ACTIVE);
			return cartRepository.save(savedCart);
		});

	}

	private User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND, "User Id : " + userId)));
	}

	private void checkIfUser(JwtUserDetails userDetails) {
		boolean isUser = userDetails.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

		if (!isUser) {
			throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED, "Only users can perform this action."));
		}
	}

	@Override
	public CartResponse getMyCart(JwtUserDetails userDetails) {
		checkIfUser(userDetails);
		Cart cart = getOrCreateCart(userDetails.getId());
		return new CartResponse(cart);
	}

	@Override
	public CartResponse addItemToCart(JwtUserDetails userDetails, AddCartItemRequest request) {
		checkIfUser(userDetails);
		Cart cart = getOrCreateCart(userDetails.getId());
		Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new BaseException(
				new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, "Product Id : " + request.getProductId())));

		Optional<CartItem> existingItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getId().equals(product.getId())).findFirst();

		if (existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQuantity(item.getQuantity() + request.getQuantity());
			cartItemRepository.save(item);
		} else {
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(request.getQuantity());
			cartItemRepository.save(newItem);
			cart.getCartItems().add(newItem);
			cartRepository.save(cart);
		}

		Cart updatedCart = cartRepository.findById(cart.getId()).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, "Cart ID: " + cart.getId())));

		return new CartResponse(updatedCart);
	}

	@Override
	public CartResponse updateCartItemQuantity(JwtUserDetails userDetails, Long cartItemId,
			UpdateCartItemRequest request) {
		checkIfUser(userDetails);
		User user = getUserById(userDetails.getId());
		CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new BaseException(
				new ErrorMessage(MessageType.CART_ITEM_NOT_FOUND, "CartItem Id : " + cartItemId)));

		User cartUser = item.getCart().getUser();

		if (!cartUser.getId().equals(user.getId())) {
			throw new BaseException(
					new ErrorMessage(MessageType.UNAUTHORIZED, "You are not allowed to update this cart item."));
		}

		int updatedQuantity = item.getQuantity() + request.getQuantity();

		if (updatedQuantity < 1) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_QUANTITY, "Quantity must be at least 1."));
		}

		item.setQuantity(updatedQuantity);

		cartItemRepository.save(item);

		Cart updatedCart = cartRepository.findById(item.getCart().getId()).orElseThrow(() -> new BaseException(
				new ErrorMessage(MessageType.CART_NOT_FOUND, "Cart ID: " + item.getCart().getId())));

		return new CartResponse(updatedCart);
	}

	@Override
	public void removeCartItem(JwtUserDetails userDetails, Long cartItemId) {
		checkIfUser(userDetails);
		User user = getUserById(userDetails.getId());
		CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new BaseException(
				new ErrorMessage(MessageType.CART_ITEM_NOT_FOUND, "CartItem Id : " + cartItemId)));

		User cartUser = item.getCart().getUser();

		if (!cartUser.getId().equals(user.getId())) {
			throw new BaseException(
					new ErrorMessage(MessageType.UNAUTHORIZED, "You are not allowed to remove this cart item."));
		}

		cartItemRepository.delete(item);

	}

	@Override
	public void clearCart(JwtUserDetails userDetails) {
		checkIfUser(userDetails);
		Cart cart = getOrCreateCart(userDetails.getId());
		cart.getCartItems().clear();
		cartRepository.save(cart);
	}

}
