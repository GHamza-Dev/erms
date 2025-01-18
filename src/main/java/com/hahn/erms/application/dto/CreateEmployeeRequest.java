package com.hahn.erms.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @NotBlank
    @Pattern(regexp = "^(ACTIVE|INACTIVE|ON_LEAVE)$", message = "Status must be ACTIVE, INACTIVE, or ON_LEAVE")
    private String employmentStatus;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long jobTitleId;

    @NotBlank
    private String address;
}