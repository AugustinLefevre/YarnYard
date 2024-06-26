package com.yarnyard.user_service.controllers;

import com.yarnyard.user_service.models.User;
import com.yarnyard.user_service.requests.CreateUserRequest;
import com.yarnyard.user_service.requests.LoginRequest;
import com.yarnyard.user_service.requests.UpdateUserRequest;
import com.yarnyard.user_service.responces.ConnectionResponse;
import com.yarnyard.user_service.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    public UserController(UserService userService){
       service = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id){
        return service.getUserById(id);
    }

    @PostMapping
    public ConnectionResponse addUser(@RequestBody CreateUserRequest request){
        return service.addUser(request);
    }

    @PutMapping
    public void updateUser(@RequestBody UpdateUserRequest request){
        service.updateUser(request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id){
        service.deleteUser(id);
    }

    @PostMapping("/login")
    public ConnectionResponse login(@RequestBody LoginRequest request){
        return service.login(request);
    }
}
