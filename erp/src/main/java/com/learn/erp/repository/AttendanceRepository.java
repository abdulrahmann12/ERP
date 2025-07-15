package com.learn.erp.repository;


import com.learn.erp.model.Attendance;
import com.learn.erp.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	Page<Attendance> findAllByDate(LocalDate date, Pageable pageable);

	List<Attendance> findByDate(LocalDate date);
    Page<Attendance> findAllByUser_Id(Long userId, Pageable pageable);
    List<Attendance> findByDateAndCheckOutIsNull(LocalDate date);
    Optional<Attendance> findByUser_IdAndDate(Long userId, LocalDate date);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user = :user AND a.status = :status AND MONTH(a.date) = :month AND YEAR(a.date) = :year")
    long countByUserAndStatusAndMonthAndYear(@Param("user") User user,
                                             @Param("status") Attendance.Status status,
                                             @Param("month") int month,
                                             @Param("year") int year);
}
