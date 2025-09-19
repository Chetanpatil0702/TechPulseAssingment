package com.example.demo16.Dto.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

    private Integer id;

    @NotBlank(message = "Product name cannot be empty")
    private String productName;

    @NotNull(message = "Product price cannot be null")
    @Min(value = 1, message = "Product price must be greater than 0")
    private Double productPrice;

    @NotBlank(message = "Category name cannot be empty")
    private String categoryName;



    // getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getProductPrice() { return productPrice; }
    public void setProductPrice(Double productPrice) { this.productPrice = productPrice; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
