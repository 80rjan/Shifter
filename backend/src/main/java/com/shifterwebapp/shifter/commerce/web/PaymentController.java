//package com.shifterwebapp.shifter.commerce.domain.controller;
//
//import com.shifterwebapp.shifter.commerce.web.response.PaymentResponse;
//import com.shifterwebapp.shifter.commerce.application.PaymentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("${api.base.path}/payment")
//public class PaymentController {
//
//    private final PaymentService paymentService;
//
//    @GetMapping
//    public ResponseEntity<?> getPaymentsByUser(@RequestParam("userId") Long userId) {
//        List<PaymentResponse> paymentDtos = paymentService.getPaymentsByUser(userId);
//        return ResponseEntity.ok(paymentDtos);
//    }
//}
