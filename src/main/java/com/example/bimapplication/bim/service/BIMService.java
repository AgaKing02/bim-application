package com.example.bimapplication.bim.service;

import com.example.bimapplication.bim.BIMEntity;
import org.hyperledger.fabric.gateway.ContractException;

import java.util.concurrent.TimeoutException;

public interface BIMService {
    BIMEntityDto create(BIMEntityRequest entity) throws ContractException, InterruptedException, TimeoutException;

    void delete(Integer id) throws ContractException, InterruptedException, TimeoutException;


    BIMEntityDto getById(Integer id) throws ContractException, InterruptedException, TimeoutException;

}
