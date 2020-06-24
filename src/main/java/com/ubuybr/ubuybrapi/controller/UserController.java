package com.ubuybr.ubuybrapi.controller;

import com.ubuybr.ubuybrapi.model.User;
import com.ubuybr.ubuybrapi.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<User> save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable String id) {
        return userService.findById(id);
    }
}
