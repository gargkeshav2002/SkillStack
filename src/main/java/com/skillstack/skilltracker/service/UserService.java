package com.skillstack.skilltracker.service;

import com.skillstack.skilltracker.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserByUsername(String username);
    UserDTO updateUser(String userName, UserDTO userDTO);
    boolean deleteUser(String username);
    List<UserDTO> getAllUsers();
    UserDTO createAdminUser(UserDTO userDTO);
}
