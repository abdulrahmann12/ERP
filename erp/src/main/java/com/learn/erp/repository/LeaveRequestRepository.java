package com.learn.erp.repository;

import com.learn.erp.model.LeaveRequest;
import com.learn.erp.model.User;
import com.learn.erp.model.LeaveRequest.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByUser(User user);

    List<LeaveRequest> findByStatus(Status status);

    List<LeaveRequest> findByStartDateBetween(LocalDate start, LocalDate end);
}
