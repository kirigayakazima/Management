package com.qym.service;

import com.qym.dao.ProductDao;
import com.qym.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    Product findByName(String name);

    List<Product> findAll();

    Product save(Product product);

    Product getById(Integer id);

    void remove(Integer id);

    Product update(Product product);
}
