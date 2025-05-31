package com.ads.clien.plus.service;

import com.ads.clien.plus.model.jpa.UserType;

import java.util.List;

public interface UserTypeService {
    // listar todos
    List<UserType> findAll();

    
//    // buscar por ID
//    UserType findById(Long id);
//    // criar
//    UserType create(UserTypeDTO dto);
//    // editar
//    UserType update(Long id, UserTypeDTO dto);
//    // deletar
//    void delete(Long id);
}
