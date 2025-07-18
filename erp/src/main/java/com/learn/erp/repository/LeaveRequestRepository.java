package com.learn.erp.repository;

import com.learn.erp.model.LeaveRequest;
import com.learn.erp.model.LeaveRequest.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    Page<LeaveRequest> findAll(Pageable pageable);

    Page<LeaveRequest> findByUser_Id(Long userId, Pageable pageable);

    Page<LeaveRequest> findByUser_IdAndStatus(Long userId, Status status, Pageable pageable);

    List<LeaveRequest> findByUser_IdAndStatus(Long userId, LeaveRequest.Status status);

    Page<LeaveRequest> findAllByStatus(Status status, Pageable pageable);


    Page<LeaveRequest> findByStatus(Status status, Pageable pageable);

    List<LeaveRequest> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate end, LocalDate start);
}
