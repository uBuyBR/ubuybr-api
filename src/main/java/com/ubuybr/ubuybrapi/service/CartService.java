package com.ubuybr.ubuybrapi.service;

import com.ubuybr.ubuybrapi.exception.NotFoundException;
import com.ubuybr.ubuybrapi.exception.ProductNotAvailableException;
import com.ubuybr.ubuybrapi.model.Cart;
import com.ubuybr.ubuybrapi.model.Product;
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

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public Mono<Cart> save() {
        return cartRepository.save(Cart.builder().build());
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
                            .flatMap(productSaved -> {
                                if (!cart.getProducts().isEmpty() && cart.getProducts().stream().anyMatch(product1 -> product1.getId().equals(product.getId()))) {
                                    cart.getProducts()
                                            .stream()
                                            .filter(cartProduct -> cartProduct.getId().equals(product.getId()))
                                            .forEach(productFound -> productFound.setQuantity(productFound.getQuantity() + product.getQuantity()));
                                } else {
                                    cart.getProducts().add(product);
                                }

                                return Mono.just(cart);
                            }).flatMap(cartRepository::save));

    }

    public Flux<Cart> findAll() {
        return this.cartRepository.findAll();
    }
}
