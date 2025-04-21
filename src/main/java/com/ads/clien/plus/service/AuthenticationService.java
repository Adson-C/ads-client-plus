package com.ads.clien.plus.service;

import com.ads.clien.plus.dto.LoginDTO;
import com.ads.clien.plus.dto.TokenDTO;

public interface AuthenticationService {

    TokenDTO auth(LoginDTO dto);

}
