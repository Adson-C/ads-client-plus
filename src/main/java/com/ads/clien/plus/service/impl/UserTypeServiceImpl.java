package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.model.UserType;
import com.ads.clien.plus.repository.UserTypeRepository;
import com.ads.clien.plus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
