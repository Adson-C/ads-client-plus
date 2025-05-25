package com.ads.clien.plus.controller;


import com.ads.clien.plus.dto.SubscriptionsTypeDTO;
import com.ads.clien.plus.model.jpa.SubscriptionsType;
import com.ads.clien.plus.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscriptions-type")
public class SubscriptionsTypeController {
    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    // retorna todos meus cadastros

    @GetMapping()
    public ResponseEntity<List<SubscriptionsType>> findAll() {
//        return ResponseEntity.ok().body(subscriptionsTypeRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findAll());
    }
    // retornado Id

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionsType> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findById(id));
    }
    // criando

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionsType> create(@Valid @RequestBody SubscriptionsTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.create(dto));
    }
    // atualizando

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionsType> update(@Valid @PathVariable("id") Long id, @RequestBody SubscriptionsTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.update(id, dto));
    }

    // deletando

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        subscriptionTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
