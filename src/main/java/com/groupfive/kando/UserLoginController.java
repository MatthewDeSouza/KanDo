package com.groupfive.kando;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.groupfive.kando.backend.classes.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserLoginController {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPassword;

    public void handleLogin() {
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both email and password!");
            alert.showAndWait();
            return;
        }

        try {
            UserRecord user = FirebaseAuth.getInstance().getUserByEmail(email);
            if (user != null) {
                switch (email) {
                    case "admin@kando.com":
                        App.setRoot("AdminHomePage");
                        break;
                    case "manager@kando.com":
                        App.setRoot("ManagerHomePage");
                        break;
                    case "teammember@kando.com":
                        App.setRoot("TeamMemberHomePage");
                        break;
                    default:
                        App.setRoot("TeamMemberHomePage");
                        break;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
