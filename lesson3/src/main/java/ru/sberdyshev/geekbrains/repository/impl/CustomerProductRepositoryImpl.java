package ru.sberdyshev.geekbrains.repository.impl;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.sberdyshev.geekbrains.entity.Customer;
import ru.sberdyshev.geekbrains.entity.Product;
import ru.sberdyshev.geekbrains.repository.CustomerProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerProductRepositoryImpl implements CustomerProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProductRepository.class);
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager; //вероятно, стоит убрать на уровень транзакции (создавать его вместе с каждой новой транзакцией?)

    public CustomerProductRepositoryImpl() {
        entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        entityManager = entityManagerFactory.createEntityManager(); //вероятно, стоит убрать на уровень транзакции (создавать его вместе с каждой новой транзакцией?)
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        logger.debug("getCustomerById() - start");
        Customer customer = null;
        try {
            customer = entityManager.find(Customer.class, customerId);
            logger.debug("getProductById() - got customer with id \"{}\", name \"{}\".", customer.getId(), customer.getName());
        } catch (Exception ex) {
            logger.debug("getCustomerById() - handling db exception", ex);
        }
        logger.debug("getCustomerById() - end");
        return customer;
    }

    @Override
    public Product getProductById(Long productId) {
        logger.debug("getProductById() - start");
        Product product = null;
        try {
            product = entityManager.find(Product.class, productId);
            logger.debug("getProductById() - got product with id \"{}\", name \"{}\", cost \"{}\".", product.getId(), product.getName(), product.getCost());
        } catch (Exception ex) {
            logger.debug("getProductById() - handling db exception", ex);
        }
        logger.debug("getProductById() - end");
        return product;
    }

    @Override
    public List<Product> getProductsByCustomerId(Long customerId) {
        logger.debug("getProductsByCustomerId() - start");
        List<Product> products = null;
        try {
            logger.debug("getProductsByCustomerId() - getting products by customer id \"{}\" from db", customerId);
            TypedQuery<Product> query = entityManager
                    .createQuery("Select distinct product " +
                            "from Product product " +
                            "join product.customers customer  " +
                            "where customer.id = :customerId", Product.class);
            query.setParameter("customerId", customerId);
            logger.debug("getProductsByCustomerId() - calling db");
            products = query.getResultList();
            logger.debug("getProductsByCustomerId() - got {} products", products.size());
        } catch (Exception ex) {
            logger.debug("getProductsByCustomerId() - handling db exception", ex);
        }
        logger.debug("getProductsByCustomerId() - end");
        return products;
    }

    @Override
    public List<Customer> getCustomersByProductId(Long productId) {
        logger.debug("getCustomersByProductId() - start");
        List<Customer> customers = null;
        try {
            logger.debug("getCustomersByProductId() - getting products by cistomer id \"{}\" from db", productId);
            TypedQuery<Customer> query = entityManager
                    .createQuery("Select distinct customer " +
                            "from Customer customer " +
                            "join customer.products product  " +
                            "where product.id = :productId", Customer.class);
            query.setParameter("productId", productId);
            logger.debug("getCustomersByProductId() - calling db");
            customers = query.getResultList();
            logger.debug("getCustomersByProductId() - got {} customers", customers.size());
        } catch (Exception ex) {
            logger.debug("getCustomersByProductId() - handling db exception", ex);
        }
        logger.debug("getCustomersByProductId() - end");
        return customers;
    }

    @Override
    public boolean deleteCustomerByCustomerId(Long customerId) {
        logger.debug("deleteCustomerByCustomerId() - start");
        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer == null) {
            logger.debug("deleteCustomerByCustomerId() - customer with id {} wasn't found", customerId);
            return false;
        }
        boolean result = false;
        try {
            logger.debug("deleteCustomerByCustomerId() - calling db");
            entityManager.getTransaction().begin();
            entityManager.remove(customer);
            entityManager.getTransaction().commit();
            result = true;
            logger.debug("deleteCustomerByCustomerId() - customer with id {} deleted", customerId);
        } catch (Exception ex) {
            logger.debug("deleteCustomerByCustomerId() - handling db exception", ex);
            entityManager.getTransaction().rollback();
        }
        logger.debug("deleteCustomerByCustomerId() - end");
        return result;
    }

    @Override
    public boolean deleteProductByProductId (Long productId) {
        logger.debug("deleteProductByProductId() - start");
        Product product = entityManager.find(Product.class, productId);
        if (product == null) {
            logger.debug("deleteProductByProductId() - product with id {} wasn't found", productId);
            return false;
        }
        boolean result = false;
        try {
            logger.debug("deleteProductByProductId() - calling db");
            entityManager.getTransaction().begin();
            entityManager.remove(product);
            entityManager.getTransaction().commit();
            result = true;
            logger.debug("deleteProductByProductId() - product with id {} deleted", productId);
        } catch (Exception ex) {
            logger.debug("deleteProductByProductId() - handling db exception", ex);
            entityManager.getTransaction().rollback();
        }
        logger.debug("deleteProductByProductId() - end");
        return result;
    }

    public List<Customer> getAllCustomers() {
        logger.debug("getAllCustomers() - start");
        List<Customer> customers = null;
        try {
            logger.debug("getAllCustomers() - calling db");
            customers = entityManager
                    .createQuery("Select customer from Customer customer", Customer.class)
                    .getResultList();
            logger.debug("getAllCustomers() - got {} customers", customers.size());
        } catch (Exception ex) {
            logger.debug("getAllCustomers() - handling db exception", ex);
        }
        logger.debug("getAllCustomers() - end");
        return customers;
    }

    public List<Product> getAllProducts() {
        logger.debug("getAllProducts() - start");
        List<Product> products = null;
        try {
            logger.debug("getAllProducts() - calling db");
            products = entityManager
                    .createQuery("Select product from Product product", Product.class)
                    .getResultList();
            logger.debug("getAllProducts() - got {} products", products.size());
        } catch (Exception ex) {
            logger.debug("getAllProducts() - handling db exception", ex);
        }
        logger.debug("getAllProducts() - end");
        return products;
    }
}
