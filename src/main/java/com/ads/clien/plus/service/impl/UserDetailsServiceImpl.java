package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.model.UserCredentials;
import com.ads.clien.plus.repository.UserDetailsRepository;
import com.ads.clien.plus.service.UserDetailsService;
import com.ads.clien.plus.utils.PasswordUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {
        var userCredentialsOpt = userDetailsRepository.findByUsername(username);

        if (userCredentialsOpt.isEmpty()) {
            throw new NotFoundExceptionAds("Usuário não encontrado");
        }

        UserCredentials userCredentials = userCredentialsOpt.get();

        if (PasswordUtils.matches(pass, userCredentials.getPassword())) {
            return userCredentials;
        }

        throw new BadReqequestExceptionAds("Usuário ou senha inválido");
    }
}
