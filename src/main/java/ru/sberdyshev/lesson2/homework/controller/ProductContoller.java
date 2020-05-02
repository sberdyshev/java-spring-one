package ru.sberdyshev.lesson2.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberdyshev.lesson2.homework.product.Product;
import ru.sberdyshev.lesson2.homework.product.ProductRepository;

@Controller
@RequestMapping("product")
public class ProductContoller {

    private final ProductRepository productRepository;

    @Autowired
    public ProductContoller(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String allProducts(Model model) {
        model.addAttribute("products", productRepository.getProducts());
        return "products";
    }

    @GetMapping("/{product-id}")
    public String allProducts(@PathVariable(name = "product-id") Integer productId, Model model) {
        model.addAttribute("product", productRepository.get(productId));
        return "product";
    }

    @GetMapping("/form")
    public String formProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping("/form")
    public String newPerson(Product product) {
        productRepository.insert(product);
        return "redirect:/product";
    }
}
