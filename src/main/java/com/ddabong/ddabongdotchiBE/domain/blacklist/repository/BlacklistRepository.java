package com.ddabong.ddabongdotchiBE.domain.blacklist.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
  
	List<Blacklist> findByUser(User user);

	Boolean existsByUserAndTarget(User user, User target);
}
