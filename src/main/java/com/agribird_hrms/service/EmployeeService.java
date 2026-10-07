package com.agribird_hrms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agribird_hrms.Repository.EmployeeRepository;
import com.agribird_hrms.entity.Employee;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // SAVE EMPLOYEE
    // =========================

    public Employee saveEmployee(Employee employee) {

        if (employee.getPassword() != null
                && !employee.getPassword().trim().isEmpty()) {

            String password = employee.getPassword();

        
            if (!isBCryptPassword(password)) {

                employee.setPassword(
                        passwordEncoder.encode(password)
                );
            }
        }

        return employeeRepository.save(employee);
    }

    // =========================
    // GET ALL
    // =========================

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    // =========================
    // GET BY ID
    // =========================

    public Employee getEmployeeById(long id) {

        return employeeRepository
                .findById(id)
                .orElse(null);
    }

    // =========================
    // UPDATE EMPLOYEE
    // =========================

    public Employee updateEmployee(
            String empID,
            Employee updatedEmployee) {

        Optional<Employee> optionalEmp =
                employeeRepository.findByEmpID(empID);

        if (optionalEmp.isEmpty()) {
            return null;
        }

        Employee existingEmployee =
                optionalEmp.get();

        existingEmployee.setName(
                updatedEmployee.getName()
        );

        existingEmployee.setEmail(
                updatedEmployee.getEmail()
        );

        existingEmployee.setPhone(
                updatedEmployee.getPhone()
        );

        existingEmployee.setEmergencyPhone(
                updatedEmployee.getEmergencyPhone()
        );

        existingEmployee.setAddress(
                updatedEmployee.getAddress()
        );

        if (updatedEmployee.getDepartment() != null) {

            existingEmployee.setDepartment(
                    updatedEmployee.getDepartment()
            );
        }

        if (updatedEmployee.getRole() != null) {

            existingEmployee.setRole(
                    updatedEmployee.getRole()
            );
        }

        if (updatedEmployee.getJoiningDate() != null) {

            existingEmployee.setJoiningDate(
                    updatedEmployee.getJoiningDate()
            );
        }

        if (updatedEmployee.getBloodGroup() != null) {

            existingEmployee.setBloodGroup(
                    updatedEmployee.getBloodGroup()
            );
        }

        // =========================
        // PASSWORD UPDATE
        // =========================

        if (updatedEmployee.getPassword() != null
                && !updatedEmployee.getPassword()
                .trim()
                .isEmpty()) {

            String newPassword =
                    updatedEmployee.getPassword();

            // फक्त plain password असेल तरच BCrypt करा
            if (!isBCryptPassword(newPassword)) {

                existingEmployee.setPassword(
                        passwordEncoder.encode(newPassword)
                );
            }
        }

        return employeeRepository.save(existingEmployee);
    }

    // =========================
    // DELETE
    // =========================

    public boolean deleteEmployee(String empID) {

        Optional<Employee> optionalEmp =
                employeeRepository.findByEmpID(empID);

        if (optionalEmp.isEmpty()) {
            return false;
        }

        employeeRepository.delete(
                optionalEmp.get()
        );

        return true;
    }

    // =========================
    // GET BY EMP ID
    // =========================

    public Employee getEmployeeByEmpID(String empID) {

        return employeeRepository
                .findByEmpID(empID)
                .orElse(null);
    }

    // =========================
    // LOGIN
    // =========================

    public Employee login(
            String email,
            String password) {

        Optional<Employee> optionalEmp =
                employeeRepository.findByEmail(email);

        if (optionalEmp.isEmpty()) {
            return null;
        }

        Employee employee =
                optionalEmp.get();

        if (password == null
                || employee.getPassword() == null) {

            return null;
        }

        if (!passwordEncoder.matches(
                password,
                employee.getPassword())) {

            return null;
        }

        return employee;
    }


    // =========================
    // CHECK BCRYPT PASSWORD
    // =========================

    private boolean isBCryptPassword(
            String password) {

        return password.matches(
                "^\\$2[ayb]?\\$\\d{2}\\$.*"
        );
    }
}
// package com.agribird_hrms.service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.agribird_hrms.Repository.EmployeeRepository;
// import com.agribird_hrms.entity.Employee;

// @Service
// public class EmployeeService {

//     private final EmployeeRepository employeeRepository;

//     public EmployeeService(EmployeeRepository employeeRepository) {
//         this.employeeRepository = employeeRepository;
//     }

//     public Employee saveEmployee(Employee employee){
//         return employeeRepository.save(employee);
//     }

//     public List<Employee> getAllEmployees() {
//         return employeeRepository.findAll();
//     }

//     public Employee getEmployeeById(long id) {
//         return employeeRepository.findById(id).orElse(null);
//     }

   
//     public Employee updateEmployee(String empID, Employee updatedEmployee) {

//     Optional<Employee> optionalEmp =
//             employeeRepository.findByEmpID(empID);

//     if (optionalEmp.isEmpty()) {
//         return null;
//     }

//     Employee existingEmployee = optionalEmp.get();

//     existingEmployee.setName(updatedEmployee.getName());
//     existingEmployee.setEmail(updatedEmployee.getEmail());
//     existingEmployee.setPhone(updatedEmployee.getPhone());
//     existingEmployee.setEmergencyPhone(
//             updatedEmployee.getEmergencyPhone()
//     );
//     existingEmployee.setAddress(
//             updatedEmployee.getAddress()
//     );

//     // Only update these when provided
//     if (updatedEmployee.getDepartment() != null) {
//         existingEmployee.setDepartment(
//                 updatedEmployee.getDepartment()
//         );
//     }

//     if (updatedEmployee.getRole() != null) {
//         existingEmployee.setRole(
//                 updatedEmployee.getRole()
//         );
//     }

//     if (updatedEmployee.getJoiningDate() != null) {
//         existingEmployee.setJoiningDate(
//                 updatedEmployee.getJoiningDate()
//         );
//     }

//     if (updatedEmployee.getBloodGroup() != null) {
//         existingEmployee.setBloodGroup(
//                 updatedEmployee.getBloodGroup()
//         );
//     }

//     return employeeRepository.save(existingEmployee);
// }


//     public boolean deleteEmployee(String empID){

//         Optional<Employee> optionalEmp = employeeRepository.findByEmpID(empID);
        
//         if(optionalEmp.isEmpty()){
//             return false;
//         }
//         employeeRepository.delete(optionalEmp.get());
//         return true;
//     }

//     public Employee getEmployeeByEmpID(String empID) {
//     return employeeRepository.findByEmpID(empID).orElse(null);
// }

//     public Employee login(String email, String password) {

//     Optional<Employee> optionalEmp =
//             employeeRepository.findByEmailAndPassword(email, password);

//     return optionalEmp.orElse(null);
// }

// }
