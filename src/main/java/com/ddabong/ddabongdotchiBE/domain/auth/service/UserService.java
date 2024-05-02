package com.ddabong.ddabongdotchiBE.domain.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ddabong.ddabongdotchiBE.domain.auth.entity.User;
import com.ddabong.ddabongdotchiBE.domain.auth.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService{

	private final UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public String register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		userRepository.save(user);
		return "redirect:/loginForm";
	}

}
