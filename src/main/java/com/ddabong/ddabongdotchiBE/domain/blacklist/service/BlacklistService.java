package com.ddabong.ddabongdotchiBE.domain.blacklist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.request.BlacklistCreateRequest;
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

	public BlacklistCreateResponse createBlacklist(User user, BlacklistCreateRequest request) {

		if (user.getUsername().equals(request.target()))
			throw new BlacklistExceptionHandler(BlacklistErrorCode.BLACKLIST_ERROR);

		final User target = userRepository.findByUsername(request.target())
			.orElseThrow(() -> new UserExceptionHandler(UserErrorCode.USER_NOT_FOUND));

		if (blacklistRepository.existsByUserAndTarget(user, target))
			throw new BlacklistExceptionHandler(BlacklistErrorCode.BLACKLIST_ALREADY_REPORTED);

		Blacklist blacklist = blacklistRepository.save(request.toEntity(user, target));
		return BlacklistCreateResponse.from(blacklist);
	}

	public void deleteBlacklist(User user, Long targetId) {
		final Blacklist blacklist = blacklistRepository.findByUserAndTargetId(user, targetId)
			.orElseThrow(() -> new BlacklistExceptionHandler(BlacklistErrorCode.BLACKLIST_NOT_FOUND));

		blacklistRepository.delete(blacklist);
	}
}
