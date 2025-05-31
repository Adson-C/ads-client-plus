package com.ads.clien.plus.controller;

import com.ads.clien.plus.configuration.SwaggerConfig;
import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.model.jpa.User;
import com.ads.clien.plus.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = SwaggerConfig.USER)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // criar usuario
    @ApiOperation(value = "Criar novo usuário", notes = "Cria um novo usuário com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário criado com sucesso."),
            @ApiResponse(code = 400, message = "Requisição inválida, dados ausentes ou inválidos."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @ApiOperation(value = "Fazer upload de foto do usuário", notes = "Atualiza a foto de perfil do usuário com o ID fornecido.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto atualizada com sucesso."),
            @ApiResponse(code = 400, message = "Requisição inválida, arquivo ausente ou inválido."),
            @ApiResponse(code = 404, message = "Usuário não encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PatchMapping(value = "/{id}/uploadPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.uploadPhoto(id, file));
    }

    @ApiOperation(value = "Baixar foto do usuário", notes = "Retorna a foto de perfil do usuário com o ID fornecido.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto retornada com sucesso."),
            @ApiResponse(code = 404, message = "Usuário ou foto não encontrados."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @GetMapping(value = "/{id}/photo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable("id") Long id)  {
        return ResponseEntity.status(HttpStatus.OK).body(userService.downloadPhoto(id));
    }
}
