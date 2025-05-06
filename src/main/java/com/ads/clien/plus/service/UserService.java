package com.ads.clien.plus.service;

import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.model.jpa.User;

public interface UserService {
    User create(UserDTO dto);

}
