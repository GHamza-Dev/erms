package com.hahn.erms.application.dto.projection;

import java.time.LocalDate;

public interface EmployeeProjection {
    Long getId();

    String getEmployeeId();

    String getFirstName();

    String getLastName();

    String getEmploymentStatus();

    String getEmail();

    String getPhone();

    DepartmentInfo getDepartment();

    JobTitleInfo getJobTitle();

    ContractInfo getContract();

    interface DepartmentInfo {
        String getName();
    }

    interface JobTitleInfo {
        String getTitle();
    }

    interface ContractInfo {
        LocalDate getHireDate();
    }
}
