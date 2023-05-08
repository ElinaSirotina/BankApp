package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.service.ProductService;
import com.sirotina.bankapp.service.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
@Tag(name="Products", description="interaction with products")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Получение всех продуктов",
            description = "Позволяет получить все продукты"
    )
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Получение продукта",
            description = "Позволяет получить продукт по id"
    )
    @GetMapping("product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) throws ProductNotFoundException {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Создание продукта",
            description = "Позволяет создать продукт"
    )
    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        ProductDto product = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(
            summary = "Редактирование продукта",
            description = "Позволяет редактировать продукт  по id"
    )
    @PutMapping("edit/{id}")
    public ResponseEntity<ProductDto> editProductById(@PathVariable UUID id, @RequestBody ProductDto dto) throws ProductNotFoundException {
        ProductDto product = productService.editProductById(id, dto);
        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Удаление продукта",
            description = "Позволяет удалить продукт по id"
    )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
