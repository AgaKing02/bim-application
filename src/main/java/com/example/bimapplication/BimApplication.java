package com.example.bimapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BimApplication {
    public static void main(String[] args) {
//        System.setProperty("javax.net.ssl.trustStore", "C:/Users/LA/IdeaProjects/bim-application/src/main/resources/test-network/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/truststore.jks");
//        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//        System.setProperty("javax.net.ssl.trustStore", "/usr/lib/jvm/java-21-openjdk-amd64/lib/security/cacerts");
//        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        SpringApplication.run(BimApplication.class, args);
    }
}
