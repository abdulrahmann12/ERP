package com.learn.erp.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learn.erp.model.Bonus;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    List<Bonus> findByUser_IdAndMonthAndYear(Long userId, int month, int year);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bonus b WHERE b.user.id = :userId AND b.month = :month AND b.year = :year")
    BigDecimal findTotalBonusByUserIdAndMonthAndYear(@Param("userId") Long userId,
                                                     @Param("month") int month,
                                                     @Param("year") int year);
    List<Bonus> findByMonthAndYear(int month, int year);

}