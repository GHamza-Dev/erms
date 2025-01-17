package com.hahn.erms.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private String employmentStatus;
    private DepartmentDTO department;
    private JobTitleDTO jobTitle;
    private String addresses;
}