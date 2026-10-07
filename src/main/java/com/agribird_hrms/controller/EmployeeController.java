package com.agribird_hrms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribird_hrms.dto.ApiResponse;
import com.agribird_hrms.dto.LoginResponse;
import com.agribird_hrms.entity.Employee;
import com.agribird_hrms.security.JwtService;
import com.agribird_hrms.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtService jwtService;

    public EmployeeController(
            EmployeeService employeeService,
            JwtService jwtService) {

        this.employeeService = employeeService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ApiResponse saveEmployee(
            @RequestBody Employee employee) {

        Employee savedEmployee =
                employeeService.saveEmployee(employee);

        return new ApiResponse(
                "SUCCESS",
                "Employee saved successfully",
                savedEmployee
        );
    }

    @PostMapping("/create-super-admin")
public ResponseEntity<ApiResponse> createSuperAdmin(
        @RequestBody Employee employee) {

    if (employee.getEmail() == null
            || employee.getEmail().trim().isEmpty()) {

        return ResponseEntity
                .badRequest()
                .body(
                        new ApiResponse(
                                "FAILED",
                                "Email is required",
                                null
                        )
                );
    }

    if (employee.getPassword() == null
            || employee.getPassword().trim().isEmpty()) {

        return ResponseEntity
                .badRequest()
                .body(
                        new ApiResponse(
                                "FAILED",
                                "Password is required",
                                null
                        )
                );
    }

    employee.setRole("SUPER_ADMIN");

    Employee savedEmployee =
            employeeService.saveEmployee(employee);

    return ResponseEntity.ok(
            new ApiResponse(
                    "SUCCESS",
                    "Super Admin created successfully",
                    savedEmployee
            )
    );
}

    @GetMapping
    public ResponseEntity<ApiResponse> getAllEmployees() {

        List<Employee> employees =
                employeeService.getAllEmployees();

        return ResponseEntity.ok(
                new ApiResponse(
                        "SUCCESS",
                        "Employees retrieved successfully",
                        employees
                )
        );
    }

    // =========================
    // EMPLOYEE LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody Employee employee) {

        Employee loggedInEmployee =
                employeeService.login(
                        employee.getEmail(),
                        employee.getPassword()
                );

        if (loggedInEmployee == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ApiResponse<>(
                                    "FAILED",
                                    "Invalid Email or Password",
                                    null
                            )
                    );
        }

        // Injected JwtService वापरायचा
        String token =
                jwtService.generateToken(
                        loggedInEmployee
                );

        LoginResponse loginResponse =
                new LoginResponse(
                        token,
                        loggedInEmployee.getId(),
                        loggedInEmployee.getEmpID(),
                        loggedInEmployee.getName(),
                        loggedInEmployee.getEmail(),
                        loggedInEmployee.getRole()
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Login successful",
                        loginResponse
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(
            @PathVariable long id) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    "ERROR",
                                    "Employee Not Found",
                                    null
                            )
                    );
        }

        return ResponseEntity.ok(
                new ApiResponse(
                        "SUCCESS",
                        "Employee Retrieved Successfully",
                        employee
                )
        );
    }

    @GetMapping("/employee-id/{empID}")
    public ResponseEntity<ApiResponse> getEmployeeByEmpID(
            @PathVariable String empID) {

        Employee employee =
                employeeService.getEmployeeByEmpID(empID);

        if (employee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    "ERROR",
                                    "Employee not found",
                                    null
                            )
                    );
        }

        return ResponseEntity.ok(
                new ApiResponse(
                        "SUCCESS",
                        "Employee found successfully",
                        employee
                )
        );
    }

    @PutMapping("/update/{empID}")
    public ResponseEntity<ApiResponse> updateEmployee(
            @PathVariable String empID,
            @RequestBody Employee employee) {

        Employee updatedEmployee =
                employeeService.updateEmployee(
                        empID,
                        employee
                );

        if (updatedEmployee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    "ERROR",
                                    "Employee not found with ID: " + empID,
                                    null
                            )
                    );
        }

        return ResponseEntity.ok(
                new ApiResponse(
                        "SUCCESS",
                        "Employee updated successfully",
                        updatedEmployee
                )
        );
    }

    @DeleteMapping("/emp/{empID}")
    public ResponseEntity<ApiResponse> deleteEmployee(
            @PathVariable String empID) {

        boolean isDeleted =
                employeeService.deleteEmployee(empID);

        if (!isDeleted) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse<>(
                                    "ERROR",
                                    "Employee not found with ID: " + empID,
                                    null
                            )
                    );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Employee deleted successfully",
                        null
                )
        );
    }
}