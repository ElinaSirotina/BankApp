package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.ProductDto;
import com.sirotina.bankapp.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    List<ProductDto> productsToProductDtos(List<Product> products);

    Product productDtoToProduct(ProductDto productDto);

}
