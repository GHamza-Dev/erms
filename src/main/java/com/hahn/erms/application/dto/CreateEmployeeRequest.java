package com.hahn.erms.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateEmployeeRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    private String phone;

    @NotNull
    private LocalDate hireDate;

    @NotNull
    private String contractType;

    @NotNull
    private String employmentStatus;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long jobTitleId;

    @NotBlank
    private String address;
}