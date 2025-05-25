package com.skillstack.skilltracker.service.impl;

import com.skillstack.skilltracker.exception.ResourceNotFoundException;
import com.skillstack.skilltracker.mapper.UserMapper;
import com.skillstack.skilltracker.model.User;
import com.skillstack.skilltracker.model.UserDTO;
import com.skillstack.skilltracker.repository.UserRepository;
import com.skillstack.skilltracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setRoles(List.of("USER")); // Default role
        return userMapper.toUserDTO(userRepository.save(userMapper.toUser(userDTO)));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(String userName, UserDTO userDTO) {
        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new ResourceNotFoundException("User", "username", userName);
        }

        user.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user.setSkills(userMapper.toUser(userDTO).getSkills());
        user.setRoles(user.getRoles());

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public boolean deleteUser(String userName) {
        User user = userRepository.findByUsername(userName);
        if(user != null) {
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }

//Only for Admins--------------------------------------------------------------------------
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(role -> role.getRoles().contains("USER"))
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createAdminUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setRoles(List.of("ADMIN"));
        return userMapper.toUserDTO(userRepository.save(userMapper.toUser(userDTO)));
    }
}
