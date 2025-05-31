package com.ads.clien.plus.controller;


import com.ads.clien.plus.configuration.SwaggerConfig;
import com.ads.clien.plus.dto.SubscriptionsTypeDTO;
import com.ads.clien.plus.model.jpa.SubscriptionsType;
import com.ads.clien.plus.service.SubscriptionTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerConfig.SUBSCRIPTIONTYPE)
@RestController
@RequestMapping("/subscriptions-type")
public class SubscriptionsTypeController {
    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    // retorna todos meus cadastros

    @ApiOperation(value = "Listar todos os tipos de assinatura", notes = "Retorna a lista de todos os tipos de assinatura disponíveis no sistema.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de tipos de assinatura retornada com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @GetMapping()
    public ResponseEntity<List<SubscriptionsType>> findAl() {
//        return ResponseEntity.ok().body(subscriptionsTypeRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findAll());
    }

    // retornado Id
    @ApiOperation(value = "Buscar tipo de assinatura por ID", notes = "Retorna um tipo de assinatura específico baseado no ID fornecido.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de assinatura encontrado com sucesso."),
            @ApiResponse(code = 404, message = "Tipo de assinatura não encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<SubscriptionsType> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findById(id));
    }

    // criando
    @ApiOperation(value = "Criar novo tipo de assinatura", notes = "Cria um novo tipo de assinatura com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tipo de assinatura criado com sucesso."),
            @ApiResponse(code = 400, message = "Requisição inválida, dados ausentes ou inválidos."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionsType> create(@Valid @RequestBody SubscriptionsTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.create(dto));
    }
    // atualizando
    @ApiOperation(value = "Atualizar tipo de assinatura", notes = "Atualiza um tipo de assinatura existente com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de assinatura atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Requisição inválida, dados ausentes ou inválidos."),
            @ApiResponse(code = 404, message = "Tipo de assinatura não encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionsType> update(@Valid @PathVariable("id") Long id, @RequestBody SubscriptionsTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.update(id, dto));
    }

    // deletando
    @ApiOperation(value = "Excluir tipo de assinatura", notes = "Remove um tipo de assinatura do sistema baseado no ID fornecido.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Tipo de assinatura excluído com sucesso."),
            @ApiResponse(code = 404, message = "Tipo de assinatura não encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor.")
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        subscriptionTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
