package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.JobTitleDTO;
import com.hahn.erms.application.entity.JobTitle;
import com.hahn.erms.application.repository.JobTitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    public JobTitleServiceImpl(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    @Override
    public List<JobTitleDTO> getAllJobTitles() {
        List<JobTitle> jobTitles = jobTitleRepository.findAll();
        return jobTitles.stream().map(JobTitleDTO::toDto).toList();
    }
}
