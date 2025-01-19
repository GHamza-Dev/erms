package com.hahn.erms.application.controller;

import com.hahn.erms.application.dto.JobTitleDTO;
import com.hahn.erms.application.service.JobTitleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.job-titles.base.url}")
public class JobTitleController {
    private final JobTitleService jobTitleService;

    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @GetMapping
    public ResponseEntity<List<JobTitleDTO>> getAllDepartments() {
        return new ResponseEntity<>(jobTitleService.getAllJobTitles(), HttpStatus.OK);
    }
}
