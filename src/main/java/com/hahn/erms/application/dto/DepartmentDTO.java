package com.hahn.erms.application.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String name;
    private EmployeeDTO manager;
    private Integer employeeCount;
}