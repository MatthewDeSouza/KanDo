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
    private Label OutputLabel;
    @FXML
    private Label Todo6;

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    

    @FXML
    private void OutputLabel(MouseEvent e) {
        OutputLabel.setText("Descriptions");
        
    }

    @FXML
    private void Todo1Output(MouseEvent event) {
        OutputLabel.setText("Add functionality to the project");
    }

    @FXML
    private void Todo2Output(MouseEvent event) {
        OutputLabel.setText("Add method to View Users");
    }

    @FXML
    private void Todo3Output(MouseEvent event) {
        OutputLabel.setText("Create User Interface");
    }

    @FXML
    private void Todo4Output(MouseEvent event) {
        OutputLabel.setText("Create a page for users to create an account");
    }

    @FXML
    private void Todo5Output(MouseEvent event) {
        OutputLabel.setText("Add new features for users to navigate the project");
    }

    @FXML
    private void Todo6Output(MouseEvent event) {
        OutputLabel.setText("Create login page for users");
    }
}
    
    