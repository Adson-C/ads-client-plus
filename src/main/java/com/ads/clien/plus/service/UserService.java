package com.ads.clien.plus.service;

import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.model.jpa.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User create(UserDTO dto);

    User uploadPhoto(Long id, MultipartFile file) throws IOException;

     byte[] downloadPhoto(Long id);

}
