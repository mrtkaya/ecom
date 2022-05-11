package com.definex.ecom.Controller;

import com.definex.ecom.Entity.ProductRequest;
import com.definex.ecom.Services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(value = { "/product/addProduct" }, produces = "application/json", method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductRequest product) {
        logger.info("Controller: createProduct");
        productService.addProduct(product);
    }

    @RequestMapping(value = { "/product/deleteProduct" }, produces = "application/json", method = RequestMethod.POST)
    public void deleteProduct(@RequestBody ProductRequest product) {
        logger.info("Controller: deleteProduct");
        productService.deleteProduct(product.getProductId());
    }

    @RequestMapping(value = { "/product/updateProduct" }, produces = "application/json", method = RequestMethod.POST)
    public void updateProduct(@RequestBody ProductRequest product) {
        logger.info("Controller: updateProduct");
        productService.updateProduct(product);
    }


    @RequestMapping(value = { "/product/getProduct" }, produces = "application/json", method = RequestMethod.POST)
    public Optional<ProductRequest> getProduct(@RequestBody ProductRequest product) {
        logger.info("Controller: updateProduct");
        return productService.getProduct(product.getProductId());
    }
}
