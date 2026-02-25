package com.demo.first.app.controller;

import com.demo.first.app.model.User;
import com.demo.first.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService = new UserService();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                 .body(createdUser);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updated = userService.updateUser(user);
        if(updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if(!isDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<User> getUserOrder(
            @PathVariable int id,
            @PathVariable int orderId
    ) {
        System.out.println("Order ID : " + orderId);
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false, defaultValue = "sonu") String name,
            @RequestParam(required = false, defaultValue = "email") String email
        )
    {

        return ResponseEntity.ok(userService.searchUsers(name, email));
    }

    @GetMapping("/info")
    public String getInfo(@RequestHeader("User-Agent") String userAgent) {
        return "User Agent: " + userAgent;
    }

    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent) {
        return "User Agent: " + userAgent
                + " : " + id
                + " : " + name;
    }

    // Exception handling method
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(Exception exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Bad request");
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

