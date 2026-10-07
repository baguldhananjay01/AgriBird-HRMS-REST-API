package com.agribird_hrms.service;

import java.util.List;

import com.agribird_hrms.entity.leaves;

public interface LeavesService {
    
    leaves applyLeave(String empID, leaves leave);

    List<leaves> getAllLeaves();

    List<leaves> getEmployeeLeaves(String empID);

    List<leaves> getLeavesByStatus(String status);

    leaves getLeaveById(Long id);

    leaves updateLeaveStatus(Long id, String status);

    long getPendingLeaveCount();

    long getEmployeePendingLeaveCount(String empID);


}
