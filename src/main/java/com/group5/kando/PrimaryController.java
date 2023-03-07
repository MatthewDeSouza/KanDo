package com.group5.kando;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    FileInputStream serviceAccount
            = new FileInputStream("/Users/cgmasoud/NetBeansProjects/KanDo/kando-project-management-firebase-adminsdk-nkidp-256da28ef7.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    FirebaseApp.initializeApp (options);

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
