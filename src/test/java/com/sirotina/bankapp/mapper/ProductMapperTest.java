package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testProductToProductDto() {
        Product product = new Product();
        UUID id = UUID.randomUUID();
        product.setId(id);
        product.setName("Test Product");

        ProductDto productDto = productMapper.productToProductDto(product);

        assertNotNull(productDto);
        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getName(), productDto.getName());
    }

    @Test
    public void testProductsToProductDtos() {
        Product product1 = new Product();
        UUID id = UUID.randomUUID();
        product1.setId(id);
        product1.setName("Test Product 1");

        Product product2 = new Product();
        UUID id2 = UUID.randomUUID();
        product2.setId(id2);
        product2.setName("Test Product 2");

        List<Product> products = Arrays.asList(product1, product2);

        List<ProductDto> productDtos = productMapper.productsToProductDtos(products);

        assertNotNull(productDtos);
        assertEquals(products.size(), productDtos.size());

        assertEquals(products.get(0).getId(), productDtos.get(0).getId());
        assertEquals(products.get(0).getName(), productDtos.get(0).getName());

        assertEquals(products.get(1).getId(), productDtos.get(1).getId());
        assertEquals(products.get(1).getName(), productDtos.get(1).getName());
    }

    @Test
    public void testProductDtoToProduct() {
        ProductDto productDto = new ProductDto();
        UUID id = UUID.randomUUID();
        productDto.setId(id);
        productDto.setName("Test Product");

        Product product = productMapper.productDtoToProduct(productDto);

        assertNotNull(product);
        assertEquals(productDto.getId(), product.getId());
        assertEquals(productDto.getName(), product.getName());
    }
}
