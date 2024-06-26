package com.yarnyard.user_service.controllers;

import com.yarnyard.user_service.requests.CreateUserRequest;
import com.yarnyard.user_service.requests.LoginRequest;
import com.yarnyard.user_service.requests.UpdateUserRequest;
import com.yarnyard.user_service.services.UserService;
import org.junit.Test;
import org.mockito.Mockito;

public class UserControllerTest {
    private final UserService service;
    private final UserController userController;

    public UserControllerTest(){
        service = Mockito.mock(UserService.class);
        userController = new UserController(service);
    }
    @Test
    public void testGetUsers(){
        userController.getUsers();
        Mockito.verify(service, Mockito.times(1)).getUsers();
     }
    @Test
    public void testGetUserById(){
        userController.getUserById("id");
        Mockito.verify(service, Mockito.times(1)).getUserById("id");
    }
    @Test
    public void testAddResponse(){
        CreateUserRequest request = CreateUserRequest.builder().build();
        userController.addUser(request);
        Mockito.verify(service, Mockito.times(1)).addUser(request);
    }
    @Test
    public void testUpdateUser(){
        UpdateUserRequest request = UpdateUserRequest.builder().build();
        userController.updateUser(request);
        Mockito.verify(service, Mockito.times(1)).updateUser(request);
    }
    @Test
    public void testDeleteUser(){
        userController.deleteUser("id");
        Mockito.verify(service, Mockito.times(1)).deleteUser("id");
    }
    @Test
    public void testLogin(){
        LoginRequest request = new LoginRequest("name", "password");
        userController.login(request);
        Mockito.verify(service, Mockito.times(1)).login(request);
    }

}
