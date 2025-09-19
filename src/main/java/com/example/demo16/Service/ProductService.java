package com.example.demo16.Service;


import com.example.demo16.Dto.Category.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto addProducts(ProductDto dto);

    Page<ProductDto> getAllProducts(Pageable pageable);

    ProductDto getProductById(Integer id);

    ProductDto getProductByName(String productName);

    ProductDto updateProduct(Integer id, ProductDto dto);
}
