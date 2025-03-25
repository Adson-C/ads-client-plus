package com.ads.clien.plus.repository;

import com.ads.clien.plus.model.SubscriptionsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRespository extends JpaRepository<SubscriptionsType, Long> {
    
}
