package com.agribird_hrms.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agribird_hrms.dto.ApiResponse;
import com.agribird_hrms.entity.Attendance;
import com.agribird_hrms.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
        
 private final AttendanceService attendanceService;

        public AttendanceController(
                AttendanceService attendanceService) {

                this.attendanceService = attendanceService;
        }

        @PostMapping("/check-in")
        public ResponseEntity<ApiResponse<Attendance>> checkIn(

                @RequestParam long employeeId,
                @RequestParam double latitude,
                @RequestParam double longitude,
                @RequestParam(required = false) String wifiSsid,
                @RequestParam(defaultValue = "false") boolean mockLocation) {

                try {

                Attendance attendance =
                        attendanceService.checkIn(
                                employeeId,
                                latitude,
                                longitude,
                                wifiSsid,
                                mockLocation
                        );

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(
                                new ApiResponse<>(
                                        "SUCCESS",
                                        "Checked In Successfully",
                                        attendance
                                )
                        );

                } catch (RuntimeException e) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                new ApiResponse<>(
                                        "ERROR",
                                        e.getMessage(),
                                        null
                                )
                        );
                }
        }

        @PostMapping("/check-out/{employeeId}")
        public ResponseEntity<ApiResponse<Attendance>> checkOut(
                @PathVariable long employeeId) {

                try {

                Attendance attendance =
                        attendanceService.checkout(employeeId);

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                "SUCCESS",
                                "Checked Out Successfully",
                                attendance
                        )
                );

                } catch (RuntimeException e) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                new ApiResponse<>(
                                        "ERROR",
                                        e.getMessage(),
                                        null
                                )
                        );
                }
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<Attendance>>
        getAttendanceById(
                @PathVariable long id) {

                Optional<Attendance> attendance =
                        attendanceService.getAttendanceById(id);

                if (attendance.isPresent()) {

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                "SUCCESS",
                                "Attendance Retrieved Successfully",
                                attendance.get()
                        )
                );
                }

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                new ApiResponse<>(
                                        "ERROR",
                                        "Attendance not found",
                                        null
                                )
                        );
        }

        @GetMapping("/employee/{employeeId}")
        public ResponseEntity<ApiResponse<List<Attendance>>>
        getAllAttendance(
                @PathVariable long employeeId) {

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                "SUCCESS",
                                "Attendance History Retrieved Successfully",
                                attendanceService
                                        .getAllAttendance(employeeId)
                        )
                );
        }

        @GetMapping("/today")
        public ResponseEntity<ApiResponse<List<Attendance>>>
        getTodayAttendance() {

                String today =
                        LocalDate.now().toString();

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                "SUCCESS",
                                "Today's Attendance Retrieved Successfully",
                                attendanceService
                                        .getTodayPresentEmployees(today)
                        )
                );
        }

        @GetMapping("/today/count")
        public ResponseEntity<ApiResponse<Long>>
        getTodayPresentCount() {

                String today =
                        LocalDate.now().toString();

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                "SUCCESS",
                                "Today's Present Count Retrieved Successfully",
                                attendanceService
                                        .getTodayPresentCount(today)
                        )
                );
        }

        @GetMapping("/today/total")
        public ResponseEntity<ApiResponse<Long>>
        getTodayAttendanceCount() {

                String today =
                        LocalDate.now().toString();

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                "SUCCESS",
                                "Today's Attendance Count Retrieved Successfully",
                                attendanceService
                                        .getTodayAttendanceCount(today)
                        )
                );
        }

        @GetMapping("/today/summary")
        public ResponseEntity<ApiResponse<Map<String, Long>>>
        getTodayAttendanceSummary() {

    String today =LocalDate.now().toString();
    long total = attendanceService.getTodayAttendanceCount(today);
    long present = attendanceService.countByDateAndStatus(today,"Present" );
    long late = attendanceService.countByDateAndStatus( today,"Late");

    long completed = attendanceService.countByDateAndStatus(today, "Completed");
    long leave = attendanceService.countByDateAndStatus(today,"Leave" );
    long halfDay =attendanceService.countByDateAndStatus(today,"Half Day" );

    long absent =attendanceService.countByDateAndStatus(  today,"Absent");

    Map<String, Long> summary = new java.util.HashMap<>();

    summary.put("totalCount", total);
    summary.put("presentCount", present + late + completed);
    summary.put("absentCount", absent);
    summary.put("leaveCount", leave);
    summary.put("halfDayCount", halfDay);

    return ResponseEntity.ok( new ApiResponse<>("SUCCESS","Today's Attendance Summary Retrieved Successfully", summary ));
}

}