package com.lab8.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    
    @Column
    private String category;

    @Column
    private String brand;

    @Column
    private int stock;

    @Column
    private Double price;

    @Column
    private String discounttype;

    
    private ProductDetail detail;
}
