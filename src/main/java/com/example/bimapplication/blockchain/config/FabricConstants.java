package com.example.bimapplication.blockchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FabricConstants {

    @Value("${blockchain.network.pem-file.location}")
    private String pemFileLocation;

    @Value("${blockchain.network.ca-client.url}")
    private String caUri;

    @Value("${blockchain.network.config.path}")
    private String networkPath;

    @PostConstruct
    private void init() {
        System.out.println("pem file location: " + getPemFileLocation());
    }

    public String getPemFileLocation() {
        return pemFileLocation;
    }

    public String getCaUri() {
        return caUri;
    }

    public String getNetworkPath() {
        return networkPath;
    }
}
