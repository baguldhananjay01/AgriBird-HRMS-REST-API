package com.agribird_hrms.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribird_hrms.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmpID(String empID);

    Optional<Employee> findByEmail(String email);

}

// package com.agribird_hrms.Repository;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.agribird_hrms.entity.Employee;

// public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
//     Optional<Employee> findByEmpID(String empID);

//     Optional<Employee> findByEmailAndPassword(String email, String password);


// }
