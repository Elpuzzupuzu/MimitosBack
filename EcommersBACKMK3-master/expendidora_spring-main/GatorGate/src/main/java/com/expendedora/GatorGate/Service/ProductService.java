package com.expendedora.GatorGate.Service;

import com.expendedora.GatorGate.Model.Product;
import com.expendedora.GatorGate.Model.ProductDTO;
import com.expendedora.GatorGate.Model.ProductRequest;
import com.expendedora.GatorGate.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public void purchaseProducts(List<ProductDTO> products) {
        for (ProductDTO productDTO : products) {
            Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
            product.setStock(product.getStock() - productDTO.getSold());
            productRepository.save(product);
        }
    }

    // Método para crear un nuevo producto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


}
