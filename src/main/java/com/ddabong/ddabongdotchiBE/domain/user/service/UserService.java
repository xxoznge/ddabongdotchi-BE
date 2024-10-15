package com.ddabong.ddabongdotchiBE.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.s3.S3Service;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.PasswordUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.UserJoinRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.UserUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.ReissueResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserJoinResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserUpdateResponse;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserErrorCode;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3Service s3Service;
	private final JwtUtil jwtUtil;

	public UserJoinResponse join(UserJoinRequest request) {
		final User user = request.toEntity(passwordEncoder.encode(request.password()));
		userRepository.save(user);
		return UserJoinResponse.from(user);
	}

	public ReissueResponse reissueToken(String refreshToken) {
		log.info("[*] Generate new token pair with " + refreshToken);
		return ReissueResponse.from(jwtUtil.reissueToken(refreshToken));
	}

	public void updatePassword(User user, PasswordUpdateRequest request) {
		String encodedNewPassword = passwordEncoder.encode(request.password());
		user.updatePassword(encodedNewPassword);
	}

	public UserUpdateResponse updateMyUser(User user, UserUpdateRequest request) {
		user.update(request.nickname(), request.description(), request.imageUrl());
		return UserUpdateResponse.from(user);
	}

	public void deactivate(User user) {
		// 이미 탈퇴한 사용자일 경우 예외를 던짐
		if (user.getUserStatus() == UserStatus.INACTIVE) {
			throw new UserExceptionHandler(UserErrorCode.USER_ALREADY_INACTIVE);
		}
		// 탈퇴 처리
		user.deactivate();
		userRepository.save(user);
	}
}
