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
import javafx.scene.control.TextField;

public class UserLoginController {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPassword;

    public void initialize() {

        try {
            InputStream serviceAccount = new FileInputStream("/Users/cgmasoud/NetBeansProjects/KanDo/kando-project-management-firebase-adminsdk-nkidp-256da28ef7.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId("kando-project-management")
                    .build();
            FirebaseApp.initializeApp(options);
            Firestore db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            System.out.println("Could not connect to Firestore.");
            e.printStackTrace();
        }
    }

    public void handleLogin() {
        //auth = FirebaseAuth.getInstance();
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();
        //auth.signInWithEmailAndPassword(email, password);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("HomePage");
    }
}
