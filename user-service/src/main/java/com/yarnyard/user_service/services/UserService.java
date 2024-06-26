package com.yarnyard.user_service.services;

import com.yarnyard.user_service.models.User;
import com.yarnyard.user_service.repositories.UserRepository;
import com.yarnyard.user_service.requests.CreateUserRequest;
import com.yarnyard.user_service.requests.LoginRequest;
import com.yarnyard.user_service.requests.UpdateUserRequest;
import com.yarnyard.user_service.responces.ConnectionResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository repository;

    public UserService (UserRepository repository){
        this.repository = repository;
    }

    public List<User> getUsers(){
       return repository.findAll();
    }

    public User getUserById(String id){
        return repository.findById(id).orElse(null);
    }

    public ConnectionResponse addUser(CreateUserRequest request){
        User user = new User();
        BeanUtils.copyProperties(request, user);
        repository.save(user);
        return ConnectionResponse.builder()
                .userId(user.getUser_id())
                .userName(user.getName())
                .token("MyFakeToken")
                .build();
    }

    public void deleteUser(String id){
        repository.deleteById(id);
    }

    public void updateUser(UpdateUserRequest request) {
        if(request.getUser_id() != null){
            User user = getUserById(request.getUser_id());

            if(user != null){
                if(request.getName() != null){
                    user.setName(request.getName());
                }
                if(request.getPassword() != null){
                    user.setPassword(request.getPassword());
                }
                if(request.getEmail() != null){
                    user.setEmail(request.getEmail());
                }
                if(request.getRole() != null){
                    user.setRole(request.getRole());
                }
                repository.save(user);
            }
        } else{
            throw new RuntimeException("The user id is null");
        }

    }

    public ConnectionResponse login(LoginRequest request) {
        User user = repository.findByName(request.getName());
        if(user != null && user.getPassword().equals(request.getPassword())){
            return ConnectionResponse.builder()
                    .userId(user.getUser_id())
                    .userName(user.getName())
                    .token("MyFakeToken")
                    .build();
        }
        return null;
    }
}
