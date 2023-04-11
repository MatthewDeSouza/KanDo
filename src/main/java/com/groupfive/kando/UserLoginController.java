package com.groupfive.kando;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserLoginController {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPassword;

    public void initialize() {

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("key.json")))
                    .build();
            FirebaseApp.initializeApp(options);

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
