package com.ubuybr.ubuybrapi.controller;

import com.mongodb.MongoException;
import com.ubuybr.ubuybrapi.model.Cart;
import com.ubuybr.ubuybrapi.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Mono<Cart> save(@Validated @RequestBody Cart cart) {
        log.info("Cart {}", cart);
        return this.cartService.save(cart);
    }

    @GetMapping
    public Flux<Cart> listAll() {
        return this.cartService.findAll();
    }
}
