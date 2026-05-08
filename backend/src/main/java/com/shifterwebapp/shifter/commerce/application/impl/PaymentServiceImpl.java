package com.shifterwebapp.shifter.commerce.application.impl;

import com.shifterwebapp.shifter.commerce.domain.Payment;
import com.shifterwebapp.shifter.commerce.web.response.PaymentResponse;
import com.shifterwebapp.shifter.commerce.domain.enums.PaymentMethod;

import java.util.List;

public interface PaymentServiceImpl {
    List<PaymentResponse> getPaymentsByUser(Long userId);
    List<PaymentResponse> getPaymentsByCourse(Long courseId);

    Double getTotalRevenueByCourse(Long courseId);
    Double getTotalMonthlyRevenueByCourse(Long courseId, Integer month, Integer year);
    Double getTotalYearlyRevenueByCourse(Long courseId, Integer year);

    Boolean hasUserPaidForCourse(Long userId, Long courseId);

    Payment initiatePayment(Long userId, Long courseId, PaymentMethod paymentMethod);
    PaymentResponse completePayment(Long paymentId);
    PaymentResponse failPayment(Long paymentId);
}
