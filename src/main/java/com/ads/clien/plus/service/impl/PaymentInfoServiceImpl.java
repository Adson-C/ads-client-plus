package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.PaymentProcessDTO;
import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.dto.wsraspay.OrderDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;
import com.ads.clien.plus.enums.UserTypeEnum;
import com.ads.clien.plus.exception.BusinessexceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.integration.MailIntegration;
import com.ads.clien.plus.integration.WsRaspayIntegration;
import com.ads.clien.plus.mapper.UserPaymentInfoMapper;
import com.ads.clien.plus.mapper.raspay.CreditCardMapper;
import com.ads.clien.plus.mapper.raspay.CustomerMapper;
import com.ads.clien.plus.mapper.raspay.OrderMapper;
import com.ads.clien.plus.mapper.raspay.PaymentMapper;
import com.ads.clien.plus.model.jpa.User;
import com.ads.clien.plus.model.jpa.UserCredentials;
import com.ads.clien.plus.model.jpa.UserPaymentInfo;
import com.ads.clien.plus.repository.jpa.*;
import com.ads.clien.plus.service.PaymentInfoService;
import com.ads.clien.plus.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {


    @Value("${webservices.ads.default.password}")
    private String defaultPass;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;
    private final UserDetailsRepository userDetailsRepository;
    private final UserTypeRepository userTypeRepository;
    private final SubscriptionsRepository subscriptionsRepository;

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                           WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration,
                           UserDetailsRepository userDetailsRepository, UserTypeRepository userTypeRepository, SubscriptionsRepository subscriptionsRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
        this.subscriptionsRepository = subscriptionsRepository;
    }

    @Override
    public Boolean processPayment(PaymentProcessDTO dto) {
        // verifica usuario por Id
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDTO().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundExceptionAds("Usuario não encontrado");
        }
        // verifica se ele ja possui uma assinatura ativa
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionsType())) {
            throw new BusinessexceptionAds("Erro Pagamento - Usuario ja possui uma assinatura ativa");
        }
        // criar ou atualiza usuario raspay
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        // cria o pedido de pagamento
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));
        // processa o pagamento
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDTO(), user.getCpf()));
        Boolean successPayement = wsRaspayIntegration.processPayment(paymentDto);

        if (Boolean.TRUE.equals(successPayement)) {

            // salvar informacoes de pagamento
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDTO(), user);
            userPaymentInfoRepository.save(userPaymentInfo);
            // enviar email de criação de conta

            var userTypeOpt = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());
            if (userTypeOpt.isEmpty()) {
                throw new NotFoundExceptionAds("User Type não encontrado");
            }
            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), PasswordUtils.encode(defaultPass), userTypeOpt.get());
            userDetailsRepository.save(userCredentials);
            // criar assinatura
            var subscriptionsOpt = subscriptionsRepository.findByProductKey(dto.getProductKey());
            if (subscriptionsOpt.isEmpty()) {
                throw new NotFoundExceptionAds("SubscriptionType não encontrado");
            }
            user.setSubscriptionsType(subscriptionsOpt.get());
            userRepository.save(user);

            mailIntegration.sendMail(user.getEmail(), "Usuario: " + user.getEmail() + " - Senha: " + defaultPass , "Acesso Liberado");
            return true;
        }
        return false;
    }
}
