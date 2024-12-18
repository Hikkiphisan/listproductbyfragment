package org.example.productmanagementbyspringmvc.service;

import org.example.productmanagementbyspringmvc.model.Product_have_Image;

import java.util.ArrayList;
import java.util.List;

public class ProductService_have_image implements IProductService_have_Image {
    private final List<Product_have_Image> productHaveImages;

    public ProductService_have_image() {
        productHaveImages = new ArrayList<>();
    }

    @Override
    public List<Product_have_Image> findAll() {
        return productHaveImages;
    }

    @Override
    public void save(Product_have_Image product) {
        productHaveImages.add(product);
    }

    @Override
    public Product_have_Image findById(int id) {
        return productHaveImages.get(id);
    }

    @Override
    public void update(int id, Product_have_Image product) {
        int index = productHaveImages.indexOf(findById(id));
        productHaveImages.set(index, product);
    }

    @Override
    public void remove(int id) {
        productHaveImages.remove(findById(id));
    }
}