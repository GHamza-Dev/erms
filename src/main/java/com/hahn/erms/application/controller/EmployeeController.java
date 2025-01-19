package com.hahn.erms.application.controller;

import com.hahn.erms.application.dto.*;
import com.hahn.erms.application.dto.projection.EmployeeProjection;
import com.hahn.erms.application.service.EmployeeService;
import com.hahn.erms.security.anotations.CanUpdateEmployee;
import com.hahn.erms.security.anotations.CanViewEmployee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.employees.base.url}")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid CreateEmployeeRequest employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @CanUpdateEmployee
    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid UpdateEmployeeRequest employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employee));
    }

    @CanViewEmployee
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("find-by-employee-id/{employeeId}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PagedResponse<EmployeeProjection>> searchEmployees(@RequestBody @Valid SearchRequest searchRequest) {
        return ResponseEntity.ok(employeeService.searchEmployees(searchRequest));
    }
}
