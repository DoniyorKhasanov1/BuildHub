package org.task.buildhub.service;

import org.task.buildhub.dto.AuthResponse;
import org.task.buildhub.dto.LoginRequest;
import org.task.buildhub.entity.User;

import java.util.List;

public interface UserService {
    AuthResponse login(LoginRequest request);
    User createUser(User user);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getCurrentUser();
}