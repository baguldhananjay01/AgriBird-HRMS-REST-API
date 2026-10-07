package com.agribird_hrms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agribird_hrms.dto.ApiResponse;
import com.agribird_hrms.entity.leaves;
import com.agribird_hrms.service.LeavesService;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin
public class LeavesController {

    private final LeavesService leavesService;

    public LeavesController(LeavesService leavesService) {
        this.leavesService = leavesService;
    }

    // Apply Leave
    @PostMapping("/apply/{empID}")
    public ResponseEntity<ApiResponse<leaves>> applyLeave(
            @PathVariable String empID,
            @RequestBody leaves leave) {

        leaves savedLeave = leavesService.applyLeave(empID, leave);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Leave applied successfully",
                        savedLeave
                )
        );
    }

    // Get All Leaves
    @GetMapping
    public ResponseEntity<ApiResponse<List<leaves>>> getAllLeaves() {

        List<leaves> leavesList = leavesService.getAllLeaves();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Leave requests fetched successfully",
                        leavesList
                )
        );
    }

    // Get Employee Leaves
    @GetMapping("/employee/{empID}")
    public ResponseEntity<ApiResponse<List<leaves>>> getEmployeeLeaves(
            @PathVariable String empID) {

        List<leaves> leavesList =
                leavesService.getEmployeeLeaves(empID);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Employee leaves fetched successfully",
                        leavesList
                )
        );
    }

    // Get Leaves By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<leaves>>> getLeavesByStatus(
            @PathVariable String status) {

        List<leaves> leavesList =
                leavesService.getLeavesByStatus(status);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Leaves fetched successfully",
                        leavesList
                )
        );
    }

    // Get Leave By ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<leaves>> getLeaveById(
            @PathVariable Long id) {

        leaves leave = leavesService.getLeaveById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Leave fetched successfully",
                        leave
                )
        );
    }

    // Update Leave Status
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<leaves>> updateLeaveStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        leaves updatedLeave =
                leavesService.updateLeaveStatus(id, status);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Leave status updated successfully",
                        updatedLeave
                )
        );
    }

    // Pending Leave Count
    @GetMapping("/count/pending")
    public ResponseEntity<ApiResponse<Long>> getPendingLeaveCount() {

        long count = leavesService.getPendingLeaveCount();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Pending leave count fetched successfully",
                        count
                )
        );
    }

    // Employee Pending Leave Count
    @GetMapping("/employee/{empID}/count/pending")
    public ResponseEntity<ApiResponse<Long>> getEmployeePendingLeaveCount(
            @PathVariable String empID) {

        long count =
                leavesService.getEmployeePendingLeaveCount(empID);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        "Pending leave count fetched successfully",
                        count
                )
        );
    }
}