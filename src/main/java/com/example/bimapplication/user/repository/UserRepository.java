package com.example.bimapplication.user.repository;

import com.example.bimapplication.user.User;
import org.hyperledger.fabric.gateway.ContractException;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

public interface UserRepository {
    Optional<User> findByEmail(String email) throws ContractException;

    Optional<User> findUserById(Integer id) throws ContractException;

    void save(User user) throws ContractException, InterruptedException, TimeoutException;

    void delete(Integer userId) throws ContractException, InterruptedException, TimeoutException;
}
