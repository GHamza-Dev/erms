package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.CreateEmployeeRequest;
import com.hahn.erms.application.dto.EmployeeDTO;
import com.hahn.erms.application.dto.SearchRequest;
import com.hahn.erms.application.dto.UpdateEmployeeRequest;
import com.hahn.erms.application.entity.Employee;
import com.hahn.erms.application.mapper.EmployeeMapper;
import com.hahn.erms.application.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(CreateEmployeeRequest request) {

        log.info("Creating new employee from request: {}", request);

        validateCreateRequest(request);

        Employee employee = employeeMapper.mapToCreateEmployee(request);

        employee.setEmployeeId(generateEmployeeId());

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeDTO.toDto(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(UpdateEmployeeRequest request) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployeeByEmployeeId(String employeeId) {
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {

    }

    @Override
    public Page<EmployeeDTO> searchEmployees(SearchRequest searchRequest, Pageable pageable) {
        return null;
    }

    @Override
    public Page<EmployeeDTO> getEmployeesByDepartment(Long departmentId, Pageable pageable) {
        return null;
    }


    private void validateCreateRequest(CreateEmployeeRequest request) {
        log.info("Validating create employee request");
        // Check for existing email
        if (employeeRepository.existsByEmail(request.getEmail())) {
            log.info("Email already exists");
            // TODO: to be refactored to custom exception
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        log.info("Validation passed");
    }

    private String generateEmployeeId() {
        // TODO: generate human readable id
        return "EMP_" + System.currentTimeMillis();
    }

    private void updateEmployeeFromRequest(Employee employee, UpdateEmployeeRequest request) {
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }
        if (request.getEmploymentStatus() != null) {
            employee.setEmployeeId(request.getEmploymentStatus());
        }
    }
}