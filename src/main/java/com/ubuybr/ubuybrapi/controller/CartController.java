package com.ubuybr.ubuybrapi.controller;

import com.ubuybr.ubuybrapi.model.Cart;
import com.ubuybr.ubuybrapi.model.Product;
import com.ubuybr.ubuybrapi.model.User;
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

    @PostMapping("/user/{userId}")
    public Mono<Cart> save(@PathVariable String userId) {
        log.info("Create a new Empty cart");
        return this.cartService.save(userId);
    }

    @PostMapping("/{cartId}/product")
    public Mono<Cart> addProduct(@Validated @RequestBody Product product,
                                 @PathVariable String cartId) {
        return this.cartService.addProduct(product, cartId);
    }

    @GetMapping
    public Flux<Cart> listAll() {
        return this.cartService.findAll();
    }
}
