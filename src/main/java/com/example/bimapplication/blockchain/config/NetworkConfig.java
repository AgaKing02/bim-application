package com.example.bimapplication.blockchain.config;

import org.hyperledger.fabric.gateway.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.bimapplication.blockchain.config.FabricConstants.NETWORK_PATH;

@Component
@DependsOn({"enrollRegisterUserConfig", "enrollAdminConfig", "fabricConstants"})
public class NetworkConfig {
    public static final String GLOBAL_CHANNEL = "samplechannel";


    @Bean
    public static Network getNetworkGlobal() {
        try {
            return getNetwork();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Network getNetwork() throws Exception {
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get(NETWORK_PATH);

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
