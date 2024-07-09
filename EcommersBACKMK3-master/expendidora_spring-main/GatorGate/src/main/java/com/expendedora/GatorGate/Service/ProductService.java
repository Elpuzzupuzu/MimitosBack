package com.expendedora.GatorGate.Service;


import com.expendedora.GatorGate.Model.Category;
import com.expendedora.GatorGate.Model.Product;
import com.expendedora.GatorGate.Model.ProductDTO;
import com.expendedora.GatorGate.Model.ProductRequest;
import com.expendedora.GatorGate.Repository.CategoryRepository;
import com.expendedora.GatorGate.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    private CategoryRepository categoryRepository;

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


    ///TESTING:

    // ProductService.java
    // ProductService.java
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepository.findById(id);
            Product product = optionalProduct.get();
            product.setImg(productRequest.getImg());
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setStock(productRequest.getStock());

            long idCategory = productRequest.getId_category();
            Category category = categoryService.getCategoryById(idCategory);
            product.setCategory(category);

            return productRepository.save(product);

    }
}