package com.ddabong.ddabongdotchiBE.domain.security.service;

import static com.ddabong.ddabongdotchiBE.domain.security.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.security.dto.response.MyCardGetResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	public static List<MyCardGetResponse> getMyCard(User user) {
		return user.getCards().stream()
			.map(MyCardGetResponse::from)
			.toList();
	}

	public User findByUserName(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));
	}

	public Boolean checkUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Boolean checkNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
}
