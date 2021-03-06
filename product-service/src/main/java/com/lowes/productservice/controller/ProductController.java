package com.lowes.productservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.productservice.model.dto.ProductDto;
import com.lowes.productservice.service.ProductService;
import com.lowes.productservice.util.model.ResponseModel;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/v1.0.0/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
	private  ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping("/getAll")
    public ResponseModel<List<ProductDto>> findAll() {
        ResponseModel<List<ProductDto>> productLists = productService.getAllProducts();
        logger.info(productLists.toString());
        return productLists;
    }

    @PostMapping("/create")
    public ResponseModel<ProductDto> createProduct(@Valid @RequestBody ProductDto newProduct) {
        ResponseModel<ProductDto> productDto = productService.createProduct(newProduct);
        logger.info(productDto.toString());
        return productDto;
    }
}