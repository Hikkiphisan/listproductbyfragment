package org.example.productmanagementbyspringmvc.controller;
import org.example.productmanagementbyspringmvc.model.Product;
import org.example.productmanagementbyspringmvc.model.Product_have_Image;
import org.example.productmanagementbyspringmvc.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/home")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/new")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/form";
    }

    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        product.setId(id);
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam String name, Model model) {
        model.addAttribute("products", productService.searchByName(name));
        return "product/list＿apdung_fragment";
    }
}
