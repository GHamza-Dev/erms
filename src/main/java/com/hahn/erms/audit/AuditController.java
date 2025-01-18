package com.hahn.erms.audit;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.audit.base.url}")
@Tag(name = "Audit API", description = "API endpoints for audit history")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<List<AuditDTO>> getEmployeeAudit(@PathVariable String employeeId) {
        return ResponseEntity.ok(auditService.getEmployeeAudit(employeeId));
    }
}