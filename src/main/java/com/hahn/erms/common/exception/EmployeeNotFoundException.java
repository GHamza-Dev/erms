package com.hahn.erms.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeNotFoundException extends BusinessException {

    public EmployeeNotFoundException() {
        super("ERR_EMPLOYEE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    public EmployeeNotFoundException(String code, HttpStatus status) {
        super(code, status);
    }

    public EmployeeNotFoundException(String code, Object[] args, HttpStatus status) {
        super(code, args, status);
    }
}