package com.ads.clien.plus.controller;

import com.ads.clien.plus.dto.PaymentProcessDTO;
import com.ads.clien.plus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentInfoController {
    private final PaymentInfoService paymentInfoService;

    public PaymentInfoController(PaymentInfoService paymentInfoService) {
        this.paymentInfoService = paymentInfoService;
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> processPayment(@RequestBody PaymentProcessDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(paymentInfoService.processPayment(dto));
    }
}
