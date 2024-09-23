package com.ddabong.ddabongdotchiBE.domain.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

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
	public Optional<User> findById(Long id) {
		return userJpaRepository.findById(id);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userJpaRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByNickname(String nickname) {
		return userJpaRepository.existsByNickname(nickname);
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(user);
	}
}
