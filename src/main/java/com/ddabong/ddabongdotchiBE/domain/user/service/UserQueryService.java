package com.ddabong.ddabongdotchiBE.domain.user.service;

import static com.ddabong.ddabongdotchiBE.domain.user.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.user.dto.response.MyCardGetResponse;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	public static List<MyCardGetResponse> getMyCard(User user) {
		return user.getCards()
			.stream()
			.map(MyCardGetResponse::from)
			.toList();
	}

	public Boolean checkUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Boolean checkNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}

	public User findByUserName(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(USER_NOT_FOUND));
	}
}
