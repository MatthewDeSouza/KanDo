package com.groupfive.kando;

import java.io.IOException;
import javafx.fxml.FXML;

public class AdminHomePageController {

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}