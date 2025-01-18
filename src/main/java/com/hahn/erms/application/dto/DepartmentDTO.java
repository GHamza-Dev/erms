package com.hahn.erms.application.dto;

import com.hahn.erms.application.entity.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String name;
    private EmployeeDTO manager;
    private Integer employeeCount;

    public static DepartmentDTO toDto(Department department) {

        if (department == null) {
            return null;
        }

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }
}