package org.example.productmanagementbyspringmvc.service;


import org.example.productmanagementbyspringmvc.model.Product_have_Image;

import java.util.List;

public interface IProductService_have_Image {
    List<Product_have_Image> findAll();

    void save(Product_have_Image product);

    Product_have_Image findById(int id);

    void update(int id, Product_have_Image product);

    void remove(int id);
}