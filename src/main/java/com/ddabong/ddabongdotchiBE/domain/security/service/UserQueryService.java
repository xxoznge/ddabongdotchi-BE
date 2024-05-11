package com.ddabong.ddabongdotchiBE.domain.security.service;

import static com.ddabong.ddabongdotchiBE.domain.security.exception.UserErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.security.dto.UserDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	public User findByUserName(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));
	}

	public UserDetailGetResponse getUser(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));

		return UserDetailGetResponse.from(user);
	}

	public Boolean checkUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Boolean checkNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
}
