package com.ddabong.ddabongdotchiBE.domain.blacklist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistGetResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.repository.BlacklistRepository;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BlacklistQueryService {

	private final BlacklistRepository blacklistRepository;

	/* 차단 목록 조회 */
	public List<BlacklistGetResponse> getBlacklist(User user) {
		return blacklistRepository.findByUser_UserStatusAndUser(UserStatus.ACTIVE, user)
			.stream()
			.map(BlacklistGetResponse::from)
			.toList();
	}
}
