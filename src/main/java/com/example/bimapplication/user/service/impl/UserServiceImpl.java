package com.example.bimapplication.user.service.impl;

import com.example.bimapplication.user.service.UserService;
import com.example.bimapplication.user.User;
import com.example.bimapplication.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findUserById(Integer id) {
        Optional<User> userById;
        try {
            userById = repository.findUserById(id);
        } catch (ContractException e) {
            return null;
        }
        return userById.get();
    }
}
