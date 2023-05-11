package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.entity.Product;
import com.sirotina.bankapp.entity.enums.CurrencyCode;
import com.sirotina.bankapp.entity.enums.ProductStatus;
import com.sirotina.bankapp.mapper.ProductMapper;
import com.sirotina.bankapp.repository.ProductRepository;
import com.sirotina.bankapp.service.exception.ProductNotFoundException;
import com.sirotina.bankapp.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testGetAll() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(new ProductDto());

        given(repository.findAll()).willReturn(products);
        given(mapper.productsToProductDtos(products)).willReturn(productDtos);

        List<ProductDto> result = productService.getAll();

        assertThat(result).isEqualTo(productDtos);
    }

    @Test
    public void testGetProductById() throws ProductNotFoundException {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);

        ProductDto productDto = new ProductDto();
        productDto.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(product));
        given(mapper.productToProductDto(product)).willReturn(productDto);

        ProductDto result = productService.getProductById(id);

        assertThat(result).isEqualTo(productDto);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductByIdNotFound() throws ProductNotFoundException {
        UUID id = UUID.randomUUID();
        given(repository.findById(id)).willReturn(Optional.empty());

        productService.getProductById(id);
    }

    @Test
    public void testCreate() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setStatus(ProductStatus.ACTIVE);
        productDto.setCurrencyCode(CurrencyCode.USD);
        productDto.setInterestRate(new BigDecimal("0.1"));

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setStatus(ProductStatus.ACTIVE);
        product.setCurrencyCode(CurrencyCode.USD);
        product.setInterestRate(new BigDecimal("0.1"));
        product.setCreatedAt(new Timestamp(new Date().getTime()));
        product.setUpdatedAt(product.getCreatedAt());

        given(mapper.productDtoToProduct(productDto)).willReturn(product);
        given(repository.save(product)).willReturn(product);
        given(mapper.productToProductDto(product)).willReturn(productDto);

        ProductDto result = productService.create(productDto);

        assertThat(result).isEqualTo(productDto);
    }

    @Test
    public void testEditProductById() throws ProductNotFoundException {
        UUID id = UUID.randomUUID();

        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setStatus(ProductStatus.ACTIVE);
        productDto.setCurrencyCode(CurrencyCode.USD);
        productDto.setInterestRate(new BigDecimal("0.1"));

        Product product = new Product();
        product.setId(id);
        product.setName("Old Product");
        product.setStatus(ProductStatus.REMOVED);
        product.setCurrencyCode(CurrencyCode.USD);
        product.setInterestRate(new BigDecimal("0.05"));
        product.setCreatedAt(new Timestamp(new Date().getTime() - 3600000));
        product.setUpdatedAt(new Timestamp(new Date().getTime() - 3600000));

        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setName("Test Product");
        updatedProduct.setStatus(ProductStatus.ACTIVE);
        updatedProduct.setCurrencyCode(CurrencyCode.USD);
        updatedProduct.setInterestRate(new BigDecimal("0.1"));
        updatedProduct.setCreatedAt(product.getCreatedAt());
        updatedProduct.setUpdatedAt(new Timestamp(new Date().getTime()));

        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setId(id);
        updatedProductDto.setName("Test Product");
        updatedProductDto.setStatus(ProductStatus.ACTIVE);
        updatedProductDto.setCurrencyCode(CurrencyCode.USD);
        updatedProductDto.setInterestRate(new BigDecimal("0.1"));

        given(repository.findById(id)).willReturn(Optional.of(product));
        given(mapper.productToProductDto(updatedProduct)).willReturn(updatedProductDto);
        given(repository.save(updatedProduct)).willReturn(updatedProduct);

        ProductDto result = productService.editProductById(id, productDto);

        assertThat(result).isEqualTo(updatedProductDto);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testEditProductByIdNotFound() throws ProductNotFoundException {
        UUID id = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        given(repository.findById(id)).willReturn(Optional.empty());

        productService.editProductById(id, productDto);
    }

    @Test
    public void testDeleteProductById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        productService.deleteProductById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
