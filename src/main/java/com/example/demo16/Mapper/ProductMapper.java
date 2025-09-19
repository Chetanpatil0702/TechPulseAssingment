package com.example.demo16.Mapper;

import com.example.demo16.Dto.Category.ProductDto;
import com.example.demo16.Entity.Category;
import com.example.demo16.Entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // DTO -> Entity
    public Product toEntity(ProductDto productDTO, Category category) {
        if (productDTO == null) return null;

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setCategory(category);
        return product;
    }

    // Entity -> DTO
    public ProductDto toDTO(Product product) {
        if (product == null) return null;

        ProductDto productDTO = new ProductDto();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductPrice(product.getProductPrice());
        if (product.getCategory() != null) {
            productDTO.setCategoryName(product.getCategory().getCategoryName());  // show category name
        }
        return productDTO;
    }

    public void updateEntity(Product product, ProductDto productDto, Category category)
    {
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setCategory(category);

    }
}
