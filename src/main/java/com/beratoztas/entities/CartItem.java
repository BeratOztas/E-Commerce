package com.beratoztas.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {

	@Column(name = "quantity")
	private Integer quantity;
	
	//Bir sepet silinirse ona ait sepetItemlerini de sil.
	@ManyToOne
	@JoinColumn(name = "cart_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Cart cart;
	
	//Bir ürün silinirse ona ait sepetItemlerini de sil.
	@ManyToOne
	@JoinColumn(name = "product_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;
	
}
