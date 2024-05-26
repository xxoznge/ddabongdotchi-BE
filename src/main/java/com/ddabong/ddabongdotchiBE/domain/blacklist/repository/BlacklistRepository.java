package com.ddabong.ddabongdotchiBE.domain.blacklist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
	Optional<Blacklist> findById(Long Id);
}
