package com.lab8.demo.repository;

import com.lab8.demo.model.*;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>{}
