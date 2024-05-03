package com.ddabong.ddabongdotchiBE.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ddabong.ddabongdotchiBE.domain.auth.dto.JoinUserRequest;
import com.ddabong.ddabongdotchiBE.domain.auth.dto.JoinUserResponse;
import com.ddabong.ddabongdotchiBE.domain.auth.entity.User;
import com.ddabong.ddabongdotchiBE.domain.auth.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public JoinUserResponse join(JoinUserRequest joinUserRequest) {
		String encodedPw = passwordEncoder.encode(joinUserRequest.password());
		User newUser = userRepository.save(joinUserRequest.toEntity(encodedPw));
		return JoinUserResponse.from(newUser);
	}
}
