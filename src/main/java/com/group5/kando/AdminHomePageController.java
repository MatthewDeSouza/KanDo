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
    if(true) Todo1:{
    OutputLabel.setText("Add functionality to the project");
    } if(true) Todo2:{
    OutputLabel.setText("Add functionality to the project");
    }
    if(true) Todo3:{
    OutputLabel.setText("Add functionality to the project");
        }
    if(true) Todo4:{
    OutputLabel.setText("Add functionality to the project");
    }
    if(true) Todo5:{
    OutputLabel.setText("Add functionality to the project");
    }
    if(true) Todo6:{
    OutputLabel.setText("Add functionality to the project");
    }
    }
}
    
    