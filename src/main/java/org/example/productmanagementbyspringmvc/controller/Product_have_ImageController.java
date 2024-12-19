package org.example.productmanagementbyspringmvc.controller;

import org.example.productmanagementbyspringmvc.model.ProductForm_have_Image;
import org.example.productmanagementbyspringmvc.model.Product_have_Image;
import org.example.productmanagementbyspringmvc.service.IProductService_have_Image;
import org.example.productmanagementbyspringmvc.service.ProductService_have_image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    public String saveProduct(@ModelAttribute ProductForm_have_Image productForm) {
        MultipartFile multipartFile = productForm.getImage(); //Lấy tệp hình ảnh từ biểu mẫu (ProductForm_have_Image) được gửi từ giao diện người dùng.
        if (multipartFile.isEmpty()) {
            System.out.println("Uploaded file is empty.");
        } else {
            System.out.println("Uploaded file: " + multipartFile.getOriginalFilename());
        }

        String fileName = multipartFile.getOriginalFilename();
        try {
            System.out.println("File Upload Path: " + fileUpload);
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));   //Chuyển đổi nội dung của tệp hình ảnh thành một mảng byte, giúp việc sao chép dữ liệu dễ dàng.
//sẽ thực hiện việc sao chép nội dung tệp (ở đây là file ảnh được upload) từ productForm.getImage() vào một tệp mới trong hệ thống tệp tại đường dẫn được chỉ định bởi fileUpload + fileName.
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Product_have_Image product = new Product_have_Image(productForm.getId(), productForm.getName(),
                productForm.getDescription(), fileName);
        productServiceHaveImage.save(product);
        return "redirect:/producthaveImages"; // Chuyển hướng về route gốc
    }


//
//    //controllercuahau
//    @PostMapping("/add_ofHau")
//    public String addProduct(
//            @ModelAttribute("product") Product_have_Image product,
//            @RequestParam("image") MultipartFile file,
//            RedirectAttributes redirectAttributes) {
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Please select an image file to upload.");
//            return "redirect:/products/add";
//        }
//
//        try {
//            // Đảm bảo thư mục upload tồn tại
//            String uploadDir = "C://Users/maitr/Downloads/images";
//            Path uploadPath = Paths.get(uploadDir);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
////             Tạo tên file duy nhất để tránh trùng lặp
//            String originalFileName = file.getOriginalFilename();
//            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//            String fileName = UUID.randomUUID().toString() + fileExtension;
//
//            // Validate và lưu file
//            if (!isImageFile(originalFileName)) {
//                redirectAttributes.addFlashAttribute("errorMessage", "Invalid file type. Please upload an image file.");
//                return "redirect:/products/add";
//            }
//
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            // Lưu đường dẫn tương đối vào database
//            product.setImage("/images/" + fileName);
//            productService.addProduct(product);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
//        } catch (IOException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading image: " + e.getMessage());
//            return "redirect:/products/add";
//        }
//
//        return "redirect:/products";
//    }
//
//    private boolean isImageFile(String fileName) {
//        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//        return Arrays.asList("jpg", "jpeg", "png", "gif", "bmp").contains(fileExtension);
//    }
//
//

}