package com.shifterwebapp.shifter.commerce.application.impl;

import com.shifterwebapp.shifter.commerce.domain.Payment;
import com.shifterwebapp.shifter.commerce.web.response.PaymentDto;
import com.shifterwebapp.shifter.commerce.domain.enums.PaymentMethod;

import java.util.List;

public interface ImplPaymentService {
    List<PaymentDto> getPaymentsByUser(Long userId);
    List<PaymentDto> getPaymentsByCourse(Long courseId);

    Double getTotalRevenueByCourse(Long courseId);
    Double getTotalMonthlyRevenueByCourse(Long courseId, Integer month, Integer year);
    Double getTotalYearlyRevenueByCourse(Long courseId, Integer year);

    Boolean hasUserPaidForCourse(Long userId, Long courseId);

    Payment initiatePayment(Long userId, Long courseId, PaymentMethod paymentMethod);
    PaymentDto completePayment(Long paymentId);
    PaymentDto failPayment(Long paymentId);
}
