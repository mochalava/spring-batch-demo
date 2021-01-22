package com.example.batchdemo.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="foods")
public class FoodEntity {
    
    @Id
    private UUID id;

    private String name;
    
    private boolean active;
}
