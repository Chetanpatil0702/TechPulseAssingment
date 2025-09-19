package com.example.demo16.Service.CategoryAndProduct;

import com.example.demo16.Dto.Category.ProductDto;
import com.example.demo16.Entity.Category;
import com.example.demo16.Entity.Product;
import com.example.demo16.Exception.DuplicateResourseException;
import com.example.demo16.Exception.ResourceNotFoundException;
import com.example.demo16.Mapper.ProductMapper;
import com.example.demo16.Repository.CategoryRepository;
import com.example.demo16.Repository.ProductRepository;
import com.example.demo16.Service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto productDto;
    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setProductName("Laptop");
        productDto.setCategoryName("Electronics");

        category = new Category();
        category.setCategoryName("Electronics");

        product = new Product();
        product.setProductName("Laptop");
        product.setCategory(category);
    }

    @Test
    void testAddProducts_Success() {
        // Lenient stubbing to avoid unnecessary stubbing warnings
        lenient().when(productRepository.existsByProductName("Laptop")).thenReturn(false);
        lenient().when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        lenient().when(productMapper.toEntity(productDto, category)).thenReturn(product);
        lenient().when(productRepository.save(product)).thenReturn(product);
        lenient().when(productMapper.toDTO(product)).thenReturn(productDto);

        ProductDto savedProduct = productService.addProducts(productDto);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("Laptop");

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testAddProducts_DuplicateProduct() {
        when(productRepository.existsByProductName("Laptop")).thenReturn(true);

        assertThatThrownBy(() -> productService.addProducts(productDto))
                .isInstanceOf(DuplicateResourseException.class)
                .hasMessageContaining("alredy exists"); // match the current typo

        verify(productRepository, never()).save(any());
    }


    @Test
    void testAddProducts_CategoryNotFound() {
        when(productRepository.existsByProductName("Laptop")).thenReturn(false);
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.addProducts(productDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found");

        verify(productRepository, never()).save(any());
    }
}

