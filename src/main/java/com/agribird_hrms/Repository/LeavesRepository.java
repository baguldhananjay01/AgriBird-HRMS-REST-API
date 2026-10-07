package com.agribird_hrms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribird_hrms.entity.Employee;
import com.agribird_hrms.entity.leaves;

public interface LeavesRepository extends JpaRepository<leaves, Long> {
    
     List<leaves> findAllByOrderByIdDesc();

    List<leaves> findByEmployeeOrderByIdDesc(Employee employee);

    List<leaves> findByStatusOrderByIdDesc(String status);

    long countByStatus(String status);

    long countByEmployeeAndStatus(Employee employee, String status);
    
}
