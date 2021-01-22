package com.example.batchdemo.repository;

import java.util.UUID;

import com.example.batchdemo.entity.FoodEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, UUID> {
    public Page<FoodEntity> findByActive(boolean active, Pageable pageable);    
}
