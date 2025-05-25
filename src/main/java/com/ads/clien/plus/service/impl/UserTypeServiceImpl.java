package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.model.jpa.UserType;
import com.ads.clien.plus.repository.jpa.UserTypeRepository;
import com.ads.clien.plus.service.UserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }
    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}
