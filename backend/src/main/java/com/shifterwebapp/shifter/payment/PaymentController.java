package com.shifterwebapp.shifter.payment;

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
    public ResponseEntity<?> getPaymentsByAccount(@RequestParam("accountId") Long accountId) {
        List<PaymentDto> paymentDtos = paymentService.getPaymentsByAccount(accountId);
        return ResponseEntity.ok(paymentDtos);
    }
}
