package com.hahn.erms.application.dto;

import com.hahn.erms.application.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private String employmentStatus;
    private DepartmentDTO department;
    private JobTitleDTO jobTitle;
    private String addresses;

    public static EmployeeDTO toDto(Employee employee) {

        if (employee == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());

        dto.setJobTitle(JobTitleDTO.toDto(employee.getJobTitle()));
        dto.setDepartment(DepartmentDTO.toDto(employee.getDepartment()));

        return dto;
    }
}