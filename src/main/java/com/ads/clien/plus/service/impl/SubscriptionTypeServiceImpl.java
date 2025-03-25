package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.model.SubscriptionsType;
import com.ads.clien.plus.repository.SubscriptionsRespository;
import com.ads.clien.plus.service.SubscriptionTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {


    private final SubscriptionsRespository subscriptionsTypeRepository;

    SubscriptionTypeServiceImpl(SubscriptionsRespository subscriptionsTypeRepository) {
        this.subscriptionsTypeRepository = subscriptionsTypeRepository;
    }

    @Override
    public List<SubscriptionsType> findAll() {
        return subscriptionsTypeRepository.findAll();
    }

    @Override
    public SubscriptionsType findById(Long id) {
        Optional<SubscriptionsType> optionalSubscriptionsType = subscriptionsTypeRepository.findById(id);
        if (optionalSubscriptionsType.isEmpty()) {
            return null;
        }
        return optionalSubscriptionsType.get();
    }

    @Override
    public SubscriptionsType create(SubscriptionsType subscriptionsType) {
        return null;
    }

    @Override
    public SubscriptionsType update(Long id, SubscriptionsType subscriptionsType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
