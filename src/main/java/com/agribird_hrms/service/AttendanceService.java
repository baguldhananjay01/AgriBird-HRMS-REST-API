package com.agribird_hrms.service;

import java.util.List;
import java.util.Optional;

import com.agribird_hrms.entity.Attendance;
import com.agribird_hrms.entity.Employee;

public interface AttendanceService {

    Attendance checkIn(
            long employeeId,
            double latitude,
            double longitude,
            String wifiSsid,
            boolean mockLocation
    );

    Attendance checkout(long employeeId);

    Optional<Attendance> getAttendanceById(long id);

    List<Attendance> getAllAttendance(long employeeId);

    List<Attendance> getTodayPresentEmployees(String date);

    long getTodayPresentCount(String date);
    
    long getTodayAttendanceCount(String date);
    long countByDateAndStatus(String date, String status);
    
}
