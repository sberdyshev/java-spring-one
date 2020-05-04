package ru.sberdyshev.geekbrains.java.spring.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberdyshev.geekbrains.java.spring.springboot.domain.Product;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notfound.NotFoundCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notfound.product.NotFoundProductException;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid.NotValidCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid.NotValidObjectException;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid.product.NotValidProductException;
import ru.sberdyshev.geekbrains.java.spring.springboot.repository.ProductRepository;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        productRepository.deleteAll();
        Product iphone = new Product(null, "iphone", "a phone", new BigDecimal(34));
        Product tv = new Product(null, "tv", "a good tv", new BigDecimal(56));
        Product chips = new Product(null, "chips", "food", new BigDecimal(1));
        productRepository.save(iphone);
        productRepository.save(tv);
        productRepository.save(chips);
        Product product1 = new Product(null, "product1", "a phone", new BigDecimal(34));
        Product product2 = new Product(null, "product2", "aef", new BigDecimal(5));
        Product product3 = new Product(null, "product3", "sefdf", new BigDecimal(7));
        Product product4 = new Product(null, "product4", "sgdr", new BigDecimal(9));
        Product product5 = new Product(null, "product5", "fghfh", new BigDecimal(14));
        Product product6 = new Product(null, "product6", "vjgy", new BigDecimal(18));
        Product product7 = new Product(null, "product7", "hlil", new BigDecimal(23));
        Product product8 = new Product(null, "product8", "ddrg", new BigDecimal(26));
        Product product9 = new Product(null, "product9", "sese", new BigDecimal(29));
        Product product10 = new Product(null, "product10", "gg", new BigDecimal(33));
        Product product11 = new Product(null, "product11", "hhhh", new BigDecimal(37));
        Product product12 = new Product(null, "product12", "jjjj", new BigDecimal(44));
        Product product13 = new Product(null, "product13", "kkkkk", new BigDecimal(48));
        Product product14 = new Product(null, "product14", "lllll", new BigDecimal(55));
        Product product15 = new Product(null, "product15", ";;;;;;", new BigDecimal(57));
        Product product16 = new Product(null, "product16", "rrrrrr", new BigDecimal(58));
        Product product17 = new Product(null, "product17", "tttttt", new BigDecimal(58));
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);
        productRepository.save(product9);
        productRepository.save(product10);
        productRepository.save(product11);
        productRepository.save(product12);
        productRepository.save(product13);
        productRepository.save(product14);
        productRepository.save(product15);
        productRepository.save(product16);
        productRepository.save(product17);
    }

    @Transactional
    public Optional<Product> insert(Product product) {
        String insertWithProductOperation = "insert product with product";
        validateProductExceptId(product, insertWithProductOperation);
        logger.debug("Called \"{}\" with id \"{}\", name \"{}\", cost \"{}\", description \"{}\"",
                insertWithProductOperation,
                product.getId(),
                product.getName(),
                product.getCost(),
                product.getDescription());
        if (product.getId() != null) {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if (optionalProduct != null && optionalProduct.isPresent()) {
                throw new NotValidProductException(NotValidCodes.NOT_VALID_OBJECT_ALREADY_EXISTS,
                        "Operation - " + insertWithProductOperation + ". Product with id \"" + product.getId() + "\" already exists");
            }
        }
        Product resultProduct = productRepository.save(product);
        return Optional.ofNullable(resultProduct);
    }

    //todo проверить, что объект есть
    @Transactional
    public Optional<Product> update(Product product) {
        String updateByProductOperation = "update product by Product object";
        validateProductStrict(product, updateByProductOperation);
        logger.debug("Called \"{}\" with id \"{}\", name \"{}\", cost \"{}\", description \"{}\"",
                updateByProductOperation,
                product.getId(),
                product.getName(),
                product.getCost(),
                product.getDescription());
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct != null && optionalProduct.isPresent()) {
            Product resultProduct = productRepository.save(product);
            return Optional.ofNullable(resultProduct);
        } else {
            logger.error("Called \"{}\". Product with id \"{}\" wasn't found, throwing NotFoundObjectException exception",
                    updateByProductOperation,
                    product.getId());
            throw new NotFoundProductException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + updateByProductOperation + ". Product with id \"" + product.getId() + "\" wasn't found");
        }
    }

    @Transactional
    public Optional<Product> delete(Product product) {
        String deleteByProductOperation = "delete product by Product object";
        validateProductExceptDataFields(product, deleteByProductOperation);
        logger.debug("Called \"{}\" with id \"{}\", name \"{}\", cost \"{}\"",
                deleteByProductOperation,
                product.getId(),
                product.getName(),
                product.getCost());
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct != null && optionalProduct.isPresent()) {
            productRepository.delete(product);
            return optionalProduct;
        } else {
            logger.error("Called \"{}\". Product with id \"{}\" wasn't found, throwing NotFoundObjectException exception",
                    deleteByProductOperation,
                    product.getId());
            throw new NotFoundProductException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + deleteByProductOperation + ". Product with id \"" + product.getId() + "\" wasn't found");
        }
    }

    @Transactional
    public Optional<Product> delete(Optional<Long> productId) {
        String deleteByIdOperation = "delete product by ID";
        logger.debug("Called \"{}\" with id \"{}\"",
                deleteByIdOperation,
                productId.get());
        if (productId == null || !productId.isPresent()) {
            logger.error("Called \"{}\". ID is null, throwing IllegalArgumentException exception",
                    deleteByIdOperation);
            throw new NotValidObjectException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL,
                    "Operation - " + deleteByIdOperation + ". ID is null");
        }
        Optional<Product> optionalProduct = productRepository.findById(productId.get());
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return optionalProduct;
        } else {
            logger.error("Called \"{}\". Product with ID \"{}\" wasn't found, throwing NotFoundObjectException exception",
                    deleteByIdOperation,
                    productId);
            throw new NotFoundProductException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + deleteByIdOperation + ". Product with id \"" + productId.get() + "\" wasn't found");
        }
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Optional<Long> productId) {
        String findProductByIdOperation = "find product by id";
        logger.debug("Called \"{}\" with id \"{}\"",
                findProductByIdOperation,
                productId.get());
        if (productId == null || !productId.isPresent()) {
            logger.error("Called \"{}\". ID is null, throwing IllegalArgumentException exception",
                    findProductByIdOperation);
            throw new NotValidObjectException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL,
                    "Operation - " + findProductByIdOperation + ". ID is null");
        }
        Optional<Product> result = productRepository.findById(productId.get());
        if (result != null && result.isPresent()) {
            logger.debug("Called \"{}\", found product with id \"{}\", name \"{}\", cost \"{}\"",
                    findProductByIdOperation,
                    result.get().getId(),
                    result.get().getName(),
                    result.get().getCost());
            return result;
        } else {
            logger.error("Called \"{}\". Found no products with id \"{}\", throwing exception",
                    findProductByIdOperation,
                    productId);
            throw new NotFoundProductException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + findProductByIdOperation + ". Product with id \"" + productId.get() + "\" wasn't found");
        }
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        logger.debug("Called getAllProducts");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllProductsByPageAndMinAndMaxPrice(Optional<BigDecimal> minPrice,
                                                               Optional<BigDecimal> maxPrice,
                                                               Optional<Integer> pageNumber,
                                                               Optional<Integer> pageSize) {
        logger.debug("Called getAllProductsByMinAndMaxPrice with page number \"{}\", page size\"{}\", min price \"{}\", nax price \"{}\"",
                pageNumber,
                pageSize,
                minPrice,
                maxPrice);
        Pageable page = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        if (pageNumber != null && pageNumber.isPresent() && pageSize != null && pageSize.isPresent()) {
            page = PageRequest.of(pageNumber.get() - 1, pageSize.get());
        }
        if (minPrice != null && minPrice.isPresent()) {
            if (maxPrice != null && maxPrice.isPresent()) {
                return productRepository.findAllByCostBetween(minPrice.get(), maxPrice.get(), page);
            } else {
                return productRepository.findAllByCostGreaterThanEqual(minPrice.get(), page);
            }
        } else {
            if (maxPrice != null && maxPrice.isPresent()) {
                return productRepository.findAllByCostLessThanEqual(maxPrice.get(), page);
            } else {
                return productRepository.findAll(page);
            }
        }
    }

    private void validateProductStrict(Product product, String operation) {
        logger.debug("Product validation - strict. Operation - \"{}\"",
                operation);
        validateProductIsNotNull(product, operation);
        StringBuffer allNullFields = getAllNullFields(product);
        if (allNullFields.length() != 0) {
            logger.error("Called {} with null fields \"{}\", throwing NotValidProductException exception",
                    operation,
                    allNullFields);
            throw new NotValidProductException(NotValidCodes.NOT_VALID_OBJECT_FIELDS_ARE_NULL,
                    "Operation - " + operation + ". Product object is invalid. Field(s) \"" + allNullFields + "\" is/are null.");
        }
    }

    private void validateProductExceptId(Product product, String operation) {
        logger.debug("Product validation - except id. Operation - \"{}\"",
                operation);
        validateProductIsNotNull(product, operation);
        StringBuffer nullDataFields = getNullDataFields(product);
        if (nullDataFields.length() != 0) {
            logger.error("Called {} with null fields \"{}\", throwing NotValidProductException exception",
                    operation,
                    nullDataFields);
            throw new NotValidProductException(NotValidCodes.NOT_VALID_OBJECT_FIELDS_ARE_NULL,
                    "Operation - " + operation + ". Product object is invalid. Field(s) \"" + nullDataFields + "\" is/are null.");
        }
    }

    private void validateProductExceptDataFields(Product product, String operation) {
        logger.debug("Product validation - except data fields. Operation - \"{}\"",
                operation);
        validateProductIsNotNull(product, operation);
        if (product.getId() == null) {
            logger.debug("Product validation - id is null");
            logger.error("Called {} with null id, throwing NotValidProductException exception",
                    operation);
            throw new NotValidProductException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL, "Operation - " + operation + ". Product ID is null");
        }
    }

    private void validateProductIsNotNull(Product product, String operation) {
        logger.debug("Product validation - checking if product is null. Operation - \"{}\"", operation);
        if (product == null) {
            logger.error("Called {} with null object, throwing NotValidProductException exception", operation);
            throw new NotValidProductException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL, "Operation - " + operation + ". Product is null");
        }
    }

    @NotNull
    private StringBuffer getAllNullFields(Product product) {
        logger.debug("Product validation - checking if all fields are null");
        StringBuffer allNullFields = new StringBuffer();
        StringBuffer nullDataFields = getNullDataFields(product);
        if (product.getId() == null) {
            logger.debug("Product validation - id is null");
            allNullFields.append("id");
            if (nullDataFields.length() != 0) {
                allNullFields.append(", ");
            }
        }
        allNullFields.append(nullDataFields);
        return allNullFields;
    }

    @NotNull
    private StringBuffer getNullDataFields(Product product) {
        logger.debug("Product validation - checking if data fields are null");
        StringBuffer nullFields = new StringBuffer();
        if (product.getName() == null) {
            logger.debug("Product validation - name is null");
            nullFields.append("name");
        }
        if (product.getCost() == null) {
            logger.debug("Product validation - cost is null");
            if (nullFields.length() != 0) {
                nullFields.append(", ");
            }
            nullFields.append("cost");
        }
        if (product.getDescription() == null) {
            logger.debug("Product validation - description is null");
            if (nullFields.length() != 0) {
                nullFields.append(", ");
            }
            nullFields.append("description");
        }
        return nullFields;
    }
}
