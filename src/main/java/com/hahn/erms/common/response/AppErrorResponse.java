package com.hahn.erms.common.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AppErrorResponse {
    private LocalDateTime timestamp;
    private String error;
    private int status;
    private String statusText;
    private List<FieldValidationError> validationErrors;
}