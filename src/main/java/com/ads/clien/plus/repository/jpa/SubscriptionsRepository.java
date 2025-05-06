package com.ads.clien.plus.repository.jpa;

import com.ads.clien.plus.model.jpa.SubscriptionsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionsRepository extends JpaRepository<SubscriptionsType, Long> {

    Optional<SubscriptionsType> findByProductKey(String productKey);
    
}
