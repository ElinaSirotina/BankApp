package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.entity.Product;
import com.sirotina.bankapp.service.exception.ProductNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto getProductById(UUID id) throws ProductNotFoundException;

    ProductDto create(ProductDto dto);

    ProductDto editProductById(UUID id, ProductDto dto) throws ProductNotFoundException;

    void deleteProductById(UUID id);

}
