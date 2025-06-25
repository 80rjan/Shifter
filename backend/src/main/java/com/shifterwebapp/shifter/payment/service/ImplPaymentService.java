package com.shifterwebapp.shifter.payment.service;

import com.shifterwebapp.shifter.payment.PaymentDto;
import com.shifterwebapp.shifter.enums.PaymentMethod;

import java.util.List;

public interface ImplPaymentService {
    List<PaymentDto> getPaymentsByAccount(Long accountId);
    List<PaymentDto> getPaymentsByCourse(Long courseId);

    Double getTotalRevenueByCourse(Long courseId);
    Double getTotalMonthlyRevenueByCourse(Long courseId, Integer month, Integer year);
    Double getTotalYearlyRevenueByCourse(Long courseId, Integer year);

    Boolean hasAccountPaidForCourse(Long accountId, Long courseId);

    PaymentDto initiatePayment(Long accountId, Long courseId, PaymentMethod paymentMethod);
    PaymentDto completePayment(Long paymentId);
    PaymentDto failPayment(Long paymentId);
}
