package com.shifterwebapp.shifter.payment.service;

import com.shifterwebapp.shifter.payment.PaymentDto;
import com.shifterwebapp.shifter.payment.enums.PaymentMethod;

import java.util.List;

public interface ImplPaymentService {
    List<PaymentDto> getPaymentsByUser(Long userId);
    List<PaymentDto> getPaymentsByCourse(Long courseId);

    Double getTotalRevenueByCourse(Long courseId);
    Double getTotalMonthlyRevenueByCourse(Long courseId, Integer month, Integer year);
    Double getTotalYearlyRevenueByCourse(Long courseId, Integer year);

    Boolean hasUserPaidForCourse(Long userId, Long courseId);

    PaymentDto initiatePayment(Long userId, Long courseId, PaymentMethod paymentMethod);
    PaymentDto completePayment(Long paymentId);
    PaymentDto failPayment(Long paymentId);
}
