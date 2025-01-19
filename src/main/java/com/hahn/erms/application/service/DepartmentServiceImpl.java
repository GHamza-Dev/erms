package com.hahn.erms.application.service;

import com.hahn.erms.application.dto.DepartmentDTO;
import com.hahn.erms.application.entity.Department;
import com.hahn.erms.application.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentDTO::toDto).toList();
    }
}
