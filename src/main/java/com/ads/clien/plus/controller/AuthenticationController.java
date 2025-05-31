package com.ads.clien.plus.controller;

import com.ads.clien.plus.configuration.SwaggerConfig;
import com.ads.clien.plus.dto.LoginDTO;
import com.ads.clien.plus.dto.TokenDTO;
import com.ads.clien.plus.dto.UserDetailsDTO;
import com.ads.clien.plus.model.redis.UserRecoveryCode;
import com.ads.clien.plus.service.AuthenticationService;
import com.ads.clien.plus.service.UserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = SwaggerConfig.AUTHENTICATION)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDetailsService userDetailsService;

    //ducmentação do swagger
    @ApiOperation(value = "Autenticação de usuário", notes = "Realiza a autenticação do usuário e retorna um token JWT.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Autenticação Beare bem-sucedida, retorna o token JWT."),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Requisição inválida, dados de login ausentes ou inválidos."),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Não autorizado, credenciais inválidas."),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Valid LoginDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.auth(dto));
    }
    @ApiOperation(value = "Verifica se o token é válido", notes = "Verifica se o token JWT fornecido é válido.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Token válido."),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Token inválido ou expirado."),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PostMapping("/recovery-code/send")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody @Valid UserRecoveryCode dto) {
        userDetailsService.sendRecovery(dto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @ApiOperation(value = "Verifica se o código de recuperação é válido", notes = "Verifica se o código de recuperação fornecido é válido para o email especificado.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Código de recuperação válido."),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Código de recuperação inválido ou email não encontrado."),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @GetMapping("/recovery-code/")
    public ResponseEntity<?> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode, @RequestParam("email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.recoveryCodeIsValid(recoveryCode, email));
    }

    @ApiOperation(value = "Atualiza a senha do usuário usando o código de recuperação", notes = "Atualiza a senha do usuário com base no código de recuperação fornecido.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 204, message = "Senha atualizada com sucesso."),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Dados inválidos ou código de recuperação inválido."),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PatchMapping("/recovery-code/password")
    public ResponseEntity<?> updatePasswordByRecoveryCode(@RequestBody @Valid UserDetailsDTO dto) {
        userDetailsService.updatePasswordByRecoveryCode(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
