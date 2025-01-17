package com.hahn.erms.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobTitleDTO {
    private Long id;
    private String title;
    private String description;
}