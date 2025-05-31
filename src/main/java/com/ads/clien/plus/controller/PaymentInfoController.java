package com.ads.clien.plus.controller;

import com.ads.clien.plus.configuration.SwaggerConfig;
import com.ads.clien.plus.dto.PaymentProcessDTO;
import com.ads.clien.plus.service.PaymentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = SwaggerConfig.PAYMENTINFO)
@RestController
@RequestMapping("/payment")
public class PaymentInfoController {
    private final PaymentInfoService paymentInfoService;

    public PaymentInfoController(PaymentInfoService paymentInfoService) {
        this.paymentInfoService = paymentInfoService;
    }

    @ApiOperation(value = "Processa o pagamento", notes = "Processa o pagamento do usuário.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Pagamento processado com sucesso."),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Requisição inválida, dados de pagamento ausentes ou inválidos."),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PostMapping("/process")
    public ResponseEntity<Boolean> processPayment(@RequestBody PaymentProcessDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(paymentInfoService.processPayment(dto));
    }
}
