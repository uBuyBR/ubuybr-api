package com.ubuybr.ubuybrapi.service;

import com.ubuybr.ubuybrapi.exception.NotFoundException;
import com.ubuybr.ubuybrapi.exception.ProductNotAvailableException;
import com.ubuybr.ubuybrapi.model.Cart;
import com.ubuybr.ubuybrapi.model.Product;
import com.ubuybr.ubuybrapi.model.User;
import com.ubuybr.ubuybrapi.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductService productService;
    private UserService userService;

    public CartService(CartRepository cartRepository, ProductService productService, UserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public Mono<Cart> save(String userId) {
        return userService.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("Usuário não encontrado")))
                .flatMap(userFound -> Mono.just(Cart.builder().user(userFound).build()))
                .flatMap(cart -> cartRepository.save(cart));
    }

    public Mono<Cart> addProduct(Product product, String cartId) {
        return cartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new NotFoundException("Carrinho não encontrado")))
                .flatMap(cart ->
                        this.productService.findById(product.getId())
                            .switchIfEmpty(Mono.error(new NotFoundException("Produto não encontrado")))
                            .flatMap(productResult -> productResult.getQuantity() <= product.getQuantity() ?
                                    Mono.error(new ProductNotAvailableException(String.format("O produto %s não está disponivel na quantidade %d",
                                            product.getDescription(),
                                            product.getQuantity())))
                                        : Mono.just(productResult))
                            .flatMap(productResult -> {
                                productResult.setQuantity(productResult.getQuantity() - product.getQuantity());
                                return this.productService.save(productResult);
                            })
                            .flatMap(productSaved -> Mono.just(Product.builder()
                                                                    .id(productSaved.getId())
                                                                    .description(productSaved.getDescription())
                                                                    .quantity(product.getQuantity())
                                                                    .value(productSaved.getValue())
                                                                    .build()))
                            .flatMap(productToAdd -> {
                                if (!cart.getProducts().isEmpty() && cart.getProducts().stream().anyMatch(cartProduct -> cartProduct.getId().equals(productToAdd.getId()))) {
                                    cart.getProducts()
                                            .stream()
                                            .filter(cartProduct -> cartProduct.getId().equals(productToAdd.getId()))
                                            .forEach(productFound -> productFound.setQuantity(productFound.getQuantity() + productToAdd.getQuantity()));
                                } else {
                                    cart.getProducts().add(productToAdd);
                                }
                                cart.setValue(0.0);
                                cart.getProducts().stream().forEach(cartProduct -> cart.setValue(cart.getValue()+cartProduct.getValue()*cartProduct.getQuantity()));

                                return Mono.just(cart);
                            }).flatMap(cartRepository::save));

    }

    public Flux<Cart> findAll() {
        return this.cartRepository.findAll();
    }
}
