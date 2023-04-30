package com.groupfive.kando;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.groupfive.kando.backend.classes.Ticket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ManagerHomePageController {
    @FXML
    private ComboBox comboBoxProject;
    @FXML
    private ListView listViewToDo;
    @FXML
    private ListView listViewDoing;
    @FXML
    private ListView listViewDone;
    @FXML
    private TextField textFieldProjectName;
    @FXML
    private TextField textFieldProjectDesc;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private DatePicker datePickerEnd;
    @FXML
    private TextField textFieldTaskName;
    @FXML
    private TextField textFieldTaskDesc;
    @FXML
    private TextField textFieldTaskType;
    @FXML
    private ComboBox comboBoxStatus;
    private ObservableList<String> projects;
    private ObservableList<String> statusList;
    private ObservableList<Ticket> toDoTickets;
    private ObservableList<Ticket> doingTickets;
    private ObservableList<Ticket> doneTickets;
    private Firestore db;

    public void initialize() {
        statusList = comboBoxStatus.getItems();
        projects = comboBoxProject.getItems();
        toDoTickets = listViewToDo.getItems();
        doingTickets = listViewDoing.getItems();
        doneTickets = listViewDone.getItems();
        db = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> query = db.collection("Projects").get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                projects.add(document.get("name").toString());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
        statusList.add("To Do");
        statusList.add("Doing");
        statusList.add("Done");

    }
    
    public void handleProjectSelection() {
        try {
            String projectName = comboBoxProject.getValue().toString();
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("project", projectName)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                Ticket ticket = new Ticket(doc.getString("name"), doc.getString("description"));
                ticket.setType(doc.getString("type"));
                switch (doc.getString("status")) {
                    case "To Do":
                        toDoTickets.add(ticket);
                        break;
                    case "Doing":
                        doingTickets.add(ticket);
                        break;
                    case "Done":
                        doneTickets.add(ticket);
                        break;
                    default:
                        toDoTickets.add(ticket);
                        break;
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    public void handleAddProject() {
        String name = textFieldProjectName.getText();
        String desc = textFieldProjectDesc.getText();
        String startDate = datePickerStart.getValue().toString();
        String endDate = datePickerEnd.getValue().toString();
        
        DocumentReference docRef = db.collection("Projects").document(UUID.randomUUID().toString());
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("description", desc);
        data.put("startDate", startDate);
        data.put("endDate", endDate);
        ApiFuture<WriteResult> result = docRef.set(data);
    }
    
    public void handleAddTask() {
        String name = textFieldTaskName.getText();
        String desc = textFieldTaskDesc.getText();
        String type = textFieldTaskType.getText();
        String status = comboBoxStatus.getValue().toString();
        
        DocumentReference docRef = db.collection("Tickets").document(UUID.randomUUID().toString());
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("description", desc);
        data.put("type", type);
        data.put("status", status);
        ApiFuture<WriteResult> result = docRef.set(data);
    }
}
