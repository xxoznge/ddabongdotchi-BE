package com.ddabong.ddabongdotchiBE.domain.user.jwt.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserErrorCode;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserExceptionHandler(UserErrorCode.USER_NOT_FOUND));

		log.info("[*] User found : " + user.getUsername());

		return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getRoleType().toString(),
			user.getUserStatus());
	}
}
