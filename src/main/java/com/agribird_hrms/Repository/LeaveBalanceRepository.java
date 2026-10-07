package com.agribird_hrms.Repository;

import com.agribird_hrms.entity.Employee;
import com.agribird_hrms.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository
        extends JpaRepository<LeaveBalance, Long> {

    List<LeaveBalance> findByEmployeeAndYear(
            Employee employee,
            String year
    );

    Optional<LeaveBalance> findByEmployeeAndLeaveTypeAndYear(
            Employee employee,
            String leaveType,
            String year
    );
}