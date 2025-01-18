package com.hahn.erms.application.repository;

import com.hahn.erms.application.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    Employee findByEmail(String email);
    Employee findByEmployeeId(String employeeId);
}
