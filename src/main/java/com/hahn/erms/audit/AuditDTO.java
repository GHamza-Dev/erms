package com.hahn.erms.audit;

import com.hahn.erms.application.dto.EmployeeDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AuditDTO {
    private String employeeId;
    private EmployeeDTO oldValue;
    private EmployeeDTO newValue;
    private String modification;
}