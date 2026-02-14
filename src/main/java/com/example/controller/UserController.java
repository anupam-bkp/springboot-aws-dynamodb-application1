package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> users(){
        List<User> allProducts = userService.getAllUsers();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> user(@PathVariable String id){
        User product = userService.getUser(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<User> user(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

}
