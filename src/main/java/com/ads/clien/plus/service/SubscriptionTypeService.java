package com.ads.clien.plus.service;

import com.ads.clien.plus.dto.SubscriptionsTypeDTO;
import com.ads.clien.plus.model.jpa.SubscriptionsType;

import java.util.List;

public interface SubscriptionTypeService {

    // listar todos
    List<SubscriptionsType> findAll();
    // buscar por ID
    SubscriptionsType findById(Long id);
    // criar
    SubscriptionsType create(SubscriptionsTypeDTO dto);

    // editar
    SubscriptionsType update(Long id, SubscriptionsTypeDTO dto);
    // deletar
    void delete(Long id);
}
