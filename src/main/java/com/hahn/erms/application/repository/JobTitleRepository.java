package com.hahn.erms.application.repository;

import com.hahn.erms.application.entity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
}
