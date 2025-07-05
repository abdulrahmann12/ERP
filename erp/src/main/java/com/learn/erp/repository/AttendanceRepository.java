package com.learn.erp.repository;


import com.learn.erp.model.Attendance;
import com.learn.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByUserAndDate(User user, LocalDate date);

    List<Attendance> findAllByDate(LocalDate date);

    List<Attendance> findAllByUser(User user);
}
