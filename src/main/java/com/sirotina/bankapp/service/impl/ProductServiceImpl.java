package com.sirotina.bankapp.service.impl;

import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.entity.Product;
import com.sirotina.bankapp.mapper.ProductMapper;
import com.sirotina.bankapp.repository.ProductRepository;
import com.sirotina.bankapp.service.ProductService;
import com.sirotina.bankapp.service.exception.ErrorMessage;
import com.sirotina.bankapp.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.productsToProductDtos(products);
    }

    @Override
    public ProductDto getProductById(UUID id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        return productMapper.productToProductDto(product);
    }

    @Override
    public ProductDto create(ProductDto dto) {
        Product product = productMapper.productDtoToProduct(dto);
        product.setId(UUID.randomUUID());
        product.setCreatedAt(new Timestamp(new Date().getTime()));
        product.setUpdatedAt(product.getCreatedAt());
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public ProductDto editProductById(UUID id, ProductDto dto) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        product.setName(dto.getName());
        product.setStatus(dto.getStatus());
        product.setCurrencyCode(dto.getCurrencyCode());
        product.setInterestRate(dto.getInterestRate());
        product.setUpdatedAt(new Timestamp(new Date().getTime()));
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }
}
