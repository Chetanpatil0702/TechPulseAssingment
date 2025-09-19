package com.example.demo16.Service.Impl;

import com.example.demo16.Dto.Category.ProductDto;
import com.example.demo16.Entity.Category;
import com.example.demo16.Entity.Product;
import com.example.demo16.Exception.DuplicateResourseException;
import com.example.demo16.Exception.ResourceNotFoundException;
import com.example.demo16.Mapper.ProductMapper;
import com.example.demo16.Repository.CategoryRepository;
import com.example.demo16.Repository.ProductRepository;
import com.example.demo16.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;



    @Override
    @Transactional
    public ProductDto addProducts(ProductDto dto) {

        boolean productExits = productRepository.existsByProductName(dto.getProductName());

        if (productExits) {
            throw new DuplicateResourseException("This product is alredy exists:" + dto.getProductName());
        }


        Category category = categoryRepository.findByCategoryName(dto.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found: " + dto.getCategoryName()
                ));
        Product product = productMapper.toEntity(dto, category);
        productRepository.save(product);

        return dto;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);

        if (productsPage.isEmpty()) {
            throw new ResourceNotFoundException("Products not founds...!");
        }

        return productsPage.map(productMapper::toDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Integer id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("This id product not exists"));


        ProductDto productDto =  productMapper.toDTO(product);
        return productDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductByName(String productName)
    {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(()-> new ResourceNotFoundException("This product does not exists: "+productName ));

         ProductDto productDto = productMapper.toDTO(product);
         return productDto;
    }

    @Override
    @Transactional()
    public ProductDto updateProduct(Integer id, ProductDto dto)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("This product id not exist: "+id));

        Category category = categoryRepository.findByCategoryName(dto.getCategoryName())
                .orElseThrow(()-> new ResourceNotFoundException("This category does not exists: "+dto.getCategoryName()));

        productMapper.updateEntity(product, dto, category);

        productRepository.save(product);

        return dto;

    }


}
