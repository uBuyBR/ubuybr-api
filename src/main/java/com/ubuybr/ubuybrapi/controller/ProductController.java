package com.ubuybr.ubuybrapi.controller;

import com.ubuybr.ubuybrapi.model.Product;
import com.ubuybr.ubuybrapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Flux<Product> findAll() {
        return this.productService.findAll();
    }

    @GetMapping("description")
    public Flux<Product> findByDescription(@RequestHeader String description) {
        log.info("Product description: {}", description);
        return this.productService.findByDescription(description);
    }

    @GetMapping("/{id}")
    public Mono<Product> findById(@PathVariable String id) {
        log.info("Product id {}", id);
        return this.productService.findById(id)
                .defaultIfEmpty(Product.builder().build());/*
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());*/
    }

    @PostMapping
    public Mono<Product> save(@Validated @RequestBody Product product) {
        return this.productService.save(product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return this.productService.deleteById(id);
    }
}
