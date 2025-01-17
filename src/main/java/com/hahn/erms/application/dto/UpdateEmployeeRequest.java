package com.hahn.erms.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEmployeeRequest {
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String employmentStatus;
    private Long departmentId;
    private Long jobTitleId;
}