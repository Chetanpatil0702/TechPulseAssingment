package com.example.demo16.Service;

import com.example.demo16.Dto.Category.CategoryDto;
import com.example.demo16.Dto.Category.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {


    CategoryDto createCategory( CategoryDto categoryDto);

    Page<CategoryDto> getAllCategories(Pageable pagable);

    CategoryDto getCategoryByName(String name);


    CategoryResponseDto getCategorieById(Integer id);

    CategoryDto updateCategory(Integer id, CategoryDto dto);

    void deleteCategoryById(Integer id);
}
