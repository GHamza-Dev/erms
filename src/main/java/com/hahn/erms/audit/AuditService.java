package com.hahn.erms.audit;

import com.hahn.erms.application.dto.EmployeeDTO;
import com.hahn.erms.application.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@Log4j2
public class AuditService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<AuditDTO> getEmployeeAudit(String employeeId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);

        Employee employee = entityManager.createQuery(
                        "SELECT e FROM Employee e WHERE e.employeeId = :employeeId", Employee.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();

        List<Number> revisions = reader.getRevisions(Employee.class, employee.getId());
        List<AuditDTO> auditTrail = new ArrayList<>();

        Employee previousVersion = null;

        for (Number revision : revisions) {
            Employee currentVersion = reader.find(Employee.class, employee.getId(), revision);
            CustomRevisionEntity revEntity = reader.findRevision(CustomRevisionEntity.class, revision);

            String modification = String.format("%s by %s at %s",
                    previousVersion == null ? "Created" : "Updated",
                    revEntity.getModifiedBy(),
                    new Date(revEntity.getTimestamp()));

            auditTrail.add(AuditDTO.builder()
                    .employeeId(employeeId)
                    .oldValue(previousVersion != null ? EmployeeDTO.toDto(previousVersion) : null)
                    .newValue(EmployeeDTO.toDto(currentVersion))
                    .modification(modification)
                    .build());

            previousVersion = currentVersion;
        }

        return auditTrail;
    }

    private Map<String, Object> convertEmployeeToMap(Employee employee) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", employee.getId());
        map.put("employeeId", employee.getEmployeeId());
        map.put("firstName", employee.getFirstName());
        map.put("lastName", employee.getLastName());
        map.put("email", employee.getEmail());
        map.put("phone", employee.getPhone());
        map.put("employmentStatus", employee.getEmploymentStatus());
        map.put("address", employee.getAddress());

        if (employee.getDepartment() != null) {
            map.put("departmentId", employee.getDepartment().getId());
            map.put("departmentName", employee.getDepartment().getName());
        }

        if (employee.getJobTitle() != null) {
            map.put("jobTitleId", employee.getJobTitle().getId());
            map.put("jobTitle", employee.getJobTitle().getTitle());
        }

        if (employee.getContract() != null) {
            map.put("hireDate", employee.getContract().getHireDate());
            map.put("contractType", employee.getContract().getContractType());
        }

        return map;
    }
}