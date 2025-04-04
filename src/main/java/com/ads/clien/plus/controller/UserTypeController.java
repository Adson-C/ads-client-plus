package com.ads.clien.plus.controller;

import com.ads.clien.plus.model.UserType;
import com.ads.clien.plus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usertype")
public class UserTypeController {

    @Autowired
    UserTypeService  userTypeService;

    // retorna todos meus cadastros
    @GetMapping
    public ResponseEntity<List<UserType>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.findAll());
    }


}
