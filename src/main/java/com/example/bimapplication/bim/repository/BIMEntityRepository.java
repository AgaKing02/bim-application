package com.example.bimapplication.bim.repository;

import com.example.bimapplication.bim.BIMEntity;
import com.example.bimapplication.user.User;
import org.hyperledger.fabric.gateway.ContractException;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

public interface BIMEntityRepository {

    Optional<BIMEntity> findBIMById(Integer id) throws ContractException;

    void save(BIMEntity entity) throws ContractException, InterruptedException, TimeoutException;

    void delete(Integer bimId) throws ContractException, InterruptedException, TimeoutException;
}
