package com.example.bimapplication.bim.controllers;

import com.example.bimapplication.bim.service.BIMEntityDto;
import com.example.bimapplication.bim.service.BIMEntityRequest;
import com.example.bimapplication.bim.service.BIMService;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/bim")
@RequiredArgsConstructor
public class MainController {

    private final BIMService service;

    @PostMapping
    public ResponseEntity<?> createBIM(BIMEntityRequest entity) throws ContractException, InterruptedException, TimeoutException {
        BIMEntityDto bimEntityDto = service.create(entity);

        return ResponseEntity.ok(bimEntityDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBIMById(@PathVariable("id") Integer id) throws ContractException, InterruptedException, TimeoutException {
        BIMEntityDto byId = service.getById(id);

        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeBIM(@PathVariable("id") Integer id) throws ContractException, InterruptedException, TimeoutException {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
