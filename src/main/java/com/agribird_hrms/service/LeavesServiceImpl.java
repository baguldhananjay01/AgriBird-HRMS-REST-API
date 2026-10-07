package com.agribird_hrms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agribird_hrms.Repository.EmployeeRepository;
import com.agribird_hrms.Repository.LeavesRepository;
import com.agribird_hrms.entity.Employee;
import com.agribird_hrms.entity.leaves;

import jakarta.transaction.Transactional;

@Service
public class LeavesServiceImpl implements LeavesService {

    private final LeavesRepository leavesRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceService leaveBalanceService;

    public LeavesServiceImpl(
            LeavesRepository leavesRepository,
            EmployeeRepository employeeRepository,
            LeaveBalanceService leaveBalanceService) {

        this.leavesRepository = leavesRepository;
        this.employeeRepository = employeeRepository;
        this.leaveBalanceService = leaveBalanceService;
    }

@Override
@Transactional
public leaves applyLeave(String empID, leaves leave) {

    Employee employee = employeeRepository
            .findById(Long.parseLong(empID))
            .orElseThrow(() ->
                    new RuntimeException(
                            "Employee not found with ID: " + empID
                    ));

    leave.setId(null);
    leave.setEmployee(employee);

    if (leave.getStatus() == null
            || leave.getStatus().trim().isEmpty()) {

        leave.setStatus("Pending");
    }

    return leavesRepository.save(leave);
}

    @Override
    public List<leaves> getAllLeaves() {
        return leavesRepository.findAllByOrderByIdDesc();
    }

    @Override
    public List<leaves> getEmployeeLeaves(String employeeId) {

        Employee employee = employeeRepository
                .findById(Long.parseLong(employeeId))
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with ID: " + employeeId
                        )
                );

        return leavesRepository
                .findByEmployeeOrderByIdDesc(employee);
    }

    @Override
    public List<leaves> getLeavesByStatus(String status) {

        return leavesRepository
                .findByStatusOrderByIdDesc(status);
    }

    @Override
    public leaves getLeaveById(Long id) {

        return leavesRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Leave not found with id: " + id
                        )
                );
    }

@Override 
@Transactional 
public leaves updateLeaveStatus( Long id, String status) {

    leaves leave = leavesRepository .findById(id) 
    .orElseThrow(() -> new RuntimeException( "Leave not found with ID: " + id ));

    String oldStatus = leave.getStatus();

    if ("Approved".equalsIgnoreCase(status) && !"Approved".equalsIgnoreCase(oldStatus)) {

        if (leave.getStartDate() == null || leave.getEndDate() == null) {
                 throw new RuntimeException( "Leave dates are required." ); 
                }

                if (leave.getEmployee() == null) {
                        throw new RuntimeException( "Employee not found for this leave." ); 
                }

                if (leave.getLeaveType() == null || leave.getLeaveType().trim().isEmpty()) { 
                        throw new RuntimeException( "Leave type is required." ); 
                }

                if (leave.getEndDate() .isBefore(leave.getStartDate())) { 
                        throw new RuntimeException( "End date cannot be before start date." ); 
                }



        long days = java.time.temporal.ChronoUnit.DAYS.between( leave.getStartDate(), leave.getEndDate() ) + 1;

        String year = String.valueOf( leave.getStartDate().getYear() );

        leaveBalanceService.deductLeave( String.valueOf( leave.getEmployee().getId() ), 
        leave.getLeaveType(), (int) days, year ); } leave.setStatus(status); 
        return leavesRepository.save(leave); 
}

    @Override
    public long getPendingLeaveCount() {

        return leavesRepository
                .countByStatus("Pending");
    }

    @Override
    public long getEmployeePendingLeaveCount(String empID) {

        Employee employee = employeeRepository
                .findById(Long.parseLong(empID))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with ID: " + empID
                        )
                );

        return leavesRepository
                .countByEmployeeAndStatus(
                        employee,
                        "Pending"
                );
    }
}