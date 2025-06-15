package com.shifterwebapp.shifter.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentByUser_Id(Long userId);

    @Query("select p from Payment p where p.enrollment.course.id = :courseId")
    List<Payment> findPaymentByCourse(@Param("courseId") Long courseId);

    @Query("select sum(p.amount) from Payment p where p.enrollment.course.id = :courseId")
    Double findTotalRevenueByCourse(@Param("courseId") Long courseId);

    @Query("select sum(p.amount) from Payment p where p.enrollment.course.id = :courseId and function('month', p.date) = :month and function('year', p.date) = :year")
    Double findTotalMonthlyRevenueByCourse(@Param("courseId") Long courseId, @Param("month") Integer month, @Param("year") Integer year);

    @Query("select sum(p.amount) from Payment p where p.enrollment.course.id = :courseId and function('year', p.date) = :year")
    Double findTotalYearlyRevenueByCourse(@Param("courseId") Long courseId, @Param("year") Integer year);

    @Query("select case when p.paymentStatus = 'COMPLETED' then true else false end from Payment p where p.user.id = :userId and p.enrollment.course.id = :courseId")
    Boolean findHasUserPaidForCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
