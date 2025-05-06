package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.UserDetailsDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.integration.MailIntegration;
import com.ads.clien.plus.model.jpa.UserCredentials;
import com.ads.clien.plus.model.redis.UserRecoveryCode;
import com.ads.clien.plus.repository.jpa.UserDetailsRepository;
import com.ads.clien.plus.repository.redis.UserRecoveryCodeRepository;
import com.ads.clien.plus.service.UserDetailsService;
import com.ads.clien.plus.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${webservices.ads.redis.recovery.timout}")
    private String recoveryCodeTimeout;

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;
    @Autowired
    private MailIntegration mailIntegration;

    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {

        var userCredentialsOpt = userDetailsRepository.findByUsername(username);

        if (userCredentialsOpt.isEmpty()) {
            throw new NotFoundExceptionAds("Usuário não encontrado");
        }
        UserCredentials userCredentials = userCredentialsOpt.get();

        if (PasswordUtils.matches(pass, userCredentials.getPassword())){
            return userCredentials;
        }
        throw new BadReqequestExceptionAds("Usuário ou senha inválido");
    }
    @Override
    public void sendRecovery(String email) {

        UserRecoveryCode userRecoveryCode;
        String code = String.format("%04d", new Random().nextInt(10000));

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
        if (userRecoveryCodeOpt.isEmpty()){
            var userOpt = userDetailsRepository.findByUsername(email);
            if (userOpt.isEmpty()){
                throw new NotFoundExceptionAds("Usuario não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);
        }else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }
        userRecoveryCode.setCode(code);


        userRecoveryCode.setCreateDate(LocalDateTime.now());
        // numero aleatorio

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.sendMail(email, "Código de recuperação de conta: " + code , "Código de recuperação de conta");

    }

    @Override
    public boolean recoveryCodeIsValid(String recoveryCode, String email) {
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()){
            throw new NotFoundExceptionAds("Usuario não encontrado");
        }
        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();

       // validar tempo de vida do codigo
       LocalDateTime timout = userRecoveryCode.getCreateDate().plusMinutes(Long.parseLong(recoveryCodeTimeout));
       LocalDateTime now = LocalDateTime.now();
       return recoveryCode.equals(userRecoveryCode.getCode()) && now.isBefore(timout);
    }

    @Override
    public void updatePasswordByRecoveryCode(UserDetailsDTO userDetailsDto) {
        if (recoveryCodeIsValid(userDetailsDto.getRecoveryCode(), userDetailsDto.getEmail())){
            var userOpt = userDetailsRepository.findByUsername(userDetailsDto.getEmail());
            if (userOpt.isEmpty()){
                throw new NotFoundExceptionAds("Usuario não encontrado");
            }
            UserCredentials userCredentials = userOpt.get();
            userCredentials.setPassword(PasswordUtils.encode(userDetailsDto.getPassword()));
            userDetailsRepository.save(userCredentials);
        }
    }
}
