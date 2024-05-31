package com.example.bimapplication.bim.controllers;

import com.example.bimapplication.bim.BIMEntity;
import com.example.bimapplication.bim.service.BIMEntityDto;
import com.example.bimapplication.bim.service.BIMEntityRequest;
import com.example.bimapplication.bim.service.BIMService;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/bim")
@RequiredArgsConstructor
public class MainController {

    private final BIMService service;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createBIM(BIMEntityRequest entity) throws ContractException, InterruptedException, TimeoutException {
        BIMEntityDto bimEntityDto = service.create(entity);

        return ResponseEntity.ok(bimEntityDto);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getBIMById(Integer id) throws ContractException, InterruptedException, TimeoutException {
        BIMEntityDto byId = service.getById(id);

        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> removeBIM(Integer id) throws ContractException, InterruptedException, TimeoutException {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
