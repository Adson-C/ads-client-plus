package com.ads.clien.plus.service;

import com.ads.clien.plus.model.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);
//
//    void sendRecoveryCode(String email);
//
//    boolean recoveryCodeIsValid(String recoveryCode, String email);
//
//    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);

}
