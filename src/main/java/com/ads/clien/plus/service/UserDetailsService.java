package com.ads.clien.plus.service;

import com.ads.clien.plus.dto.UserDetailsDTO;
import com.ads.clien.plus.model.jpa.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);
    void sendRecovery(String email);

    boolean recoveryCodeIsValid(String recoveryCode, String email);

    void updatePasswordByRecoveryCode(UserDetailsDTO userDetailsDto);

}
