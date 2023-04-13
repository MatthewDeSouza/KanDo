package com.group5.kando;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminHomePageController {

    @FXML
    private Label ToDo1;
    @FXML
    private Label ToDo2;
    @FXML
    private Label ToDo3;
    @FXML
    private Label ToDo4;
    @FXML
    private Label ToDo5;
    @FXML
    private Label Label1;
    @FXML
    private Label OutputLabel;

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void OutputLabel(MouseEvent event) {
        System.out.println("Add new components to the project.");
        }
    }
    