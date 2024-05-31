package com.example.bimapplication.blockchain.config;

import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@DependsOn({"enrollRegisterUserConfig", "enrollAdminConfig", "fabricConstants"})
public class NetworkConfig {

    public static final String GLOBAL_CHANNEL = "samplechannel";

    @Value("${blockchain.network.config.path}")
    private String networkPath;

    private static NetworkConfig instance;

    @PostConstruct
    private void init() {
        instance = this;
        System.out.println(networkPath);
    }

    public static String getNetworkPath() {
        return instance.networkPath;
    }

    @Bean
    public Network getNetworkGlobal(Gateway gateway) {
        try {
            return getNetwork(gateway);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Gateway.Builder getGatewayBuilder() throws IOException {
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get(getNetworkPath());

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
        return builder;
    }

    @Bean
    public Gateway getGateway(Gateway.Builder builder) {
        return builder.connect();
    }

    public static Network getNetwork(Gateway gateway) throws Exception {

        Network network = gateway.getNetwork(GLOBAL_CHANNEL);

        Contract userTransfer = network.getContract("UserTransfer");

//        byte[] bytes = userTransfer.submitTransaction("addNewUser", "6", "aga", "aga", "aga@gmail.com", "agahan02", "USER");
//
//        System.out.println("Successfully added : " + new String(bytes));

        byte[] queryUserByIds = userTransfer.evaluateTransaction("queryUserById", "1");

        System.out.println("Respones from blockchain: " + new String(queryUserByIds));
        return network;

    }
}

