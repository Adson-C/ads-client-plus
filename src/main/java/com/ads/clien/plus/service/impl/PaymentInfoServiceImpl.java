package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.PaymentProcessDTO;
import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.dto.wsraspay.OrderDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;
import com.ads.clien.plus.exception.BusinessexceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.integration.WsRaspayIntegration;
import com.ads.clien.plus.mapper.UserPaymentInfoMapper;
import com.ads.clien.plus.mapper.raspay.CreditCardMapper;
import com.ads.clien.plus.mapper.raspay.CustomerMapper;
import com.ads.clien.plus.mapper.raspay.OrderMapper;
import com.ads.clien.plus.mapper.raspay.PaymentMapper;
import com.ads.clien.plus.model.User;
import com.ads.clien.plus.model.UserPaymentInfo;
import com.ads.clien.plus.repository.UserPaymentInfoRepository;
import com.ads.clien.plus.repository.UserRepository;
import com.ads.clien.plus.service.PaymentInfoService;

import java.util.Objects;

public class PaymentInfoServiceImpl implements PaymentInfoService {
    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository, WsRaspayIntegration wsRaspayIntegration){
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
    }
    @Override
    public Boolean processPayment(PaymentProcessDTO dto) {
        // verifica usuario por Id
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDTO().getUserId());
        if(userOpt.isEmpty()){
            throw new NotFoundExceptionAds("Usuario não encontrado");
        }
        // verifica se ele ja possui uma assinatura ativa
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionsType())){
            throw new BusinessexceptionAds("Erro Pagamento - Usuario ja possui uma assinatura ativa");
        }
        // criar ou atualiza usuario raspay
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        // cria o pedido de pagamento
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));
        // processa o pagamento
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDTO(), user.getCpf()));
        Boolean successPayement = wsRaspayIntegration.processPayment(paymentDto);

        if (successPayement){

            // salvar informacoes de pagamento
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDTO(), user);
            userPaymentInfoRepository.save(userPaymentInfo);
        }
        // enviar email de criação de conta
        // retorna true ou false do pagamento


        return null;
    }
}
