package com.ubuybr.ubuybrapi.service;

import com.ubuybr.ubuybrapi.exception.NotFoundException;
import com.ubuybr.ubuybrapi.model.Product;
import com.ubuybr.ubuybrapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> save(Product product) {
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

    public Mono<Void> deleteById(String id) {
        return this.productRepository.deleteById(id);
    }
}
