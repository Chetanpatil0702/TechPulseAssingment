package com.example.demo16.Mapper;

import com.example.demo16.Dto.Category.CategoryDto;
import com.example.demo16.Dto.Category.CategoryResponseDto;
import com.example.demo16.Dto.Category.ProductDto;
import com.example.demo16.Entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    // Convert Entity -> DTO
    public CategoryDto toDto(Category category) {
        if (category == null) return null;

        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    // Convert DTO -> Entity
    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    public CategoryResponseDto responseToDto(Category category, CategoryResponseDto dto)
    {
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());

        List<ProductDto> products = category.getProducts()
                .stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setProductName(product.getProductName());
                    productDto.setProductPrice(product.getProductPrice());
                    productDto.setCategoryName(product.getProductName());
                    return productDto;
                })
                .toList();

        dto.setProducts(products);

        return dto;
    }
}
