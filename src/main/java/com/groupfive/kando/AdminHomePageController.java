package com.groupfive.kando;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1.Document;
import com.groupfive.kando.backend.classes.Project;
import com.groupfive.kando.backend.classes.Ticket;
import com.groupfive.kando.backend.status.Status;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
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

public class AdminHomePageController {

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
    @FXML
    private TextField textFieldUpdate;
    @FXML
    private TextField textFieldDelete;
    @FXML
    private ComboBox comboBoxUpdate;
    private ObservableList<String> statusList2;
    private ObservableList<String> projects;
    private ObservableList<String> statusList;
    private ObservableList<Ticket> toDoTickets;
    private ObservableList<Ticket> doingTickets;
    private ObservableList<Ticket> doneTickets;
    private Firestore db;

    public void initialize() {
        statusList = comboBoxStatus.getItems();
        statusList2 = comboBoxUpdate.getItems();
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
        statusList2.add("To Do");
        statusList2.add("Doing");
        statusList2.add("Done");

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

    public void handleUpdateStatus() {
        try {
            String name = textFieldUpdate.getText();
            String status = comboBoxUpdate.getValue().toString();
            String docId = "";

            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                docId = doc.getString(name);
            }
            DocumentReference docRef = db.collection("Tickets").document(docId);
            ApiFuture<WriteResult> future = docRef.update("status", status);
            WriteResult result = future.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleDelete() {
        try {
            String name = textFieldDelete.getText();
            String docId = "";

            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                docId = doc.getString(name);
            }
            ApiFuture<WriteResult> writeResult = db.collection("Tickets").document(docId).delete();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
    
}
