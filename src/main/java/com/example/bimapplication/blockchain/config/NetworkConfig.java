package com.example.bimapplication.blockchain.config;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    public Network getNetworkGlobal() {
        try {
            return getNetwork();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Network getNetwork() throws Exception {
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get(getNetworkPath());

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            return gateway.getNetwork(GLOBAL_CHANNEL);
        } catch (Exception e) {
            throw e;
        }
    }
}

