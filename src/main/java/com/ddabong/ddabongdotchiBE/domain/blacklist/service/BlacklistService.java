package com.ddabong.ddabongdotchiBE.domain.blacklist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;
import com.ddabong.ddabongdotchiBE.domain.blacklist.exception.BlacklistErrorCode;
import com.ddabong.ddabongdotchiBE.domain.blacklist.exception.BlacklistExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.blacklist.repository.BlacklistRepository;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserErrorCode;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BlacklistService {

	private final BlacklistRepository blacklistRepository;
	private final UserRepository userRepository;

	/* 차단하기 */
	public BlacklistCreateResponse createBlacklist(User user, Long targetId) {

		// 사용자가 자신의 ID를 차단하려는 경우 예외 발생
		if (user.getId().equals(targetId)) {
			throw new BlacklistExceptionHandler(BlacklistErrorCode.BLACKLIST_ERROR);
		}

		// 대상 사용자를 userRepository 조회
		User target = userRepository.findById(targetId)
			.orElseThrow(() -> new UserExceptionHandler(UserErrorCode.USER_NOT_FOUND));

		// 이미 차단한 사용자인지 확인
		if (blacklistRepository.existsByUserAndTarget(user, target)) {
			throw new BlacklistExceptionHandler(BlacklistErrorCode.BLACKLIST_ALREADY_REPORTED);
		}

		// 차단 정보 저장
		Blacklist blacklist = blacklistRepository.save(
			Blacklist.builder()
				.user(user)   // 차단 요청을 한 사용자
				.target(target) // 차단 대상 사용자
				.build()
		);

		return BlacklistCreateResponse.from(blacklist);
	}

	/* 차단 해제 */
	public void deleteBlacklist(User user, Long targetId) {
		final Blacklist blacklist = blacklistRepository.findByUserAndTargetId(user, targetId)
			.orElseThrow(() -> new BlacklistExceptionHandler(BlacklistErrorCode.BLACKLIST_NOT_FOUND));

		blacklistRepository.delete(blacklist);
	}
}
