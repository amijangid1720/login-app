//package com.alibou.security.auth;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
////import com.google.api.client.json.jackson2.JacksonFactory;
//
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.imageio.spi.IIORegistry;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//@RestController
//@RequestMapping("api/v1/auth")
//public class GoogleSignInController {
//
//    private static final String CLIENT_ID = "360599613542-j8il63optd440q20iknopkmttkev7lj2.apps.googleusercontent.com";
//    private static GsonFactory JacksonFactory;
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final HttpTransport HTTP_TRANSPORT;
//
//    static {
//        try {
//            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        } catch (Exception e) {
//            throw new RuntimeException("Error initializing HTTP transport", e);
//        }
//    }
//
//    @PostMapping("/google")
//    public ResponseEntity<String> authenticateGoogle(@RequestBody String idToken) {
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
//                    .setAudience(Collections.singletonList(CLIENT_ID))
//                    .build();
//
//            GoogleIdToken googleIdToken = verifier.verify(idToken);
//            if (googleIdToken != null) {
//                GoogleIdToken.Payload payload = googleIdToken.getPayload();
//
//                // Process the payload as needed
//                String userId = payload.getSubject();
//                System.out.println("User ID: " + userId);
//
//                // ... (rest of your processing logic)
//
//                return ResponseEntity.ok().body("{\"message\": \"ID token verified successfully\"}");
//            } else {
//                System.out.println("Invalid ID token.");
//                return ResponseEntity.badRequest().body("{\"error\": \"Invalid ID token\"}");
//            }
//        } catch (GeneralSecurityException | IOException e) {
//            // Handle exceptions appropriately
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("{\"error\": \"Internal Server Error\"}");
//        }
//    }
//}
