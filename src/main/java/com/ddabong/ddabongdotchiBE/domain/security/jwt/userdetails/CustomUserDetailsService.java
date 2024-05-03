package com.ddabong.ddabongdotchiBE.domain.security.jwt.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserErrorCode;
import com.ddabong.ddabongdotchiBE.domain.security.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.security.repository.user.UserRepository;

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

		return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getRoleType().toString());
	}

	public User userDetailsToUser(UserDetails userDetails) {
		return userRepository.findByUsername(userDetails.getUsername())
			.orElseThrow(() -> new UserExceptionHandler(UserErrorCode.USER_NOT_FOUND));
	}
}
