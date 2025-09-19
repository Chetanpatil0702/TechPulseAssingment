package com.example.demo16.Service.CategoryAndProduct;

import com.example.demo16.Dto.Category.CategoryDto;
import com.example.demo16.Dto.Category.CategoryResponseDto;
import com.example.demo16.Entity.Category;
import com.example.demo16.Exception.DuplicateResourseException;
import com.example.demo16.Exception.ResourceNotFoundException;
import com.example.demo16.Mapper.CategoryMapper;
import com.example.demo16.Repository.CategoryRepository;
import com.example.demo16.Service.Impl.CategorieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategorieServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategorieServiceImpl categorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //  createCategory - success
    @Test
    void testCreateCategory_Success() {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryName("Electronics");

        Category savedEntity = new Category();
        savedEntity.setId(1);
        savedEntity.setCategoryName("Electronics");

        when(categoryRepository.existsByCategoryName("Electronics")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedEntity);

        CategoryDto result = categorieService.createCategory(dto);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getCategoryName()).isEqualTo("Electronics");

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    //  createCategory - duplicate
    @Test
    void testCreateCategory_Duplicate() {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryName("Books");

        when(categoryRepository.existsByCategoryName("Books")).thenReturn(true);

        assertThatThrownBy(() -> categorieService.createCategory(dto))
                .isInstanceOf(DuplicateResourseException.class)
                .hasMessageContaining("already exists");

        verify(categoryRepository, never()).save(any(Category.class));
    }


    //  getAllCategories - success
    @Test
    void testGetAllCategories_Success() {
        Category category = new Category();
        category.setId(1);
        category.setCategoryName("Clothing");

        CategoryDto dto = new CategoryDto();
        dto.setId(1);
        dto.setCategoryName("Clothing");

        Page<Category> page = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(dto);

        Page<CategoryDto> result = categorieService.getAllCategories(PageRequest.of(0, 10));

        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).getCategoryName()).isEqualTo("Clothing");
    }

    //  getAllCategories - empty
    @Test
    void testGetAllCategories_Empty() {
        Page<Category> page = Page.empty();
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(page);

        assertThatThrownBy(() -> categorieService.getAllCategories(PageRequest.of(0, 10)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No Categories found");
    }

    //  getCategoryByName - success
    @Test
    void testGetCategoryByName_Success() {
        Category category = new Category();
        category.setId(1);
        category.setCategoryName("Sports");

        CategoryDto dto = new CategoryDto();
        dto.setId(1);
        dto.setCategoryName("Sports");

        when(categoryRepository.findByCategoryName("Sports")).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(dto);

        CategoryDto result = categorieService.getCategoryByName("Sports");

        assertThat(result.getCategoryName()).isEqualTo("Sports");
    }

    //  getCategoryByName - not found
    @Test
    void testGetCategoryByName_NotFound() {
        when(categoryRepository.findByCategoryName("Unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categorieService.getCategoryByName("Unknown"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("does not exist");
    }

    //  getCategoryById - success
    @Test
    void testGetCategoryById_Success() {
        Category category = new Category();
        category.setId(1);
        category.setCategoryName("Toys");

        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(1);
        dto.setCategoryName("Toys");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(categoryMapper.responseToDto(eq(category), any(CategoryResponseDto.class))).thenReturn(dto);

        CategoryResponseDto result = categorieService.getCategorieById(1);

        assertThat(result.getCategoryName()).isEqualTo("Toys");
    }

    //  updateCategory - success
    @Test
    void testUpdateCategory_Success() {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryName("Updated");

        Category category = new Category();
        category.setId(1);
        category.setCategoryName("Old");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDto result = categorieService.updateCategory(1, dto);

        assertThat(result.getCategoryName()).isEqualTo("Updated");
    }

    //  deleteCategory - success
    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.existsById(1)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1);

        categorieService.deleteCategoryById(1);

        verify(categoryRepository, times(1)).deleteById(1);
    }

    //  deleteCategory - not found
    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> categorieService.deleteCategoryById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found");
    }
}
