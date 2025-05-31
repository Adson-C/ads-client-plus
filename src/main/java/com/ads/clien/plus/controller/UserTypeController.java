package com.ads.clien.plus.controller;

import com.ads.clien.plus.configuration.SwaggerConfig;
import com.ads.clien.plus.model.jpa.UserType;
import com.ads.clien.plus.service.UserTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = SwaggerConfig.USERTYPE)
@RestController
@RequestMapping("/usertype")
public class UserTypeController {

    @Autowired
    UserTypeService  userTypeService;

    @ApiOperation(value = "Listar todos os tipos de usuário", notes = "Retorna a lista de todos os tipos de usuário disponíveis no sistema.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de tipos de usuário retornada com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity<List<UserType>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.findAll());
    }


}
