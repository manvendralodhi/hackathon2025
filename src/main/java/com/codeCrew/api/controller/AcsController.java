package com.codeCrew.api.controller;


import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;

import com.azure.communication.identity.CommunicationIdentityClientBuilder;

import com.azure.communication.identity.models.CommunicationUserIdentifierAndToken;

import com.azure.communication.identity.models.CommunicationTokenScope;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;

@RestController()
//@CrossOrigin(origins = "*")
public class AcsController {

    private final CommunicationIdentityClient identityClient;

    public AcsController(@Value("${azure.communication.connection-string}") String connectionString) {

        this.identityClient = new CommunicationIdentityClientBuilder()

                .connectionString(connectionString)

                .buildClient();

    }

//    @GetMapping("/")
//
//    public String index() {
//
//        return "ACS Backend is running!";
//
//    }
//
//    @GetMapping("/htoken")
//
//    public Map<String, String> hardcodedToken() {
//
//        return Map.of(
//
//                "token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRCQ...",
//
//                "userId", "8:acs:a67ff070-2c82-40a9-a677-2d3c16e72be1_00000027..."
//
//        );
//
//    }
//
    @PostMapping("/register")

    public Map<String, String> register() {

        try {

            CommunicationUserIdentifier user = identityClient.createUser();

            return Map.of("userId", user.getId());

        } catch (Exception e) {

            throw new RuntimeException("Error creating user ID", e);

        }

    }

    @GetMapping("/token")
    public Map<String, String> getToken() {
        try {

            CommunicationUserIdentifierAndToken userWithToken =

                    identityClient.createUserAndToken(List.of(CommunicationTokenScope.VOIP));

            return Map.of(

                    "userId", userWithToken.getUser().getId(),

                    "token", userWithToken.getUserToken().getToken()

            );

        } catch (Exception e) {

            throw new RuntimeException("Error generating token", e);

        }

    }

}


