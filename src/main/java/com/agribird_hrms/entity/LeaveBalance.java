package com.agribird_hrms.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "leave_balance",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"employee_id", "leave_type", "year"}
        )
    }
)
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "leave_type", nullable = false)
    private String leaveType;

    @Column(name = "allocated_days", nullable = false)
    private int allocatedDays;

    @Column(name = "used_days", nullable = false)
    private int usedDays;

    @Column(name = "remaining_days", nullable = false)
    private int remainingDays;

    @Column(nullable = false)
    private String year;

    public LeaveBalance() {
    }

    public LeaveBalance(Employee employee,
                        String leaveType,
                        int allocatedDays,
                        int usedDays,
                        int remainingDays,
                        String year) {

        this.employee = employee;
        this.leaveType = leaveType;
        this.allocatedDays = allocatedDays;
        this.usedDays = usedDays;
        this.remainingDays = remainingDays;
        this.year = year;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getAllocatedDays() {
        return allocatedDays;
    }

    public void setAllocatedDays(int allocatedDays) {
        this.allocatedDays = allocatedDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(int usedDays) {
        this.usedDays = usedDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}