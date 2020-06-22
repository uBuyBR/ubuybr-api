package com.ubuybr.ubuybrapi.service;

import com.mongodb.MongoException;
import com.ubuybr.ubuybrapi.exception.NotFoundException;
import com.ubuybr.ubuybrapi.exception.ProductNotAvailable;
import com.ubuybr.ubuybrapi.model.Cart;
import com.ubuybr.ubuybrapi.model.Product;
import com.ubuybr.ubuybrapi.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public Mono<Cart> save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Mono<Cart> addProduct(Product product, String cartId) {
        return cartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new NotFoundException("Carrinho não encontrado")))
                .flatMap(cart ->
                        this.productService.findById(product.getId())
                            .switchIfEmpty(Mono.error(new NotFoundException("Produto não encontrado")))
                            .flatMap(productResult -> productResult.getQuantity() < product.getQuantity() ?
                                    Mono.error(new ProductNotAvailable("Produto x não está disponivel nessa quantidade!"))
                                        : Mono.just(productResult))
                            .flatMap(productResult -> {
                                productResult.setQuantity(productResult.getQuantity() - product.getQuantity());
                                return this.productService.save(productResult);
                            })
                            .flatMap(productSaved -> {
                            if (cart.getProducts().contains(product)) {
                                cart.getProducts().forEach(cartProduct -> {
                                    if (cartProduct.getId().equals(productSaved.getId())){
                                        cartProduct.setQuantity(cartProduct.getQuantity() + product.getQuantity());
                                    }
                                });
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
