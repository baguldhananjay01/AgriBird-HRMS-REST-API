package com.agribird_hrms.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribird_hrms.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployee_IdAndDate(long employeeId, String date);

    List<Attendance> findByEmployee_IdOrderByIdDesc(long employeeId);

    List<Attendance> findByDate(String date);

    List<Attendance> findByDateAndStatus(String date, String status);

    long countByDate(String date);
    long countByDateAndStatus(String date, String status);
}