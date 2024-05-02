package com.ddabong.ddabongdotchiBE.domain.auth.jwt.userdetails;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ddabong.ddabongdotchiBE.domain.auth.entity.User;
import com.ddabong.ddabongdotchiBE.domain.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public PrincipalDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userEntity = userRepository.findByUsername(username);
		if (userEntity.isPresent()) {
			User user = userEntity.get();
			return new PrincipalDetails(user.getUsername(),user.getPassword(), user.getRole());
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}
