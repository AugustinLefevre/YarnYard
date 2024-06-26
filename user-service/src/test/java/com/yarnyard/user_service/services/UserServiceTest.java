package com.yarnyard.user_service.services;

import com.yarnyard.user_service.models.User;
import com.yarnyard.user_service.repositories.UserRepository;
import com.yarnyard.user_service.requests.CreateUserRequest;
import com.yarnyard.user_service.requests.LoginRequest;
import com.yarnyard.user_service.requests.UpdateUserRequest;
import com.yarnyard.user_service.responces.ConnectionResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final UserRepository repository = Mockito.mock(UserRepository.class);;

    private UserService service;

    public UserServiceTest(){
        service = new UserService(repository);
    }

    @Test
    public void testGetUser(){
        var actual = service.getUsers();
        Assert.assertTrue(actual instanceof List<User>);
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    public void givenId_whenGetUserById_thenReturnUser(){
        String givenId = "given id";
        User expected = new User();
        Optional<User> optUser = Optional.of(expected);
        Mockito.when(repository.findById(givenId)).thenReturn(optUser);
        var actual = service.getUserById(givenId);
        Assert.assertTrue(actual instanceof User);
        Assert.assertNotNull(actual);
        Mockito.verify(repository, Mockito.times(1)).findById(givenId);
    }

    @Test
    public void givenCreateUserRequest_whenAddUser_thenReturnConnectionResponse(){
        String givenEmail = "mail@mail.com";
        String givenName = "name";
        String givenPassword = "password";
        CreateUserRequest request = CreateUserRequest.builder()
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .build();

        var actual = service.addUser(request);
        Assert.assertTrue(actual instanceof ConnectionResponse);
        Assert.assertNotNull(actual);
        Assert.assertEquals(givenName, actual.getUserName());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testDeleteUser(){
        String givenId = "id";
        service.deleteUser(givenId);
        Mockito.verify(repository, Mockito.times(1)).deleteById(givenId);
    }

    @Test
    public void GivenCompleteUpdateUserRequest_whenUpdateUser(){
        String givenId = "id";
        String givenEmail = "email";
        String givenName = "name";
        String givenPassword = "password";
        String givenRole = "ROLE";
        UpdateUserRequest givenRequest = UpdateUserRequest.builder()
                .user_id(givenId)
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .role(givenRole)
                .build();

        User mockedUser = Mockito.mock(User.class);
        Mockito.when(repository.findById(givenId)).thenReturn(Optional.of(mockedUser));

        service.updateUser(givenRequest);

        Mockito.verify(repository, Mockito.times(1)).findById(givenId);
        Mockito.verify(mockedUser, Mockito.times(1)).setName(givenName);
        Mockito.verify(mockedUser, Mockito.times(1)).setPassword(givenPassword);
        Mockito.verify(mockedUser, Mockito.times(1)).setEmail(givenEmail);
        Mockito.verify(mockedUser, Mockito.times(1)).setRole(givenRole);
        Mockito.verify(repository, Mockito.times(1)).save(mockedUser);
    }
    @Test
    public void givenNullAsEmail_whenUpdateUser(){
        String givenId = "id";
        String givenEmail = null;
        String givenName = "name";
        String givenPassword = "password";
        String givenRole = "ROLE";
        UpdateUserRequest givenRequest = UpdateUserRequest.builder()
                .user_id(givenId)
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .role(givenRole)
                .build();

        User mockedUser = Mockito.mock(User.class);
        Mockito.when(repository.findById(givenId)).thenReturn(Optional.of(mockedUser));

        service.updateUser(givenRequest);

        Mockito.verify(repository, Mockito.times(1)).findById(givenId);
        Mockito.verify(mockedUser, Mockito.times(1)).setName(givenName);
        Mockito.verify(mockedUser, Mockito.times(1)).setPassword(givenPassword);
        Mockito.verify(mockedUser, Mockito.times(0)).setEmail(givenEmail);
        Mockito.verify(mockedUser, Mockito.times(1)).setRole(givenRole);
        Mockito.verify(repository, Mockito.times(1)).save(mockedUser);
    }
    @Test
    public void givenNullAsName_whenUpdateUser(){
        String givenId = "id";
        String givenEmail = "email@email.com";
        String givenName = null;
        String givenPassword = "password";
        String givenRole = "ROLE";
        UpdateUserRequest givenRequest = UpdateUserRequest.builder()
                .user_id(givenId)
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .role(givenRole)
                .build();

        User mockedUser = Mockito.mock(User.class);
        Mockito.when(repository.findById(givenId)).thenReturn(Optional.of(mockedUser));

        service.updateUser(givenRequest);

        Mockito.verify(repository, Mockito.times(1)).findById(givenId);
        Mockito.verify(mockedUser, Mockito.times(0)).setName(givenName);
        Mockito.verify(mockedUser, Mockito.times(1)).setPassword(givenPassword);
        Mockito.verify(mockedUser, Mockito.times(1)).setEmail(givenEmail);
        Mockito.verify(mockedUser, Mockito.times(1)).setRole(givenRole);
        Mockito.verify(repository, Mockito.times(1)).save(mockedUser);
    }
    @Test
    public void givenNullAsPassword_whenUpdateUser(){
        String givenId = "id";
        String givenEmail = "email@email.com";
        String givenName = "name";
        String givenPassword = null;
        String givenRole = "ROLE";
        UpdateUserRequest givenRequest = UpdateUserRequest.builder()
                .user_id(givenId)
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .role(givenRole)
                .build();

        User mockedUser = Mockito.mock(User.class);
        Mockito.when(repository.findById(givenId)).thenReturn(Optional.of(mockedUser));

        service.updateUser(givenRequest);

        Mockito.verify(repository, Mockito.times(1)).findById(givenId);
        Mockito.verify(mockedUser, Mockito.times(1)).setName(givenName);
        Mockito.verify(mockedUser, Mockito.times(0)).setPassword(givenPassword);
        Mockito.verify(mockedUser, Mockito.times(1)).setEmail(givenEmail);
        Mockito.verify(mockedUser, Mockito.times(1)).setRole(givenRole);
        Mockito.verify(repository, Mockito.times(1)).save(mockedUser);
    }
    @Test
    public void givenNullAsRole_whenUpdateUser(){
        String givenId = "id";
        String givenEmail = "email@email.com";
        String givenName = "name";
        String givenPassword = "password";
        String givenRole = null;
        UpdateUserRequest givenRequest = UpdateUserRequest.builder()
                .user_id(givenId)
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .role(givenRole)
                .build();

        User mockedUser = Mockito.mock(User.class);
        Mockito.when(repository.findById(givenId)).thenReturn(Optional.of(mockedUser));

        service.updateUser(givenRequest);

        Mockito.verify(repository, Mockito.times(1)).findById(givenId);
        Mockito.verify(mockedUser, Mockito.times(1)).setName(givenName);
        Mockito.verify(mockedUser, Mockito.times(1)).setPassword(givenPassword);
        Mockito.verify(mockedUser, Mockito.times(1)).setEmail(givenEmail);
        Mockito.verify(mockedUser, Mockito.times(0)).setRole(givenRole);
        Mockito.verify(repository, Mockito.times(1)).save(mockedUser);
    }
    @Test(expected = RuntimeException.class)
    public void givenNullAsUserId_whenUpdateUser_thenThrowException(){
        String givenId = null;
        String givenEmail = "email";
        String givenName = "name";
        String givenPassword = "password";
        String givenRole = "ROLE";
        UpdateUserRequest givenRequest = UpdateUserRequest.builder()
                .user_id(givenId)
                .email(givenEmail)
                .name(givenName)
                .password(givenPassword)
                .role(givenRole)
                .build();

        service.updateUser(givenRequest);
    }

    @Test
    public void givenInvalidLoginRequest_whenLogin(){
        LoginRequest request = new LoginRequest("name", "password");
        Assert.assertNull(service.login(request));
    }
    @Test
    public void givenValidLoginRequest_whenLogin(){
        LoginRequest request = new LoginRequest("name", "password");
        User mockedUser = Mockito.mock(User.class);
        Mockito.when(mockedUser.getPassword()).thenReturn("password");
        Mockito.when(mockedUser.getName()).thenReturn("name");
        Mockito.when(mockedUser.getUser_id()).thenReturn("id");
        Mockito.when(repository.findByName("name")).thenReturn(mockedUser);

        ConnectionResponse actual = service.login(request);
        Assert.assertNotNull(actual);
        Assert.assertEquals("name", actual.getUserName());
        Assert.assertEquals("id", actual.getUserId());
        Assert.assertNotNull( actual.getToken());
    }
}