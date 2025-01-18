package com.hahn.erms.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEmployeeRequest {
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;

    @Email
    private String email;
    private String phone;

    @Pattern(regexp = "^(ACTIVE|INACTIVE|ON_LEAVE)$", message = "Status must be ACTIVE, INACTIVE, or ON_LEAVE")
    private String employmentStatus;

    private Long departmentId;
    private Long jobTitleId;
}