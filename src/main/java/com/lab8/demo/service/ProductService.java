package com.lab8.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lab8.demo.model.Product;
import com.lab8.demo.model.Review;
import com.lab8.demo.repository.ProductRepository;
import com.lab8.demo.repository.ReviewRepository;
import com.lab8.demo.strategy.DiscountContext;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final DiscountContext discountContext;

    public ProductService(ProductRepository productRepository,
        ReviewRepository reviewRepository,DiscountContext discountContext) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.discountContext = discountContext;
    }
    
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found: "));
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product){
        Product oldProduct = getProductById(id);

        oldProduct.setName(product.getName());
        oldProduct.setCategory(product.getCategory());
        oldProduct.setDiscountType(product.getDiscountType());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setDiscountType(product.getDiscountType());
        oldProduct.setBrand(product.getBrand());

        if(oldProduct.getDetail() == null){
            oldProduct.setDetail(product.getDetail());
        }else if(oldProduct.getDetail() != null){
            oldProduct.getDetail().setDescription(product.getDetail().getDescription());
            oldProduct.getDetail().setWarranty(product.getDetail().getWarranty());
            oldProduct.getDetail().setWeight(product.getDetail().getWeight());
            oldProduct.getDetail().setDimensions(product.getDetail().getDimensions());
            oldProduct.getDetail().setManufacturedCountry(product.getDetail().getManufacturedCountry());
        }

        return productRepository.save(oldProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Review addReview(Long productId, Review review) {
        Product product = getProductById(productId);
        review.setReviewDate(LocalDate.now());
        product.addReview(review);
        productRepository.save(product);
        return review;
    }//++

    public List<Review> getReviewsForProduct(Long productId){
        return reviewRepository.findByProductId(productId);
    }

    //strategy
    public double getDiscountedPrice(Product product){
        if (product.getPrice() == null) {
            return 0.0;
        }
        return discountContext.calculatePrice(product.getPrice(), product.getDiscountType());
    }
}