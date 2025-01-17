package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.CreateEmployeeRequest;
import com.hahn.erms.application.dto.EmployeeDTO;
import com.hahn.erms.application.dto.SearchRequest;
import com.hahn.erms.application.dto.UpdateEmployeeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeDTO createEmployee(CreateEmployeeRequest request);
    EmployeeDTO updateEmployee(UpdateEmployeeRequest request);
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO getEmployeeByEmployeeId(String employeeId);
    void deleteEmployee(Long id);
    Page<EmployeeDTO> searchEmployees(SearchRequest searchRequest, Pageable pageable);
    Page<EmployeeDTO> getEmployeesByDepartment(Long departmentId, Pageable pageable);
}
