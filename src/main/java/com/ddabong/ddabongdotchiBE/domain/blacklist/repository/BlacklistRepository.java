package com.ddabong.ddabongdotchiBE.domain.blacklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

	// 특정 사용자가 차단한 사용자 목록을 가져오는 메소드
	List<Blacklist> findByUser(User user);

	List<Blacklist> findByUser_UserStatusAndUser(UserStatus userStatus, User user);

	Boolean existsByUserAndTarget(User user, User target);

	Optional<Blacklist> findByUserAndTargetUsername(User user, String targetUsername);
}
