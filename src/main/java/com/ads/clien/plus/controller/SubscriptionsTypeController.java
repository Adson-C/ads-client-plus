package com.ads.clien.plus.controller;


import com.ads.clien.plus.model.SubscriptionsType;
import com.ads.clien.plus.repository.SubscriptionsRespository;
import com.ads.clien.plus.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionsType> findById(@PathVariable("id") Long id) {
        SubscriptionsType subscriptionsType = subscriptionTypeService.findById(id);
        if (Objects.nonNull(subscriptionsType)) {
            return ResponseEntity.status(HttpStatus.OK).body(subscriptionsType);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
