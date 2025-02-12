package com.hahn.erms.application.repository;

import com.hahn.erms.application.entity.Department;
import com.hahn.erms.application.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findById(Long id);
}
