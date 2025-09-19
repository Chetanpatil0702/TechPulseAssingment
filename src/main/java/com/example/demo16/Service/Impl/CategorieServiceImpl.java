package com.example.demo16.Service.Impl;

import com.example.demo16.Dto.Category.CategoryDto;
import com.example.demo16.Dto.Category.CategoryResponseDto;
import com.example.demo16.Entity.Category;
import com.example.demo16.Exception.DuplicateResourseException;
import com.example.demo16.Exception.ResourceNotFoundException;
import com.example.demo16.Mapper.CategoryMapper;
import com.example.demo16.Repository.CategoryRepository;
import com.example.demo16.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategorieServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;



    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new DuplicateResourseException(
                    "This category already exists: " + categoryDto.getCategoryName()
            );
        }

        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());

        Category saved = categoryRepository.save(category);

        // Convert saved entity to DTO
        CategoryDto dto = new CategoryDto();
        dto.setId(saved.getId());
        dto.setCategoryName(saved.getCategoryName());

        return dto; // <-- must return the DTO
    }


    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        if (categoryPage.isEmpty()) {
            throw new ResourceNotFoundException("No Categories found...!");
        }

        return categoryPage.map(categoryMapper::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new ResourceNotFoundException("This category does not exist..!"));

        return categoryMapper.toDto(category);
    }





    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategorieById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This category id does not exist..!"));

        CategoryResponseDto dto = new CategoryResponseDto();
        return categoryMapper.responseToDto(category,dto);
    }


    @Override
    @Transactional
    public CategoryDto updateCategory(Integer id, CategoryDto dto)
    {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("This catrgory does not exist..!"));




       category.setCategoryName(dto.getCategoryName());
        categoryRepository.save(category);
        return dto;
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer id)
    {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

         categoryRepository.deleteById(id);
    }


}
