package com.hahn.erms.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldValidationError {
    private String field;
    private String message;
}