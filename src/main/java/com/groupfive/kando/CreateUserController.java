/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.groupfive.kando;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.groupfive.kando.backend.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author sam
 */
public class CreateUserController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnCreateAccount;

    private DatabaseReference databaseReference;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.info("Initializing CreateUserController");
        initializeFirebase();
    }

    private void initializeFirebase() {
        try {
            log.info("Initializing Firebase from provided API key");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("key.json")))
                    .setDatabaseUrl("https://your-database-url.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            // Get a reference to the users node
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

        } catch (FileNotFoundException e) {
            log.warn("API key file not found");
        } catch (IOException e) {
            log.warn("Error initializing Firebase. IOException: {}", e.getMessage());
        }
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        // Get user input from text fields
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Create a new user with the email
        User newUser = new User(email);

        // Set user's name, username, and password
        newUser.setPassword(password);

        // Save the user to Firebase
        saveUser(newUser);

        // Clear the text fields for the next user creation
        txtEmail.clear();
        txtPassword.clear();
    }

    private void saveUser(User user) {
        if (databaseReference != null) {
            log.info("Saving user [{}] to Firebase", user.getEmail());
            // Persist the user data using the user's UUID as the key
            databaseReference.child(user.getUUID().toString()).setValueAsync(user);
        }
    }
}