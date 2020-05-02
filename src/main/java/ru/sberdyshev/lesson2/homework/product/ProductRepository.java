package ru.sberdyshev.lesson2.homework.product;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProductRepository {
    private AtomicInteger identifier;
    private Map<Integer, Product> products;

    public ProductRepository() {
        identifier = new AtomicInteger();
        products = new ConcurrentHashMap<Integer, Product>();
        Product product = new Product(identifier.incrementAndGet(), "asd", 123);
        products.put(product.getId(), product);
        product = new Product(identifier.incrementAndGet(), "zxc", 456);
        products.put(product.getId(), product);
        product = new Product(identifier.incrementAndGet(), "qwe", 768);
        products.put(product.getId(), product);

    }

    public void insert(Product product) {
        product.setId(identifier.incrementAndGet());
        products.put(product.getId(), product);
    }

    public Product get(Integer productId) {
        return products.get(productId);
    }

    public List<Product> getProducts() {
        return new LinkedList<>(products.values());
    }
}
