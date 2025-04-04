package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.mapper.UserMapper;
import com.ads.clien.plus.model.User;
import com.ads.clien.plus.model.UserType;
import com.ads.clien.plus.repository.UserRepository;
import com.ads.clien.plus.repository.UserTypeRepository;
import com.ads.clien.plus.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }
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
}
