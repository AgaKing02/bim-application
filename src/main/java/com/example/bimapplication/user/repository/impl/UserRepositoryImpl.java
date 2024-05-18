package com.example.bimapplication.user.repository.impl;

import com.example.bimapplication.blockchain.exception.RecordAlreadyExistsException;
import com.example.bimapplication.blockchain.exception.RecordNotFoundException;
import com.example.bimapplication.blockchain.util.HlfExceptionMessageUtil;
import com.example.bimapplication.blockchain.util.JsonUtil;
import com.example.bimapplication.user.User;
import com.example.bimapplication.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static com.example.bimapplication.user.repository.impl.UserRepositoryImpl.UserTransactionUtil.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Network network;
    private final JsonUtil jsonUtil;
    private Contract userContract;


    @PostConstruct
    private void init() {
        this.userContract = network.getContract(USER_TRANSFER);
    }

    @Override
    public Optional<User> findByEmail(String email) throws ContractException {
        Optional<byte[]> result = Optional.ofNullable(userContract.evaluateTransaction(USER_BY_EMAIL, email));
        return result.filter(bytes -> bytes.length > 0)
                .map(bytes -> jsonUtil.mapToObject(bytes, User.class));
    }

    @Override
    public Optional<User> findUserById(Integer id) throws ContractException {
        Optional<byte[]> result = Optional.ofNullable(userContract.evaluateTransaction(USER_BY_ID, String.valueOf(id)));
        return result.filter(bytes -> bytes.length > 0)
                .map(bytes -> jsonUtil.mapToObject(bytes, User.class));
    }

    @Override
    public void save(User newUser) throws ContractException, InterruptedException, TimeoutException {
        try {
            userContract.submitTransaction(CREATE_NEW_USER,
                    newUser.getUserId().toString(),
                    newUser.getFirstName(),
                    newUser.getLastName(),
                    newUser.getEmail(),
                    newUser.getPassword(),
                    newUser.getRole().toString());
        } catch (ContractException ex) {
            if (ex.getMessage().contains(HlfExceptionMessageUtil.ASSET_ALREADY_EXISTS_MESSAGE)) {
                throw new RecordAlreadyExistsException(String.format("User %s is already exists", newUser.getUserId()));
            }
            throw ex;
        }
    }

    @Override
    public void delete(Integer userId) throws ContractException, InterruptedException, TimeoutException {
        try {
            userContract.submitTransaction(DELETE_USER, String.valueOf(userId));
        } catch (ContractException ex) {
            if (ex.getMessage().contains(HlfExceptionMessageUtil.ASSET_NOT_FOUND_MESSAGE)) {
                throw new RecordNotFoundException(String.format("User %s not found", userId));
            }
            throw ex;
        }
    }

    protected static class UserTransactionUtil {
        public static final String USER_TRANSFER = "UserTransfer";

        public static final String USER_BY_EMAIL = "getUserByEmail";
        public static final String USER_BY_ID = "getUserById";
        public static final String DELETE_USER = "deleteUserById";
        public static final String CREATE_NEW_USER = "createNewUser";

    }
}
