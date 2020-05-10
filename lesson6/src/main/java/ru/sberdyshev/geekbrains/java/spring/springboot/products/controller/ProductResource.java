package ru.sberdyshev.geekbrains.java.spring.springboot.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.sberdyshev.geekbrains.java.spring.springboot.products.domain.Product;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.dto.GenericListDto;
import ru.sberdyshev.geekbrains.java.spring.springboot.products.dto.ProductDto;
import ru.sberdyshev.geekbrains.java.spring.springboot.products.service.ProductService;

import java.util.Optional;

@RequestMapping("/api/v1")
@RestController
public class ProductResource {
    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);
    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericListDto<Product> getAllProducts() {
        logger.debug("Called GET /api/v1/products");
        GenericListDto<Product> genericListDto = new GenericListDto<>(productService.getAllProducts());
        return genericListDto;
    }

    @GetMapping(path = "/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getProduct(@PathVariable Long productId) {
        logger.debug("Called GET /api/v1/products/{}", productId);
        Optional<Long> optionalProductId = Optional.ofNullable(productId);
        Optional<Product> optionalProduct = productService.findById(optionalProductId);
        ProductDto productDto = new ProductDto(optionalProduct.get().getId(),
                optionalProduct.get().getName(),
                optionalProduct.get().getDescription(),
                optionalProduct.get().getCost());
        return productDto;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/products/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto createProduct(@RequestBody Product product) {
        logger.debug("Called POST /api/v1/products/new with new product with id \"{}\", name \"{}\", descr \"{}\", cost \"{}\"",
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCost());
        Optional<Product> optionalProduct = productService.insert(product);
        ProductDto productDto = new ProductDto(optionalProduct.get().getId(),
                optionalProduct.get().getName(),
                optionalProduct.get().getDescription(),
                optionalProduct.get().getCost());
        return productDto;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping(path = "/products/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        logger.debug("Called PUT /api/v1/products/{} with product with id \"{}\", name \"{}\", descr \"{}\", cost \"{}\"",
                productId,
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCost());
        Optional<Product> optionalProduct = productService.update(product);
        ProductDto productDto = new ProductDto(optionalProduct.get().getId(),
                optionalProduct.get().getName(),
                optionalProduct.get().getDescription(),
                optionalProduct.get().getCost());
        return productDto;
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto deleteProduct(@PathVariable Long productId) {
        logger.debug("Called DELETE /api/v1/products/{}", productId);
        Optional<Long> optionalProductId = Optional.ofNullable(productId);
        Optional<Product> optionalProduct = productService.delete(optionalProductId);
        ProductDto productDto = new ProductDto(optionalProduct.get().getId(),
                optionalProduct.get().getName(),
                optionalProduct.get().getDescription(),
                optionalProduct.get().getCost());
        return productDto;
    }
}
