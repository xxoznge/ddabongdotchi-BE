package com.ddabong.ddabongdotchiBE.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	boolean existsByTarget(String target);
}
