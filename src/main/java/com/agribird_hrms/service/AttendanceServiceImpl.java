package com.agribird_hrms.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.agribird_hrms.Repository.AttendanceRepository;
import com.agribird_hrms.Repository.EmployeeRepository;
import com.agribird_hrms.entity.Attendance;
import com.agribird_hrms.entity.Employee;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    private static final double OFFICE_LATITUDE = 21.156025;
    private static final double OFFICE_LONGITUDE = 74.430331;
    private static final double OFFICE_RADIUS_METERS = 1000.0;

    private static final String OFFICE_WIFI_1 = "AGRIBIRD_2.4G";
    private static final String OFFICE_WIFI_2 = "AGRIBIRD_5G";

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository) {

        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Attendance checkIn(
            long employeeId,
            double latitude,
            double longitude,
            String wifiSsid,
            boolean mockLocation) {

        if (mockLocation) {
            throw new RuntimeException(
                    "Fake GPS detected. Attendance blocked."
            );
        }

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        String todayDate = LocalDate.now().toString();

        Optional<Attendance> existing =
                attendanceRepository.findByEmployee_IdAndDate(
                        employeeId,
                        todayDate
                );

        if (existing.isPresent()) {
            throw new RuntimeException(
                    "Attendance already marked for today"
            );
        }

        double distance = calculateDistance(
                latitude,
                longitude,
                OFFICE_LATITUDE,
                OFFICE_LONGITUDE
        );

        boolean wifiMatched =
                OFFICE_WIFI_1.equalsIgnoreCase(wifiSsid)
                || OFFICE_WIFI_2.equalsIgnoreCase(wifiSsid);

        boolean withinOffice =
                distance <= OFFICE_RADIUS_METERS;

        if (!withinOffice && !wifiMatched) {
            throw new RuntimeException(
                    "You are not within the office premises. Attendance blocked."
            );
        }

        LocalTime currentTime = LocalTime.now();

        LocalTime officeStartTime =
                LocalTime.of(9, 30);

        String status =
                currentTime.isAfter(officeStartTime)
                        ? "Late"
                        : "Present";

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setDate(todayDate);
        attendance.setCheckInTime(
                currentTime.format(
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                )
        );
        attendance.setCheckOutTime("--:--");
        attendance.setStatus(status);
        attendance.setTotalHours("00h 00m");

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance checkout(long employeeId) {

        String todayDate = LocalDate.now().toString();

        Attendance attendance =
                attendanceRepository
                        .findByEmployee_IdAndDate(
                                employeeId,
                                todayDate
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Attendance not found for today"
                                ));

        if (attendance.getCheckOutTime() != null
                && !attendance.getCheckOutTime().equals("--:--")) {

            throw new RuntimeException(
                    "You have already checked out for today"
            );
        }

        LocalTime checkIn =
                LocalTime.parse(
                        attendance.getCheckInTime()
                );

        LocalTime checkOut =
                LocalTime.now();

        attendance.setCheckOutTime(
                checkOut.format(
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                )
        );

        long minutes =
                Duration.between(
                        checkIn,
                        checkOut
                ).toMinutes();

        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;

        attendance.setTotalHours(
                String.format(
                        "%02dh %02dm",
                        hours,
                        remainingMinutes
                )
        );

        if (!"Late".equalsIgnoreCase(
                attendance.getStatus())) {

            attendance.setStatus("Completed");
        }

        return attendanceRepository.save(attendance);
    }

    @Override
    public Optional<Attendance> getAttendanceById(long id) {

        return attendanceRepository.findById(id);
    }

    @Override
    public List<Attendance> getAllAttendance(
            long employeeId) {

        return attendanceRepository
                .findByEmployee_IdOrderByIdDesc(
                        employeeId
                );
    }

    @Override
    public List<Attendance> getTodayPresentEmployees(
            String date) {

        return attendanceRepository
                .findByDateAndStatus(
                        date,
                        "Present"
                );
    }

    @Override
    public long getTodayPresentCount(String date) {

        return attendanceRepository
                .findByDateAndStatus(date, "Present")
                .size();
    }

    @Override
    public long getTodayAttendanceCount(String date) {

        return attendanceRepository
                .countByDate(date);
    }

    private double calculateDistance(
            double lat1,
            double lon1,
            double lat2,
            double lon2) {

        final double R = 6371000;

        double latDistance =
                Math.toRadians(lat2 - lat1);

        double lonDistance =
                Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(latDistance / 2)
                        * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2)
                        * Math.sin(lonDistance / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return R * c;
    }

    @Override
    public long countByDateAndStatus(String date, String status) {
       return attendanceRepository.countByDateAndStatus(date, status);
    }
}