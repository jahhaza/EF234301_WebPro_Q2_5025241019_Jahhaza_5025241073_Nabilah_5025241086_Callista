package com.freshandfix.service;

import com.freshandfix.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Initialize with sample products
        products.add(createProduct(1L, "Setra Ramos — 5 kg", 
            "Setra Ramos is a popular medium-texture white rice variety...", 
            new BigDecimal("85000"), "rice", "SetraRamos.jpg"));
        
        products.add(createProduct(2L, "Beras IR64 — 5 kg", 
            "IR64 is a long-established rice variety...", 
            new BigDecimal("92500"), "rice", "BerasIR64.jpg"));
            
        // Add more products...
    }

    private Product createProduct(Long id, String name, String description, 
                                 BigDecimal price, String category, String image) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setImage(image);
        product.setStockQuantity(100);
        product.setWholesaleInfo("Wholesale (≥11 units): Rp " + 
            price.multiply(new BigDecimal("0.9")).toBigInteger().toString());
        return product;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                      .filter(p -> p.getId().equals(id))
                      .findFirst();
    }

    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                      .filter(p -> category.equals("all") || p.getCategory().equals(category))
                      .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String query) {
        String lowerQuery = query.toLowerCase();
        return products.stream()
                      .filter(p -> p.getName().toLowerCase().contains(lowerQuery) ||
                                  p.getDescription().toLowerCase().contains(lowerQuery) ||
                                  p.getCategory().toLowerCase().contains(lowerQuery))
                      .collect(Collectors.toList());
    }

    public List<Product> getFlashSaleProducts() {
        return products.stream()
                      .filter(Product::getIsFlashSale)
                      .collect(Collectors.toList());
    }
}