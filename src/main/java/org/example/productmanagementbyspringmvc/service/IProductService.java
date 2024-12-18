package org.example.productmanagementbyspringmvc.service;

import org.example.productmanagementbyspringmvc.model.Product;
import org.example.productmanagementbyspringmvc.model.Product_have_Image;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product);
    void update(Product product);
    void deleteById(int id);
    List<Product> searchByName(String name);
}
