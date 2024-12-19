package org.example.productmanagementbyspringmvc.thungrac;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, "Laptop", 1200.0, "A high-performance laptop", "Dell"));
        products.add(new Product(2, "Phone", 800.0, "Latest smartphone", "Samsung"));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void update(Product product) {
        Product existing = findById(product.getId());
        if (existing != null) {
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            existing.setDescription(product.getDescription());
            existing.setManufacturer(product.getManufacturer());
        }
    }

    @Override
    public void deleteById(int id) {
        products.removeIf(p -> p.getId() == id);
    }


    //bịa ra
    @Override
    public List<Product> searchByName(String name) {
        List<Product> products1 = (List<Product>) new Product();
        return products1;
    }
}
