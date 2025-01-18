package com.hahn.erms.devonly;

import com.hahn.erms.application.entity.Contract;
import com.hahn.erms.application.entity.Department;
import com.hahn.erms.application.entity.Employee;
import com.hahn.erms.application.entity.JobTitle;
import com.hahn.erms.application.repository.DepartmentRepository;
import com.hahn.erms.application.repository.EmployeeRepository;
import com.hahn.erms.application.repository.JobTitleRepository;
import com.hahn.erms.security.dao.User;
import com.hahn.erms.security.dao.UserRepository;
import com.hahn.erms.security.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Log4j2
public class DatabaseInitializer {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final JobTitleRepository jobTitleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            JobTitleRepository jobTitleRepository,
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void run(String... args) {
        log.info("Initializing Database");
//        if (userRepository.count() > 0) {
//            log.info("Database already initialized");
//            return;
//        }

//        cleanDatabase();

        log.info("Initializing Database with sample data");

        // Create Departments
        log.info("Creating departments...");
        Department itDepartment = createDepartment("IT", "IT-001");
        Department hrDepartment = createDepartment("Human Resources", "HR-001");
        Department financeDepartment = createDepartment("Finance", "FIN-001");
        Department marketingDepartment = createDepartment("Marketing", "MKT-001");

        // Create Job Titles
        log.info("Creating job titles...");
        JobTitle ceo = createJobTitle("CEO", "Chief Executive Officer");
        JobTitle cto = createJobTitle("CTO", "Chief Technology Officer");
        JobTitle hrManager = createJobTitle("HR Manager", "Human Resources Manager");
        JobTitle developer = createJobTitle("Software Developer", "Software Developer");
        JobTitle designer = createJobTitle("UX Designer", "User Experience Designer");
        JobTitle accountant = createJobTitle("Accountant", "Financial Accountant");
        JobTitle marketingManager = createJobTitle("Marketing Manager", "Marketing Department Manager");

        // Create Employees with Contracts
        log.info("Creating employees...");

        // Admin Employee
        Employee adminEmployee = createEmployee(
                "John", "Admin", "admin@erms.com", "+1234567890",
                itDepartment, ceo, "ACTIVE", "PERMANENT"
        );

        // HR Manager Employee
        Employee hrManagerEmployee = createEmployee(
                "Sarah", "Johnson", "hr.manager@erms.com", "+1234567891",
                hrDepartment, hrManager, "ACTIVE", "PERMANENT"
        );

        // IT Manager Employee
        Employee itManagerEmployee = createEmployee(
                "Michael", "Tech", "it.manager@erms.com", "+1234567892",
                itDepartment, cto, "ACTIVE", "PERMANENT"
        );

        Employee mrkManagerEmployee = createEmployee(
                "Hamza", "Tijani", "mrk.manager@erms.com", "+1234567892",
                marketingDepartment, marketingManager, "ACTIVE", "PERMANENT"
        );

        // Regular Employees
        Employee developer1 = createEmployee(
                "David", "Smith", "david.smith@erms.com", "+1234567893",
                itDepartment, developer, "ACTIVE", "PERMANENT"
        );

        Employee developer2 = createEmployee(
                "Emma", "Wilson", "emma.wilson@erms.com", "+1234567894",
                itDepartment, developer, "ACTIVE", "PERMANENT"
        );

        Employee designer1 = createEmployee(
                "Sophie", "Brown", "sophie.brown@erms.com", "+1234567895",
                itDepartment, designer, "ACTIVE", "PERMANENT"
        );

        Employee accountant1 = createEmployee(
                "James", "Davis", "james.davis@erms.com", "+1234567896",
                financeDepartment, accountant, "ACTIVE", "PERMANENT"
        );

        // Create Users
        log.info("Creating users...");
        createUser("admin", "admin", "admin@erms.com", Role.ROLE_ADMIN, adminEmployee);
        createUser("hrmanager", "hr", "hr.manager@erms.com", Role.ROLE_HR, hrManagerEmployee);
        createUser("itmanager", "it", "it.manager@erms.com", Role.ROLE_MANAGER, itManagerEmployee);
        createUser("developer1", "dev", "david.smith@erms.com", Role.ROLE_EMPLOYEE, developer1);
        createUser("mrkmanager", "mrkmanager", "mrk.manager@erms.com", Role.ROLE_MANAGER, mrkManagerEmployee);


        log.info("Database initialization completed");
    }

    private Department createDepartment(String name, String code) {
        Department department = new Department();
        department.setName(name);
        department.setCode(code);
        return departmentRepository.save(department);
    }

    private JobTitle createJobTitle(String title, String description) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setTitle(title);
        jobTitle.setDescription(description);
        return jobTitleRepository.save(jobTitle);
    }

    private Employee createEmployee(String firstName, String lastName, String email, String phone,
                                    Department department, JobTitle jobTitle,
                                    String employmentStatus, String contractType) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setEmployeeId("EMP_" + System.currentTimeMillis());
        employee.setEmploymentStatus(employmentStatus);
        employee.setDepartment(department);
        employee.setJobTitle(jobTitle);
        employee.setAddress("123 Main St, City, Country"); // Default address

        Contract contract = new Contract();
        contract.setHireDate(LocalDate.now());
        contract.setContractType(contractType);
        contract.setEmployee(employee);

        employee.setContract(contract);

        return employeeRepository.save(employee);
    }

    private User createUser(String username, String password, String email,
                            Role role, Employee employee) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(role);
        user.setEmployee(employee);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Transactional
    public void cleanDatabase() {
        log.info("Cleaning database...");
        userRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        jobTitleRepository.deleteAll();
        log.info("Database cleaned successfully");
    }

    @PostConstruct
    public void logEncoderType() {
        log.info("Using password encoder: {}", passwordEncoder.getClass().getName());
    }
}