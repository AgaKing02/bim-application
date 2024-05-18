package com.example.bimapplication.blockchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FabricConstants {

    @Value("${blockchain.network.pem-file.location}")
    public static String PEMFILE_LOCATION;

    @Value("${blockchain.network.ca-client.url}")
    public static String CA_URI;

    @Value("${blockchain.network.config.path}")
    public static String NETWORK_PATH;

}
