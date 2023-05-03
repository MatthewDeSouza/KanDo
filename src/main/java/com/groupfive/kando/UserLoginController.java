package com.groupfive.kando;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The UserLoginController class GUI logic for the app's login page.
 *
 * @author Chris Masoud
 * @author Matthew DeSouza
 */
public class UserLoginController {
    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPassword;

    /**
     * The initialize() method runs at the start of the app and contains code to
     * set up the app.
     */
    public void initialize() {
        log.info("Initializing Firebase");
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("key.json")))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException ex) {
            log.error("API Key file not found!");
        } catch (IOException ex) {
            log.error("An IOException occurred. IOException: {}", ex.getMessage());
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
            log.warn("Email or password field is empty!");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both email and password!");
            alert.showAndWait();
            return;
        }

        try {
            log.info("Authenticating user [{}]", email);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            UserRecord user = auth.getUserByEmail(email);
            if (user != null) {
                switch (email) {
                    case "admin@kando.com":
                        log.info("Admin user [{}] logged in", email);
                        App.setRoot("AdminHomePage");
                        break;
                    case "manager@kando.com":
                        log.info("Manager user [{}] logged in", email);
                        App.setRoot("ManagerHomePage");
                        break;
                    default:
                        log.info("Team member user [{}] logged in", email);
                        App.setRoot("TeamMemberHomePage");
                        break;
                }
            } else {
                log.warn("Invalid email or password!");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            log.warn("Error authenticating user. Exception: {}", e.getMessage());
        }
    }
}
