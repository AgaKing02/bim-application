package com.example.bimapplication.bim.service.impl;

import com.example.bimapplication.bim.BIMEntity;
import com.example.bimapplication.bim.repository.BIMEntityRepository;
import com.example.bimapplication.bim.service.BIMService;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class BIMServiceImpl implements BIMService {
    private final BIMEntityRepository repository;

    @Override
    public void create(BIMEntity entity) throws ContractException, InterruptedException, TimeoutException {
        repository.save(entity);
    }

    @Override
    public void delete(Integer id) throws ContractException, InterruptedException, TimeoutException {
        repository.delete(id);
    }
}
