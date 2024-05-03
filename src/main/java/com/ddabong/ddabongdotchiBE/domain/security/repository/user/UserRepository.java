package com.ddabong.ddabongdotchiBE.domain.security.repository.user;

import java.util.Optional;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

public interface UserRepository {

	Optional<User> findByUsername(String username);

	User save(User user);
}
