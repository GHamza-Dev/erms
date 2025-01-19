package com.hahn.erms.application.dto;

import jakarta.validation.constraints.Pattern;
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

    private Integer page = 0;
    private Integer size = 20;

    @Pattern(regexp = "id|employeeId")
    private String sortBy = "id";

    @Pattern(regexp = "desc|asc")
    private String sortDirection = "desc";
}