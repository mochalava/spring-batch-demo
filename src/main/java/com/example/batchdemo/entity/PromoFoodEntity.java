package com.example.batchdemo.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="promo_foods")
public class PromoFoodEntity {
    
    @Id
    private UUID id;

    private String name;
}
