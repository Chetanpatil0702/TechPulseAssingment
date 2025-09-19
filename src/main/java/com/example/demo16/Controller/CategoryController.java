package com.example.demo16.Controller;

import com.example.demo16.Dto.Category.CategoryDto;
import com.example.demo16.Dto.Category.CategoryResponseDto;
import com.example.demo16.Responce.GenericResponse;
import com.example.demo16.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // --- Create a new category ---
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CATEGORY:CREATE')")
    public ResponseEntity<GenericResponse> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "Category Saved Successfully."));
    }

    // --- Get category by ID ---
    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('CATEGORY:READ')")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer id) {
        CategoryResponseDto dto = categoryService.getCategorieById(id);
        return ResponseEntity.ok(dto);
    }

    // --- Get all categories (paginated) ---
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('CATEGORY:READ')")
    public ResponseEntity<Page<CategoryDto>> getAllCategories(Pageable pageable) {
        Page<CategoryDto> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    // --- Get category by name ---
    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('CATEGORY:READ')")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        CategoryDto categoryDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(categoryDto);
    }

    // --- Update category ---
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
    public ResponseEntity<GenericResponse> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDto dto) {
        categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(new GenericResponse(true, "Category updated..!"));
    }

    // --- Delete category ---
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY:DELETE')")
    public ResponseEntity<GenericResponse> deleteCategoryById(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new GenericResponse(true, "Category deleted successfully"));
    }
}
