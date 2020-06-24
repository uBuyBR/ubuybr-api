package com.ubuybr.ubuybrapi.repository;

import com.ubuybr.ubuybrapi.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
