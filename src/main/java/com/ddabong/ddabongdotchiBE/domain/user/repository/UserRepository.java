package com.ddabong.ddabongdotchiBE.domain.user.repository;

import java.util.Optional;

import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

public interface UserRepository {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByNickname(String nickname);

	User save(User user);
}
