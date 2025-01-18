package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.EmployeeDTO;
import com.hahn.erms.application.entity.Department;
import com.hahn.erms.application.entity.Employee;
import com.hahn.erms.security.dao.User;
import com.hahn.erms.security.service.AppAuthorizationService;
import com.hahn.erms.security.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeAuthorizationService extends AppAuthorizationService {

    private final EmployeeService employeeService;

    public EmployeeAuthorizationService(CustomUserDetailsService userDetailsService, EmployeeService employeeService) {
        super(userDetailsService);
        this.employeeService = employeeService;
    }

    public boolean isEmployeeInManagersDepartment(Long employeeId) {
        log.info("Checking if employee is a manager of department");

        if (employeeId == null) {
            log.warn("Employee id is null, returning false");
            return false;
        }

        Employee authenticatedEmployee = getCurrentAuthenticatedEmployee();
        Department authDepartment = authenticatedEmployee.getDepartment();

        if (authDepartment == null) {
            log.warn("Authenticated employee is not associated with any department");
            return false;
        }

        log.info("Authenticated employee department code is: {}", authDepartment.getCode());

        EmployeeDTO targetEmployee = employeeService.getEmployeeById(employeeId);

        if (targetEmployee == null) {
            log.warn("No employee found with id {}", employeeId);
            return false;
        }

        log.info("Employee found with id {}", employeeId);

        if (targetEmployee.getDepartment() == null) {
            log.warn("No department found of employee with id {}", employeeId);
            return false;
        }

        boolean sameDepartment = targetEmployee.getDepartment().getId().equals(authDepartment.getId());

        log.info("Employee and manager are {}", sameDepartment ? "in the same department" : "in the departments");

        return sameDepartment;
    }

    public Employee getCurrentAuthenticatedEmployee() {
        User user = this.getCurrentUser();
        Employee currentEmployee = user.getEmployee();

        if (currentEmployee == null) {
            log.warn("Current authenticated user is not associated with any employee");
            throw new AuthorizationDeniedException("Current authenticated user is not associated with any employee");
        }

        return currentEmployee;
    }
}