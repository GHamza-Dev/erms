package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.*;
import com.hahn.erms.application.dto.projection.EmployeeProjection;
import com.hahn.erms.application.entity.Employee;
import com.hahn.erms.application.mapper.RequestEntityMapper;
import com.hahn.erms.application.repository.EmployeeRepository;
import com.hahn.erms.common.exception.BusinessException;
import com.hahn.erms.common.exception.EmployeeNotFoundException;
import com.hahn.erms.security.enums.Role;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RequestEntityMapper requestEntityMapper;
    private final EmployeeAuthorizationService employeeAuthorizationService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               RequestEntityMapper requestEntityMapper,
                               EmployeeAuthorizationService employeeAuthorizationService) {
        this.employeeRepository = employeeRepository;
        this.requestEntityMapper = requestEntityMapper;
        this.employeeAuthorizationService = employeeAuthorizationService;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(CreateEmployeeRequest request) {

        log.info("Creating new employee from request: {}", request);

        validateCreateRequest(request);

        Employee employee = requestEntityMapper.mapToCreateEmployee(request);

        employee.setEmployeeId(generateEmployeeId());

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeDTO.toDto(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(UpdateEmployeeRequest request) {
        log.info("Updating employee from request: {}", request);
        Employee employee = employeeRepository.findById(request.getId()).orElseThrow(EmployeeNotFoundException::new);

        List<String> fieldsToUpdate = requestEntityMapper.mapToUpdateEmployee(employee, request);

        employeeAuthorizationService.verifyFieldsAccess(Set.copyOf(fieldsToUpdate));

        Employee updatedEmployee = employeeRepository.save(employee);

        log.info("Employee updated successfully");
        return EmployeeDTO.toDto(updatedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        return EmployeeDTO.toDto(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException();
        }

        return EmployeeDTO.toDto(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<EmployeeDTO> getEmployeesByDepartment(Long departmentId, Pageable pageable) {
        return null;
    }


    private void validateCreateRequest(CreateEmployeeRequest request) {
        log.info("Validating create employee request");
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("ERR_EMAIL_ALREADY_EXIST", HttpStatus.BAD_REQUEST);
        }

        log.info("Validation passed");
    }

    private String generateEmployeeId() {
        // TODO: generate human readable id
        return "EMP_" + System.currentTimeMillis();
    }

    @Override
    public PagedResponse<EmployeeProjection> searchEmployees(SearchRequest request) {
        log.info("Searching employees with criteria: {}", request);

        List<String> roles = employeeAuthorizationService.getAuthorities();

        if (hasOnlyManagerRole(roles)) {
            Employee employee = employeeAuthorizationService.getCurrentAuthenticatedEmployee();
            if (employee != null && employee.getDepartment() != null) {
                request.setDepartmentId(employee.getDepartment().getId());
            }
        }

        Sort sort = Sort.by(
                request.getSortDirection().equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC, request.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort
        );

        Page<EmployeeProjection> page = employeeRepository.searchEmployees(request, pageable);
        return new PagedResponse<>(page);
    }


    private boolean hasOnlyManagerRole(List<String> roles) {
        if (roles == null) {
            log.warn("Current authenticated user have no roles!");
            return false;
        }

        if (roles.size() > 1) {
            return false;
        }

        return roles.contains(Role.ROLE_MANAGER.name());
    }
}