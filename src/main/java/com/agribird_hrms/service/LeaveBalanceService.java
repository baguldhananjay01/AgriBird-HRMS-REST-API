package com.agribird_hrms.service;

import com.agribird_hrms.entity.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {

    List<LeaveBalance> getEmployeeLeaveBalance(
            String empID,
            String year
    );

    LeaveBalance getBalance(
            String empID,
            String leaveType,
            String year
    );

    void initializeEmployeeBalance(
            String empID,
            String year
    );

    void deductLeave(
            String empID,
            String leaveType,
            int days,
            String year
    );
}