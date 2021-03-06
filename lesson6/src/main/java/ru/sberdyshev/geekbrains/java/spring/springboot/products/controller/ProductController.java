package ru.sberdyshev.geekbrains.java.spring.springboot.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberdyshev.geekbrains.java.spring.springboot.products.domain.Product;
import ru.sberdyshev.geekbrains.java.spring.springboot.products.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{product-id}/details")
    public String getProduct(@PathVariable(name = "product-id") Long productId, Model model) {
        logger.debug("Called GET /products/{}/details", productId);
        Optional<Long> optionalProductId = Optional.ofNullable(productId);
        model.addAttribute("product", (productService.findById(optionalProductId)).get());
        return "product_details";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        logger.debug("Called GET /products/new");
        model.addAttribute("product", new Product());
        return "product_add";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/products")
    public String addProduct(@RequestParam(value = "_method", required = false) String method,
                             @Valid Product product, BindingResult bindingResult) {
        logger.debug("Called POST /products, method \"{}\"", method);
        if (bindingResult.hasErrors()) {
            if (method == "put" || method == "delete") {
                return "product_details";
            }
            return "product_add";
        }
        if (method == null) {
            productService.insert(product);
        } else {
            switch (method) {
                case "put":
                    productService.update(product);
                    break;
                case "delete":
                    productService.delete(product);
                    break;
                default:
                    //todo throw an exception?
                    productService.insert(product);
            }
        }
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String getProductList(@RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                 @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                                 @RequestParam(value = "page", required = false) Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 Model model) {
        logger.debug("Called GET /products with min prices \"{}\", max prices \"{}\"", minPrice, maxPrice);
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        Optional<BigDecimal> optionalMinPrice = Optional.ofNullable(minPrice);
        Optional<BigDecimal> optionalMaxPrice = Optional.ofNullable(maxPrice);
        Optional<Integer> optionalPageNumber = Optional.ofNullable(pageNumber);
        Optional<Integer> optionalPageSize = Optional.ofNullable(pageSize);
        model.addAttribute("productPage", productService.getAllProductsByPageAndMinAndMaxPrice(optionalMinPrice, optionalMaxPrice, optionalPageNumber, optionalPageSize));
        return "product_list";
    }
}
