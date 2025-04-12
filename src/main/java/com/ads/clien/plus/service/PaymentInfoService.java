package com.ads.clien.plus.service;

import com.ads.clien.plus.dto.PaymentProcessDTO;

public interface PaymentInfoService {

    Boolean processPayment(PaymentProcessDTO dto);
}
