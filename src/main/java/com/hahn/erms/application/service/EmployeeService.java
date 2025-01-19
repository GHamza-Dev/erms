package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.*;
import com.hahn.erms.application.dto.projection.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeDTO createEmployee(CreateEmployeeRequest request);
    EmployeeDTO updateEmployee(UpdateEmployeeRequest request);
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO getEmployeeByEmployeeId(String employeeId);
    void deleteEmployee(Long id);
    PagedResponse<EmployeeProjection> searchEmployees(SearchRequest searchRequest);
    Page<EmployeeDTO> getEmployeesByDepartment(Long departmentId, Pageable pageable);
}
