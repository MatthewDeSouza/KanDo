package com.groupfive.kando;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.groupfive.kando.backend.classes.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserLoginController {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Label labelLoginFailed;
    private Firestore db;
    private User admin = new User("admin@kando.com");
    private User manager = new User("manager@kando.com");
    private User teamMember = new User("teammember@kando.com");

    public void initialize() {
        admin.setPassword("abcdef");
        manager.setPassword("abcdef");
        teamMember.setPassword("abcdef");
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
    }

    public void handleLogin() {
        try {
            String email = textFieldEmail.getText();
            String password = textFieldPassword.getText();
            User user = new User(email);
            user.setPassword(password);
            if (user.isEmailPassEqual(admin)) {
                App.setRoot("AdminHomePage");
            } else if (user.isEmailPassEqual(manager)) {
                App.setRoot("ManagerHomePage");
            } else if (user.isEmailPassEqual(teamMember)) {
                App.setRoot("TeamMemberHomePage");
            } else {
                labelLoginFailed.setVisible(true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
