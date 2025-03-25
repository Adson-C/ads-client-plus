package com.ads.clien.plus.service;

import com.ads.clien.plus.model.SubscriptionsType;

import java.util.List;

public interface SubscriptionTypeService {

    // listar todos
    List<SubscriptionsType> findAll();
    // buscar por ID
    SubscriptionsType findById(Long id);
    // criar
    SubscriptionsType create(SubscriptionsType subscriptionsType);
    // editar
    SubscriptionsType update(Long id, SubscriptionsType subscriptionsType);
    // deletar
    void delete(Long id);
}
