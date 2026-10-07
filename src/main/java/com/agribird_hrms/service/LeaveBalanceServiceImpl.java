package com.agribird_hrms.service; 

import com.agribird_hrms.entity.Employee; 
import com.agribird_hrms.entity.LeaveBalance; 
import com.agribird_hrms.Repository.EmployeeRepository; 
import com.agribird_hrms.Repository.LeaveBalanceRepository; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import java.util.List; 

@Service 
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository; 
    private final EmployeeRepository employeeRepository;

    public LeaveBalanceServiceImpl( LeaveBalanceRepository leaveBalanceRepository, EmployeeRepository employeeRepository) { 
        
        this.leaveBalanceRepository = leaveBalanceRepository; 
        this.employeeRepository = employeeRepository; 
}

    @Override 
    @Transactional
     public List<LeaveBalance> getEmployeeLeaveBalance( String empID, String year) {

        Employee employee = employeeRepository .findById(Long.parseLong(empID)) 
        .orElseThrow(() -> new RuntimeException( "Employee not found with ID: " + empID ));

        initializeEmployeeBalance(empID, year);

        return leaveBalanceRepository .findByEmployeeAndYear(employee, year);

        }

    @Override 
    @Transactional
     public LeaveBalance getBalance( String empID, String leaveType, String year) {

        Employee employee = employeeRepository .findById(Long.parseLong(empID)) 
        .orElseThrow(() -> new RuntimeException( "Employee not found with ID: " + empID ));

        initializeEmployeeBalance(empID, year);

        String normalizedLeaveType = normalizeLeaveType(leaveType);

       return leaveBalanceRepository .findByEmployeeAndLeaveTypeAndYear( 
                                employee,
                                normalizedLeaveType, year ) 
       .orElseThrow(() -> new RuntimeException( "Leave balance not found for: " + normalizedLeaveType )); 
        }

@Override
@Transactional
public void initializeEmployeeBalance(String empID, String year) {

    Employee employee = employeeRepository.findById(Long.parseLong(empID))
            .orElseThrow(() ->
                    new RuntimeException("Employee not found with ID: " + empID)
            );

    createIfNotExists(employee, "Casual", 10, year);
    createIfNotExists(employee, "Sick", 10, year);
    createIfNotExists(employee, "Earned", 10, year);
    createIfNotExists(employee, "Privilege", 10, year);
    createIfNotExists(employee, "Paid Leave", 10, year);
}

   private void createIfNotExists( Employee employee, String leaveType, int allocatedDays, String year) {

        boolean exists = leaveBalanceRepository .findByEmployeeAndLeaveTypeAndYear( employee, leaveType, year ) .isPresent();

        if (!exists) { 
        LeaveBalance balance = new LeaveBalance( employee, leaveType, allocatedDays, 0, allocatedDays, year );
         leaveBalanceRepository.save(balance); }
         }

@Override
@Transactional
public void deductLeave( String empID, String leaveType, int days, String year) {

        if (days <= 0) { 
        throw new RuntimeException( "Leave days must be greater than zero." ); 
        }

        Employee employee = employeeRepository .findById(Long.parseLong(empID))
         .orElseThrow(() -> new RuntimeException( "Employee not found with ID: " + empID ));

         initializeEmployeeBalance(empID, year);

         String normalizedLeaveType = normalizeLeaveType(leaveType);

        LeaveBalance balance = leaveBalanceRepository 
        .findByEmployeeAndLeaveTypeAndYear( employee, normalizedLeaveType, year ) 
        .orElseThrow(() -> new RuntimeException( "Leave balance not found for: " + normalizedLeaveType ));

        if (days <= 0) {
            throw new RuntimeException(
                    "Leave days must be greater than zero."
            );
        }

        if (balance.getRemainingDays() < days) { 
                throw new RuntimeException( "Insufficient " + normalizedLeaveType + 
                " leave balance. Remaining: " + balance.getRemainingDays() 
                + ", Required: " + days ); }

        balance.setUsedDays( balance.getUsedDays() + days );

        balance.setRemainingDays( balance.getAllocatedDays() - balance.getUsedDays() );

        leaveBalanceRepository.save(balance); 
}

    private String normalizeLeaveType(String leaveType) {

    if (leaveType == null || leaveType.trim().isEmpty()) {
        throw new RuntimeException("Leave type is required.");
    }

    String type = leaveType.trim().toLowerCase();

    switch (type) {

        case "casual":
        case "casual leave":
        case "cl":
            return "Casual";

        case "sick":
        case "sick leave":
        case "sl":
            return "Sick";

        case "earned":
        case "earned leave":
        case "el":
        case "paid leave":
            return "Earned";

        case "privilege":
        case "privilege leave":
        case "pl":
            return "Privilege";

        default:
            throw new RuntimeException(
                    "Invalid leave type: " + leaveType
            );
    }
}
}