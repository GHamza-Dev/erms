package com.hahn.erms.application.controller;

import com.hahn.erms.application.dto.CreateEmployeeRequest;
import com.hahn.erms.application.dto.EmployeeDTO;
import com.hahn.erms.application.dto.UpdateEmployeeRequest;
import com.hahn.erms.application.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.employees.base.url}")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid CreateEmployeeRequest employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid UpdateEmployeeRequest employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("find-by-employee-id/{employeeId}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmployeeId(employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
