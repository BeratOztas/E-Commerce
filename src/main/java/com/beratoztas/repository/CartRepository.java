package com.beratoztas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beratoztas.entities.Cart;
import com.beratoztas.entities.User;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart>  findByUser(User user);
}
