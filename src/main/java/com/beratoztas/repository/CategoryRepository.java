package com.beratoztas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beratoztas.entities.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long>{

	public Category findByName(String name);

}
