package com.ddabong.ddabongdotchiBE.domain.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddabong.ddabongdotchiBE.domain.s3.util.S3Service;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.PasswordUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.UserJoinRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.UserUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserJoinResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserUpdateResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3Service s3Service;

	public UserJoinResponse join(UserJoinRequest request, MultipartFile file) {
		String imageUrl = s3Service.uploadImage(file);
		final User user = request.toEntity(passwordEncoder.encode(request.password()));
		user.setImageUrl(imageUrl);
		userRepository.save(user);
		return UserJoinResponse.from(user);
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
