package com.example.demo16.Controller;

import com.example.demo16.Dto.Category.ProductDto;
import com.example.demo16.Responce.GenericResponse;
import com.example.demo16.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // --- Add a new product ---
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRODUCT:CREATE')")
    public ResponseEntity<GenericResponse> addProduct(@Valid @RequestBody ProductDto dto) {
        productService.addProducts(dto);
        return ResponseEntity.ok(new GenericResponse(true,
                "New product added successfully: " + dto.getProductName()));
    }

    // --- Get all products (paginated) ---
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PRODUCT:READ')")
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        Page<ProductDto> productsPage = productService.getAllProducts(pageable);
        return ResponseEntity.ok(productsPage);
    }

    // --- Get product by ID ---
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT:READ')")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    // --- Update product details ---
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public ResponseEntity<GenericResponse> updateProductDetails(
            @PathVariable Integer id,
            @Valid @RequestBody ProductDto productDto) {

        productService.updateProduct(id, productDto);
        return ResponseEntity.ok(new GenericResponse(true, "Product details updated successfully!"));
    }
}
