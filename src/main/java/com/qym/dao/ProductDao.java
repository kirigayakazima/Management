package com.qym.dao;

import com.qym.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductDao extends JpaRepository<Product,Integer> {

    Product findByName(String name);

    List<Product> findAllByName(String name);

    Product getById(Integer id);
}
