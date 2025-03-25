package com.ads.clien.plus.repository;

import com.ads.clien.plus.model.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentInfoRespository extends JpaRepository<UserPaymentInfo, Long> {
    
}
