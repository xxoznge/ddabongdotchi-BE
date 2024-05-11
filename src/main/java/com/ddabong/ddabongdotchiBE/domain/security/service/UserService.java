package com.ddabong.ddabongdotchiBE.domain.security.service;

import static com.ddabong.ddabongdotchiBE.domain.security.exception.UserErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.security.dto.request.PasswordUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.UserJoinRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.UserUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserJoinResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserUpdateResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserJoinResponse join(UserJoinRequest request) {
		final User user = userRepository.save(
			request.toEntity(passwordEncoder.encode(request.password())));
		return UserJoinResponse.from(user);
	}

	public void deactivate(String username) {
		final User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));

		user.deactivate();
	}

	public void updatePassword(String username, PasswordUpdateRequest request) {
		final User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));

		String encodedNewPassword = passwordEncoder.encode(request.password());
		user.updatePassword(encodedNewPassword);
	}

	public UserUpdateResponse updateMyUser(String username, UserUpdateRequest request) {
		final User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));

		user.update(request.nickname(), request.description());
		return UserUpdateResponse.from(user);
	}

}
