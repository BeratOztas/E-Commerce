package com.beratoztas.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.beratoztas.entities.Order;
import com.beratoztas.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends BaseDto {

    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderItemResponse> items;
    private UserResponse user;

    public OrderResponse(Order order) {
        this.setId(order.getId());
        this.setCreatedTime(order.getCreatedTime());
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.items = order.getOrderItems().stream()
                          .map(orderItem -> new OrderItemResponse(orderItem))
                          .collect(Collectors.toList());
        this.user=new UserResponse(order.getUser());
    }
}
