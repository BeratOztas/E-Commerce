package com.beratoztas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beratoztas.entities.Address;
import com.beratoztas.entities.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findByUserAndCityAndDistrictAndNeighborhoodAndStreet(
		    User user,
		    String city,
		    String district,
		    String neighborhood,
		    String street
		);
}
