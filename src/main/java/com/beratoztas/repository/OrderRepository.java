package com.beratoztas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beratoztas.entities.Order;
import com.beratoztas.entities.User;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(User user);

	Page<Order> findByUser(User user, Pageable pageable);

}
