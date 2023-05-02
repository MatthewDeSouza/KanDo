package com.groupfive.kando;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * The UserLoginController class GUI logic for the app's login page.
 *
 * @author Chris Masoud
 * @author Matthew DeSouza
 */
public class UserLoginController {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPassword;

    /**
     * The initialize() method runs at the start of the app and contains code to
     * set up the app.
     */
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("key.json")))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    /**
     * The handleLogin() method authenticates and opens the appropriate homepage
     * depending on the user's access level.
     */
    public void handleLogin() {
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both email and password!");
            alert.showAndWait();
            return;
        }

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            UserRecord user = auth.getUserByEmail(email);
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
