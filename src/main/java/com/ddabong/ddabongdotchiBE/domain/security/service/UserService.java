package com.ddabong.ddabongdotchiBE.domain.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.security.dto.JoinUserRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.JoinUserResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public JoinUserResponse join(JoinUserRequest request) {

		String encodedPw = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPw);

		return JoinUserResponse.from(userRepository.save(newUser));
	}
}
