package com.beratoztas.dto.response;

import java.math.BigDecimal;

import com.beratoztas.entities.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse extends BaseDto {

    private String productName;
    private BigDecimal price;
    private Integer quantity;

    public OrderItemResponse(OrderItem item) {
        this.setId(item.getId());
        this.setCreatedTime(item.getCreatedTime());
        this.productName = item.getProduct().getName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }
}
