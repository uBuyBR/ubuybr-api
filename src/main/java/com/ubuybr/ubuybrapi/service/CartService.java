package com.ubuybr.ubuybrapi.service;

import com.mongodb.MongoException;
import com.ubuybr.ubuybrapi.model.Cart;
import com.ubuybr.ubuybrapi.repository.CartRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public Mono<Cart> save(Cart cart) {
        return this.productService.findAllById(cart.getProductsId())
                .count()
                .flatMap(aLong -> {
                    if (aLong != cart.getProductsId().size()) {
                        return Mono.error(new MongoException("test"));
                    }
                    
                    return cartRepository.save(cart);
                });
    }

    public Flux<Cart> findAll() {
        return this.cartRepository.findAll();
    }
}
