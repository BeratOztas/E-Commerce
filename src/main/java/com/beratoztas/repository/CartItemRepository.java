package com.beratoztas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beratoztas.entities.CartItem;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, Long>{

}
