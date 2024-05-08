package com.ddabong.ddabongdotchiBE.domain.security.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByNickname(String nickname);
}
