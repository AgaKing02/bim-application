package com.example.bimapplication.blockchain.config;

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
@Configuration
public class GatewayConfig {

    @Value("${fabric.walletDirectory}")
    private String walletDirectory;

    @Value("${fabric.networkConfigPath}")
    private String networkConfigPath;

    @Value("${fabric.certificatePath}")
    private String certificatePath;

    @Value("${fabric.privateKeyPath}")
    private String privateKeyPath;

    @Value("${fabric.mspid}")
    private String mspid;

    @Value("${fabric.username}")
    private String username;

    @Value("${fabric.channelName}")
    private String channelName;

    @Value("${fabric.contractName}")
    private String contractName;


    @Bean
    public Gateway connectGateway() throws IOException, InvalidKeyException, CertificateException {
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(this.walletDirectory));
        X509Certificate certificate = readX509Certificate(Paths.get(this.certificatePath));
        PrivateKey privateKey = getPrivateKey(Paths.get(this.privateKeyPath));
        wallet.put(username, Identities.newX509Identity(this.mspid, certificate, privateKey));

        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, username)
                .networkConfig(Paths.get(this.networkConfigPath));

        //连接网关
        return builder.connect();
    }


    @Bean
    public Network network(Gateway gateway) {
        return gateway.getNetwork(this.channelName);
    }


    private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }

    private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
}
