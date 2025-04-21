package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.LoginDTO;
import com.ads.clien.plus.dto.TokenDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.model.UserCredentials;
import com.ads.clien.plus.service.AuthenticationService;
import com.ads.clien.plus.service.TokenService;
import com.ads.clien.plus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;

    @Override
    public TokenDTO auth(LoginDTO dto) {
        try {
            UserCredentials userCredentials = userDetailsService.loadUserByUsernameAndPass(dto.getUsername(), dto.getPassword());
            String token = tokenService.getToken(userCredentials.getId());
            return TokenDTO.builder().token(token).type("Bearer").build();
        } catch (Exception e) {
            throw new BadReqequestExceptionAds("Usuário ou senha inválidos" + e.getMessage());
        }
    }
}
