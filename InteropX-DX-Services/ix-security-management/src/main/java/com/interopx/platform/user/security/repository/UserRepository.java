package com.interopx.platform.user.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.user.security.entities.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByuserId(Long Id);
	
	List<User> findByAccountEnabled(Boolean accountEnabled);
	
	@Modifying
	@Query("update User user set user.accountEnabled = ?1 where user.userId = ?2")
	int deactivateUser(Boolean value, Long userId);

}
