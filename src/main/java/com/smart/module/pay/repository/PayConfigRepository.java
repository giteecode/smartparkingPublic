package com.smart.module.pay.repository;

import com.smart.module.pay.entity.AppPayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayConfigRepository extends JpaRepository<AppPayConfig, Long> {

    AppPayConfig findByCarParkId(Long carParkId);

}
