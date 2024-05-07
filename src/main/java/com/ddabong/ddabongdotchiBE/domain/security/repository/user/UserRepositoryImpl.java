package com.ddabong.ddabongdotchiBE.domain.security.repository.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		return userJpaRepository.findByUsername(username);
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(user);
	}
}