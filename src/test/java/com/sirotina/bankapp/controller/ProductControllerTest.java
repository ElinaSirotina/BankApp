package com.sirotina.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAll() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto());

        given(productService.getAll()).willReturn(products);

        mockMvc.perform(get("/api/products/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetProductById() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDto product = new ProductDto();
        product.setId(productId);

        given(productService.getProductById(productId)).willReturn(product);

        mockMvc.perform(get("/api/products/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productId.toString())));
    }

    @Test
    public void testCreate() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Product 1");

        given(productService.create(any(ProductDto.class))).willReturn(product);

        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Product 1")));
    }

    @Test
    public void testEditProductById() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 2");

        given(productService.editProductById(eq(productId), any(ProductDto.class))).willReturn(productDto);

        mockMvc.perform(put("/api/products/edit/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product 2")));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        UUID productId = UUID.randomUUID();

        mockMvc.perform(delete("/api/products/delete/{id}", productId))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
