package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.controller.SubscriptionsTypeController;
import com.ads.clien.plus.dto.SubscriptionsTypeDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.mapper.SubscriptionsTypeMapper;
import com.ads.clien.plus.model.jpa.SubscriptionsType;
import com.ads.clien.plus.repository.jpa.SubscriptionsRepository;
import com.ads.clien.plus.service.SubscriptionTypeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private final SubscriptionsRepository subscriptionsTypeRepository;

    SubscriptionTypeServiceImpl(SubscriptionsRepository subscriptionsTypeRepository) {
        this.subscriptionsTypeRepository = subscriptionsTypeRepository;
    }


    @Cacheable(value = "subscriptiontype")
    @Override
    public List<SubscriptionsType> findAll() {
        return subscriptionsTypeRepository.findAll();
    }

    @Cacheable(value = "subscriptiontype", key = "#id")
    @Override
    public SubscriptionsType findById(Long id) {
        return getSubscriptionsType(id).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionsTypeController.class).findById(id)).withSelfRel()
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionsTypeController.class).update(id, new SubscriptionsTypeDTO())).withRel(UPDATE)
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionsTypeController.class).delete(id)).withRel(DELETE)
        );
    }

    @CacheEvict(value = "subscriptiontype", allEntries = true)
    @Override
    public SubscriptionsType create(SubscriptionsTypeDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadReqequestExceptionAds("Id deve ser nulo");
        }
        return subscriptionsTypeRepository.save(SubscriptionsTypeMapper.fromDtoToEntity(dto));
    }

    @CacheEvict(value = "subscriptiontype", allEntries = true)
    @Override
    public SubscriptionsType update(Long id, SubscriptionsTypeDTO dto) {

        getSubscriptionsType(id);

        dto.setId(id);
        return subscriptionsTypeRepository.save(SubscriptionsTypeMapper.fromDtoToEntity(dto));
    }

    @CacheEvict(value = "subscriptiontype", allEntries = true)
    @Override
    public void delete(Long id) {
        getSubscriptionsType(id);
        subscriptionsTypeRepository.deleteById(id);
    }

    private SubscriptionsType getSubscriptionsType(Long id) {
        Optional<SubscriptionsType> optionalSubscriptionsType = subscriptionsTypeRepository.findById(id);
        if (optionalSubscriptionsType.isEmpty()) {
            throw new NotFoundExceptionAds("SubscriptionType Não encontrado");
        }
        return optionalSubscriptionsType.get();
    }
}
