package com.group5.kando;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXML;

public class PrimaryController {

    public void initialize() {
        try {
            InputStream serviceAccount = new FileInputStream("/Users/cgmasoud/NetBeansProjects/KanDo/kando-project-management-firebase-adminsdk-nkidp-256da28ef7.json");
    
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            System.out.println("Could not connect to Firestore.");
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
