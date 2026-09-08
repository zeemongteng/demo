package com.lab8.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lab8.demo.model.*;
import com.lab8.demo.service.ProductService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Product product = new Product();
        product.setDetail(new ProductDetail());
        model.addAttribute("product", product);
        return "products/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product.getDetail() == null) {
            product.setDetail(new ProductDetail());
        }
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/delete";
    }

    // POST /products/delete/{id} — actually delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    // POST /products/{id}/reviews — small extra endpoint so the 1:N side
    // is actually exercised end-to-end from the edit page, not just displayed
    @PostMapping("/{id}/reviews")
    public String addReview(@PathVariable Long id,@RequestParam String reviewer,@RequestParam Integer rating,@RequestParam(required = false) String comment) {
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDate.now());
        productService.addReview(id, review);
        return "redirect:/products/edit/" + id;
    }
    
}
