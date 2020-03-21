package com.qym.service.Impl;

import com.qym.dao.ProductDao;
import com.qym.pojo.Product;
import com.qym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductDao productDao;


    @Override
    public Product findByName(String name) {
        return productDao.findByName(name);
    }

    //index首页查出所有的卡种
    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product getById(Integer id) {
        return productDao.getById(id);
    }

    @Override
    public void remove(Integer id) {
        productDao.deleteById(id);
    }

    @Transactional
    @Override
    public Product update(Product product) {
        return productDao.save(product);
    }

}
