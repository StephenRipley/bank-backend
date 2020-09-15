package com.fdm.Bank.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.fdm.Bank.Models.User;

public interface UserDao extends JpaRepository<User, Long>{

	Optional<User> findByEmail(@Param("email") String email);
	
	Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}

