package com.shifterwebapp.shifter.payment.controller;

import com.shifterwebapp.shifter.payment.dto.PaymentDto;
import com.shifterwebapp.shifter.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getPaymentsByUser(@RequestParam("userId") Long userId) {
        List<PaymentDto> paymentDtos = paymentService.getPaymentsByUser(userId);
        return ResponseEntity.ok(paymentDtos);
    }
}
