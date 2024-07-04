package com.ddabong.ddabongdotchiBE.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

public interface ReportRepository extends JpaRepository<Report, Long> {

	Boolean existsByUserAndTarget(User user, User target);
}
