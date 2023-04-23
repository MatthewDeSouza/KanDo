package com.groupfive.kando;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1.Document;
import com.groupfive.kando.backend.classes.Project;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class AdminHomePageController {

    @FXML
    private ComboBox comboBoxProject;
    private ObservableList<String> projects;
    private Firestore db;

    public void initialize() {
        try {
            projects = comboBoxProject.getItems();
            try {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("key.json")))
                        .build();
                FirebaseApp.initializeApp(options);
                db = FirestoreClient.getFirestore();
            } catch (Exception e) {
                System.out.println("Could not connect to Firestore.");
                e.printStackTrace();
            }
            
            ApiFuture<QuerySnapshot> query = db.collection("Projects").get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                projects.add(document.get("name").toString());
            }
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

    }
}

