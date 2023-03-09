package cz.my.snemovna.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Profile("!test")
public class FirebaseConfiguration {

    @Bean
    GoogleCredentials googleCredentials(@Value("${firebase.serviceAccount}") String serviceAccountPath) throws IOException {
        if (serviceAccountPath != null) {
            try (FileInputStream serviceAccount = new FileInputStream(serviceAccountPath)) {
                return GoogleCredentials.fromStream(serviceAccount);
            }
        } else {
            // Use standard credentials chain. Useful when running inside GKE.
            return GoogleCredentials.getApplicationDefault();
        }
    }

    @Bean
    FirebaseApp firebaseApp(GoogleCredentials credentials, @Value("${firebase.datasource.url}") String firebaseDbUrl) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setDatabaseUrl(firebaseDbUrl)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
