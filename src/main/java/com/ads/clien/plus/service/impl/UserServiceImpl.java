package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.mapper.UserMapper;
import com.ads.clien.plus.model.jpa.User;
import com.ads.clien.plus.model.jpa.UserType;
import com.ads.clien.plus.repository.jpa.UserRepository;
import com.ads.clien.plus.repository.jpa.UserTypeRepository;
import com.ads.clien.plus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // criar minha constantes
    private static final String PNG = ".png";
    private static final String JPEG = ".jpeg";
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;


    @Override
    public User create(UserDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadReqequestExceptionAds("Id deve ser nulo");
        }
        // consultar se userType existe
        var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());
        if (userTypeOpt.isEmpty()) {
            throw new NotFoundExceptionAds("UserTypeId inexistente");
        }
        UserType userType = userTypeOpt.get();
        User user = UserMapper.fromDtoToEntity(dto, userType, null);
        return userRepository.save(user);
    }

    @Override
    public User uploadPhoto(Long id, MultipartFile file) throws IOException {

        String imgName = file.getOriginalFilename();
        String formaPNG = imgName.substring(imgName.length() - 4);
        String formaJPEG = imgName.substring(imgName.length() - 5);
        // fazer um if
        if (!PNG.equalsIgnoreCase(formaPNG) || JPEG.equalsIgnoreCase(formaJPEG)) {
            throw new BadReqequestExceptionAds("Imagem deve possuir formato JPEG ou PNG.");
        }
        // retorna User
        User user = findById(id);
        user.setPhotoName(file.getOriginalFilename());
       user.setPhoto(file.getBytes());
        return userRepository.save(user);
    }

    @Override
    public byte[] downloadPhoto(Long id) {
        User user = findById(id);
        if (Objects.isNull(user.getPhoto())) {
            throw new BadReqequestExceptionAds("Usuario não possui foto");
        }
        return user.getPhoto();
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionAds("Usuario não encontrado"));
    }
}
