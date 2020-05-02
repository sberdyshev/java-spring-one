package ru.sberdyshev.geekbrains.spring.products.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberdyshev.geekbrains.spring.products.entity.Product;
import ru.sberdyshev.geekbrains.spring.products.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
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
        Product product3 = new Product(null, "product3", "sefdf" , new BigDecimal(7));
        Product product4 = new Product(null, "product4", "sgdr" , new BigDecimal(9));
        Product product5 = new Product(null, "product5", "fghfh" , new BigDecimal(14));
        Product product6 = new Product(null, "product6", "vjgy" , new BigDecimal(18));
        Product product7 = new Product(null, "product7", "hlil" , new BigDecimal(23));
        Product product8 = new Product(null, "product8", "ddrg" , new BigDecimal(26));
        Product product9 = new Product(null, "product9", "sese" , new BigDecimal(29));
        Product product10 = new Product(null, "product10", "gg" , new BigDecimal(33));
        Product product11 = new Product(null, "product11", "hhhh" , new BigDecimal(37));
        Product product12 = new Product(null, "product12", "jjjj" , new BigDecimal(44));
        Product product13 = new Product(null, "product13", "kkkkk" , new BigDecimal(48));
        Product product14 = new Product(null, "product14", "lllll" , new BigDecimal(55));
        Product product15 = new Product(null, "product15", ";;;;;;" , new BigDecimal(57));
        Product product16 = new Product(null, "product16", "rrrrrr" , new BigDecimal(58));
        Product product17 = new Product(null, "product17", "tttttt" , new BigDecimal(58));

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
    public void insert(Product product) {
        logger.debug("Called insert product with id \"{}\", name \"{}\", cost \"{}\"", product.getId(), product.getName(), product.getCost());
        productRepository.save(product);
    }

    @Transactional
    public void update(Product product) {
        logger.debug("Called update product with id \"{}\", name \"{}\", cost \"{}\"", product.getId(), product.getName(), product.getCost());
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        logger.debug("Called find product by id \"{}\"", id);
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()) {
            logger.debug("Called find product by id, found product with id \"{}\", name \"{}\", cost \"{}\"", result.get().getId(), result.get().getName(), result.get().getCost());
        } else {
            logger.debug("Found no products with id \"{}\"", id);
        }
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        logger.debug("Called getAllProducts");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllProductsByPageAndMinAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice, Integer pageNumber, Integer pageSize) {
        logger.debug("Called getAllProductsByMinAndMaxPrice with page number \"{}\", page size\"{}\", min price \"{}\", nax price \"{}\"", pageNumber, pageSize, minPrice, maxPrice);
        Pageable page = PageRequest.of(0, 5);
//        page = null;
        if (pageNumber != null && pageSize != null) {
            page = PageRequest.of(pageNumber-1, pageSize);
        }
        if (minPrice == null) {
            if (maxPrice == null) {
                return productRepository.findAll(page);
            } else {
                return productRepository.findAllByCostLessThanEqual(maxPrice, page);
            }
        } else {
            if (maxPrice == null) {
                return productRepository.findAllByCostGreaterThanEqual(minPrice, page);
            } else {
                return productRepository.findAllByCostBetween(minPrice, maxPrice, page);
            }
        }
    }
//    @Transactional(readOnly = true)
//    public List<Product> findAllWithPagination() {
//        return productRepository.findAllWithPagination(PageRequest.of(1, 5));
//    }
}
