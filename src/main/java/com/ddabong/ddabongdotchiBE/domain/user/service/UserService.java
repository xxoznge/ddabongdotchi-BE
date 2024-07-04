package com.ddabong.ddabongdotchiBE.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddabong.ddabongdotchiBE.domain.s3.util.S3Service;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.PasswordUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.UserJoinRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.UserUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.ReissueResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserJoinResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserUpdateResponse;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
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

	public UserJoinResponse join(UserJoinRequest request, MultipartFile file) {
		String imageUrl = s3Service.uploadImage(file);
		final User user = request.toEntity(passwordEncoder.encode(request.password()));
		user.setImageUrl(imageUrl);
		userRepository.save(user);
		return UserJoinResponse.from(user);
	}

	public ReissueResponse reissueToken(String refreshToken) {
		log.info("[*] Generate new token pair with " + refreshToken);
		return ReissueResponse.from(jwtUtil.reissueToken(refreshToken));
	}

	public void deactivate(User user) {
		user.deactivate();
	}

	public void updatePassword(User user, PasswordUpdateRequest request) {
		String encodedNewPassword = passwordEncoder.encode(request.password());
		user.updatePassword(encodedNewPassword);
	}

	public UserUpdateResponse updateMyUser(User user, UserUpdateRequest request, MultipartFile file) {
		String imageUrl = s3Service.uploadImage(file);
		user.update(request.nickname(), request.description(), imageUrl);
		return UserUpdateResponse.from(user);
	}

}
