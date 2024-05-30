package com.ddabong.ddabongdotchiBE.domain.blacklist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistGetResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.repository.BlacklistRepository;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BlacklistQueryService {

	private final BlacklistRepository blacklistRepository;

	public List<BlacklistGetResponse> getBlacklist(User user) {
		return blacklistRepository.findByUser(user)
			.stream()
			.map(BlacklistGetResponse::from)
			.toList();
	}
}
