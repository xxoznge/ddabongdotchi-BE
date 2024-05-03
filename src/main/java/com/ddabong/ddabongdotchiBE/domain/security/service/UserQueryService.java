package com.ddabong.ddabongdotchiBE.domain.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserErrorCode;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.security.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	public User findByUserName(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(UserErrorCode.USER_NOT_FOUND));
	}
}
