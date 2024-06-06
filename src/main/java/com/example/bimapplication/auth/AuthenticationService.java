package com.example.bimapplication.auth;

import com.example.bimapplication.auth.model.AuthenticationRequest;
import com.example.bimapplication.auth.model.AuthenticationResponse;
import com.example.bimapplication.auth.model.RegisterRequest;
import com.example.bimapplication.config.JwtService;
import com.example.bimapplication.user.Role;
import com.example.bimapplication.user.User;
import com.example.bimapplication.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws ContractException, InterruptedException, TimeoutException {
        var user = User.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ContractException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
