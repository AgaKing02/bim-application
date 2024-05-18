package com.example.bimapplication.bim.repository.impl;

import com.example.bimapplication.bim.BIMEntity;
import com.example.bimapplication.bim.repository.BIMEntityRepository;
import com.example.bimapplication.blockchain.exception.RecordAlreadyExistsException;
import com.example.bimapplication.blockchain.exception.RecordNotFoundException;
import com.example.bimapplication.blockchain.util.HlfExceptionMessageUtil;
import com.example.bimapplication.blockchain.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static com.example.bimapplication.bim.repository.impl.BIMEntityRepositoryImpl.BIMTransactionUtil.*;

@Repository
@RequiredArgsConstructor
public class BIMEntityRepositoryImpl implements BIMEntityRepository {

    private final Network network;
    private final JsonUtil jsonUtil;
    private Contract bimContract;


    @PostConstruct
    private void init() {
        this.bimContract = network.getContract(BIM_TRANSFER);
    }


    @Override
    public Optional<BIMEntity> findBIMById(Integer id) throws ContractException {
        Optional<byte[]> result = Optional.ofNullable(bimContract.evaluateTransaction(BIM_BY_ID, String.valueOf(id)));
        return result.filter(bytes -> bytes.length > 0)
                .map(bytes -> jsonUtil.mapToObject(bytes, BIMEntity.class));
    }

    @Override
    public void save(BIMEntity bimEntity) throws ContractException, InterruptedException, TimeoutException {
        try {
            bimContract.submitTransaction(CREATE_NEW_BIM, jsonUtil.mapToJsonString(bimEntity));
        } catch (ContractException ex) {
            if (ex.getMessage().contains(HlfExceptionMessageUtil.ASSET_ALREADY_EXISTS_MESSAGE)) {
                throw new RecordAlreadyExistsException(String.format("BIM %s is already exists", bimEntity.getId()));
            }
            throw ex;
        }
    }

    @Override
    public void delete(Integer bimId) throws ContractException, InterruptedException, TimeoutException {
        try {
            bimContract.submitTransaction(DELETE_BIM, String.valueOf(bimId));
        } catch (ContractException ex) {
            if (ex.getMessage().contains(HlfExceptionMessageUtil.ASSET_NOT_FOUND_MESSAGE)) {
                throw new RecordNotFoundException(String.format("BIM %s not found", bimId));
            }
            throw ex;
        }
    }

    protected static class BIMTransactionUtil {
        public static final String BIM_TRANSFER = "BIMTransfer";

        public static final String BIM_BY_ID = "getBIMById";
        public static final String DELETE_BIM = "deleteBIMById";
        public static final String CREATE_NEW_BIM = "createNewBIM";

    }
}
