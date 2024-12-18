package org.example.productmanagementbyspringmvc.controller;

import org.example.productmanagementbyspringmvc.model.ProductForm_have_Image;
import org.example.productmanagementbyspringmvc.model.Product_have_Image;
import org.example.productmanagementbyspringmvc.service.IProductService_have_Image;
import org.example.productmanagementbyspringmvc.service.ProductService_have_image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/producthaveImages")
public class Product_have_ImageController {
    @Value("${file-upload}")
    private String fileUpload;


    private final IProductService_have_Image productServiceHaveImage = new ProductService_have_image();

    @GetMapping("")
    public String index(Model model) {
        List<Product_have_Image> productshaveImages = productServiceHaveImage.findAll();
        model.addAttribute("productshaveImages", productshaveImages);
        return "index_have_images";
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/create_have_images");
        modelAndView.addObject("productForm", new ProductForm_have_Image());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute ProductForm_have_Image productForm) {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Product_have_Image product = new Product_have_Image(productForm.getId(), productForm.getName(),
                productForm.getDescription(), fileName);
        productServiceHaveImage.save(product);
        ModelAndView modelAndView = new ModelAndView("/create_have_images");
        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("message", "Created new product successfully !");
        return modelAndView;
    }


}