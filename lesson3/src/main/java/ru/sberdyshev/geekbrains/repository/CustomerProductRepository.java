package ru.sberdyshev.geekbrains.repository;

import ru.sberdyshev.geekbrains.entity.Customer;
import ru.sberdyshev.geekbrains.entity.Product;

import java.util.List;

public interface CustomerProductRepository {
    Customer getCustomerById(Long customerId);
    Product getProductById(Long productId);
    List<Customer> getCustomersByProductId (Long productId);
    List<Product> getProductsByCustomerId (Long customerId);
    boolean deleteCustomerByCustomerId (Long customerId);
    boolean deleteProductByProductId (Long productId);
    List<Customer> getAllCustomers ();
    List<Product> getAllProducts ();
}
