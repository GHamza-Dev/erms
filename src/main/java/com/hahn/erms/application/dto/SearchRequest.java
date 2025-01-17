package com.hahn.erms.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SearchRequest {
    private String name;
    private String employeeId;
    private Long departmentId;
    private Long jobTitleId;
    private String employmentStatus;
    private LocalDate hireDateFrom;
    private LocalDate hireDateTo;
    private String contractType;
}