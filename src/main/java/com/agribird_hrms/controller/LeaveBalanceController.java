package com.agribird_hrms.controller;

import com.agribird_hrms.dto.ApiResponse;
import com.agribird_hrms.entity.LeaveBalance;
import com.agribird_hrms.service.LeaveBalanceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/leave-balance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(
            LeaveBalanceService leaveBalanceService) {

        this.leaveBalanceService = leaveBalanceService;
    }

    @GetMapping("/employee/{empID}")
    public ResponseEntity<ApiResponse<List<LeaveBalance>>>
    getEmployeeBalance(
            @PathVariable String empID,
            @RequestParam(required = false) String year) {

        if (year == null || year.trim().isEmpty()) {
            year = String.valueOf(Year.now().getValue());
        }

        List<LeaveBalance> balances =
                leaveBalanceService.getEmployeeLeaveBalance(
                        empID,
                        year
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Leave balance fetched successfully.",
                        balances
                )
        );
    }
}