package com.ubuybr.ubuybrapi.service;

import com.ubuybr.ubuybrapi.exception.NotFoundException;
import com.ubuybr.ubuybrapi.model.Product;
import com.ubuybr.ubuybrapi.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> save(@Validated Product product) {
        log.info("Produto recebido {}", product);
        return this.productRepository.save(product);
    }

    public Flux<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Flux<Product> findAllById(List<String> ids) {
        return this.productRepository.findAllById(ids);
    }

    public Mono<Product> findById(String id) {
        return this.productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Produto não encontrado")));
    }

    public Flux<Product> findByDescription(String description) {
        return this.productRepository.findByDescription(description);
    }

    public Flux<Product> updateProducts(List<Product> products) {
        return this.productRepository.saveAll(products);
    }

    public Mono<Void> deleteById(String id) {
        return this.productRepository.deleteById(id);
    }
}
