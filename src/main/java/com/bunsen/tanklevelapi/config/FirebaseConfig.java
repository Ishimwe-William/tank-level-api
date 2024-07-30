package com.bunsen.tanklevelapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${rtdb.name}")
    private String dbName;

    @Bean
    public FirebaseDatabase firebaseDatabase() throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource("static/gprs-with-firebase-85713-firebase-adminsdk-h5xha-80f3820c9f.json");

        if (!serviceAccount.exists()) {
            logger.error("Service account file not found!");
            throw new FileNotFoundException("Service account file not found");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setDatabaseUrl(dbName)
                .build();

        FirebaseApp.initializeApp(options);

        logger.info("Firebase initialized with database URL: " + dbName);

        return FirebaseDatabase.getInstance();
    }
}
