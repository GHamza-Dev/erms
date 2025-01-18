package com.hahn.erms.application.dto;

import com.hahn.erms.application.entity.JobTitle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobTitleDTO {
    private Long id;
    private String title;
    private String description;

    public static JobTitleDTO toDto(JobTitle jobTitle) {

        if (jobTitle == null) {
            return null;
        }

        JobTitleDTO dto = new JobTitleDTO();
        dto.setId(jobTitle.getId());
        dto.setTitle(jobTitle.getTitle());
        dto.setDescription(jobTitle.getDescription());
        return dto;
    }
}