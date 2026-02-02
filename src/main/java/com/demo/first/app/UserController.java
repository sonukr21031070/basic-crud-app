package com.demo.first.app;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private Map<Integer, User> userDb = new HashMap<>();

    @PostMapping
    public String createUser(@RequestBody User user) {
        userDb.putIfAbsent(user.getId(), user);
        System.out.println(user.getEmail());
        return "User Created";
    }

    @PutMapping
    public String updateUser(@RequestBody User user) {
        if(userDb.containsKey(user.getId()))
            userDb.put(user.getId(), user);
        return "Update Successful";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        if(!userDb.containsKey(id))
            return "User not found";
        userDb.remove(id);
        return "User deleted";
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(userDb.values());
    }
}

