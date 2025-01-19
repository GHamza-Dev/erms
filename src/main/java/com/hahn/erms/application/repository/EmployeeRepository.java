package com.hahn.erms.application.repository;

import com.hahn.erms.application.dto.SearchRequest;
import com.hahn.erms.application.dto.projection.EmployeeProjection;
import com.hahn.erms.application.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    Employee findByEmployeeId(String employeeId);


    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN e.department d " +
            "LEFT JOIN e.jobTitle j " +
            "LEFT JOIN e.contract c " +
            "WHERE (:#{#search.name} IS NULL OR LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :#{#search.name}, '%'))) " +
            "AND (:#{#search.employeeId} IS NULL OR LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :#{#search.employeeId}, '%'))) " +
            "AND (:#{#search.departmentId} IS NULL OR e.department.id = :#{#search.departmentId}) " +
            "AND (:#{#search.jobTitleId} IS NULL OR e.jobTitle.id = :#{#search.jobTitleId}) " +
            "AND (:#{#search.employmentStatus} IS NULL OR LOWER(e.employmentStatus) LIKE LOWER(CONCAT('%', :#{#search.employmentStatus}, '%'))) " +
            "AND (:#{#search.hireDateFrom} IS NULL OR c.hireDate >= :#{#search.hireDateFrom}) " +
            "AND (:#{#search.hireDateTo} IS NULL OR c.hireDate <= :#{#search.hireDateTo}) " +
            "AND (:#{#search.contractType} IS NULL OR LOWER(c.contractType) LIKE LOWER(CONCAT('%', :#{#search.contractType}, '%')))")
    Page<EmployeeProjection> searchEmployees(@Param("search") SearchRequest search, Pageable pageable);
}
