package com.example.bimapplication.bim.service.impl;

import com.example.bimapplication.bim.BIMEntity;
import com.example.bimapplication.bim.repository.BIMEntityRepository;
import com.example.bimapplication.bim.service.BIMEntityDto;
import com.example.bimapplication.bim.service.BIMEntityRequest;
import com.example.bimapplication.bim.service.BIMService;
import com.example.bimapplication.blockchain.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class BIMServiceImpl implements BIMService {
    private final BIMEntityRepository repository;
    private final JsonUtil jsonUtil;

    @Override
    public BIMEntityDto create(BIMEntityRequest entity) throws ContractException, InterruptedException, TimeoutException {
        String s = jsonUtil.mapToJsonString(entity);
        BIMEntity bimEntity = jsonUtil.mapJsonToObject(s, BIMEntity.class);

        return repository.save(bimEntity);
    }

    @Override
    public void delete(Integer id) throws ContractException, InterruptedException, TimeoutException {
        repository.delete(id);
    }

    @Override
    public BIMEntityDto getById(Integer id) throws ContractException, InterruptedException, TimeoutException {
        Optional<BIMEntity> bimById = repository.findBIMById(id);

        BIMEntity bimEntity = bimById.get();
        String s = jsonUtil.mapToJsonString(bimEntity);

        return jsonUtil.mapJsonToObject(s, BIMEntityDto.class);
    }
}
