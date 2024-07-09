package com.expendedora.GatorGate.Controller;

import com.expendedora.GatorGate.Model.Category;
import com.expendedora.GatorGate.Model.Product;
import com.expendedora.GatorGate.Model.ProductDTO;
import com.expendedora.GatorGate.Model.ProductRequest;
import com.expendedora.GatorGate.Service.CategoryService;
import com.expendedora.GatorGate.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000") // Ajusta el origen según sea necesario
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
   @Autowired
    private CategoryService categoryService;

   ////-------------------------------------------///////////////

    // ProductController.java
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.updateProduct(id, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }











   ///////////--- testing

    // Método para crear un nuevo producto
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PostMapping("/create2")
    public ResponseEntity<Product> createProduct2(@RequestBody ProductRequest product) {

        Product p= new Product();
        p.setImg(product.getImg());
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setStock(product.getStock());

        long id_category=product.getId_category();
        Category category = categoryService.getCategoryById(id_category);

        p.setCategory(category);
        Product createdProduct=productService.createProduct(p);


        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }





    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseProducts(@RequestBody List<ProductDTO> products) {
        try {
            productService.purchaseProducts(products);
            return ResponseEntity.ok("Purchase successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing purchase");
        }
    }

    @GetMapping("/summary")
    public List<ProductDTO> getAllProductSummaries() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getImg(),
                        product.getDescription(),
                        product.getSold(),
                        product.getStock(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/summary2")
    public List<ProductDTO> getAllProductSummaries2() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getImg(),
                        product.getDescription(),
                        product.getSold(),
                        product.getStock(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/getall")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }

    @GetMapping("/by-category")
    public List<Product> getProductsByCategory(@RequestParam String categoryName) {
        return productService.getProductsByCategory(categoryName);
    }
}
