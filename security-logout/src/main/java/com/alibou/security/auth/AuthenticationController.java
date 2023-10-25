package com.alibou.security.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    //    @PostMapping("/google")
//    public ResponseEntity<String> authenticateGoogle(@RequestBody String idToken) {
//        // Your logic to handle the received ID token
//        // Validate the token, extract user information, and perform other actions
//        System.out.println("Received ID token from frontend: " + idToken);
//
//        // You can return a success message or any relevant response
//        return ResponseEntity.ok().body("{\"message\": \"ID token received successfully\"}");
//    }
    @PostMapping("/google")
    public ResponseEntity<String> authenticateGoogle(@RequestBody String idToken) throws  Exception{
        try {
            // Initialize the Google API client
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();


            // Build the Google ID token verifier
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList("360599613542-j8il63optd440q20iknopkmttkev7lj2.apps.googleusercontent.com")) // Replace with your actual client ID
                    .build();


            // Verify the ID token
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken != null) {
                // Extract user information from the payload
                GoogleIdToken.Payload payload = googleIdToken.getPayload();
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);
                System.out.println(payload.getEmail());

                // Add your logic to process the verified ID token
                // ...
                AuthenticationResponse response = service.authenticateViaGoogle(payload.getEmail());

                System.out.println("response.toString()");
                System.out.println(response.toString());
                return ResponseEntity.ok().body("{\"token\": \""+response.getToken()+"\"}");
            } else {
                System.out.println("Invalid ID token.");
                return ResponseEntity.badRequest().body("{\"error\": \"Invalid ID token\"}");
            }
        } catch (GeneralSecurityException | IOException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\": \"Internal Server Error\"}");
        }


    }
}
