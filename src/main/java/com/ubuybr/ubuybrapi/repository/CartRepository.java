package com.ubuybr.ubuybrapi.repository;

import com.ubuybr.ubuybrapi.model.Cart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ReactiveMongoRepository<Cart, String> {

}
