package ru.sberdyshev.geekbrains.spring.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberdyshev.geekbrains.spring.products.entity.Product;
import ru.sberdyshev.geekbrains.spring.products.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
//@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
//    @GetMapping
//    public String allPersons(@RequestParam("minPrice") BigDecimal minPrice,
//                             @RequestParam("maxPrice") BigDecimal maxPrice,
//                             Model model) {
//        model.addAttribute("products", productService.getAllPersons());
//        return "products";
//    }
//    @GetMapping("/form")
//    public String formPerson(Model model) {
//        model.addAttribute("product", new Product());
//        return "product";
//    }
//
//    @PostMapping("/form")
//    public String newPerson(@Valid Product product, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "product_add";
//        }
//
//        productService.insert(product);
//        return "redirect:/product";
//    }

    @GetMapping("/product/{product-id}/details")
    public String getProduct(@PathVariable(name = "product-id") Long productId, Model model) {
        logger.debug("Called /product/{}/details", productId);
        model.addAttribute("product", (productService.findById(productId)).get());
        return "product_details";
    }

    @GetMapping("/product/new")
    public String addProduct(Model model) {
        logger.debug("Called /product/new");
        model.addAttribute("product", new Product());
        return "product_add";
    }

    @PostMapping("/product")
    public String newProduct(@Valid Product product, BindingResult bindingResult) {
        logger.debug("Called /product");
        if (bindingResult.hasErrors()) {
            return "product_add";
        }
        productService.insert(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String getProductList(@RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                 @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 Model model) {
        logger.debug("Called /products with min prices \"{}\", max prices \"{}\"", minPrice, maxPrice);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("products", productService.getAllProductsByPageAndMinAndMaxPrice(minPrice, maxPrice, page, pageSize));
        return "product_list";
    }
}
