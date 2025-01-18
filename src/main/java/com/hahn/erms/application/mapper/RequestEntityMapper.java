package com.hahn.erms.application.mapper;

import com.hahn.erms.application.dto.CreateEmployeeRequest;
import com.hahn.erms.application.entity.Contract;
import com.hahn.erms.application.entity.Employee;
import com.hahn.erms.application.entity.Department;
import com.hahn.erms.application.entity.JobTitle;
import com.hahn.erms.application.repository.DepartmentRepository;
import com.hahn.erms.application.repository.JobTitleRepository;
import com.hahn.erms.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class RequestEntityMapper {
    private final DepartmentRepository departmentRepository;
    private final JobTitleRepository jobTitleRepository;


    public Employee mapToCreateEmployee(CreateEmployeeRequest request) {
        log.info("Mapping create employee request to Employee");
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setEmploymentStatus(request.getEmploymentStatus());
        employee.setAddress(request.getAddress());

        Contract contract = new Contract();
        contract.setHireDate(request.getHireDate());
        contract.setContractType(request.getContractType());

        contract.setEmployee(employee);
        employee.setContract(contract);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new BusinessException("ERR_DEPARTMENT_NOT_FOUND", HttpStatus.BAD_REQUEST));
        employee.setDepartment(department);

        JobTitle jobTitle = jobTitleRepository.findById(request.getJobTitleId())
                .orElseThrow(() -> new BusinessException("ERR_JOB_TITLE_NOT_FOUND", HttpStatus.BAD_REQUEST));
        employee.setJobTitle(jobTitle);

        log.info("Employee mapped successfully");
        return employee;
    }
}
