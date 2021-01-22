package com.example.batchdemo.repository;

import java.util.UUID;

import com.example.batchdemo.entity.PromoFoodEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoFoodRepository extends CrudRepository<PromoFoodEntity, UUID> {
    
}
