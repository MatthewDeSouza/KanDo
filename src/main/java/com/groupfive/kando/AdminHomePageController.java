package com.groupfive.kando;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.groupfive.kando.backend.classes.Ticket;
import java.io.IOException;
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

/**
 * The AdminHomePageController class contains GUI logic for the administrator's
 * homepage.
 *
 * @author Chris Masoud
 * @author Matthew Desouza
 */
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
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldUserName;
    @FXML
    private TextField textFieldPassword;
    private ObservableList<String> statusList2;
    private ObservableList<String> projects;
    private ObservableList<String> statusList;
    private ObservableList<Ticket> toDoTickets;
    private ObservableList<Ticket> doingTickets;
    private ObservableList<Ticket> doneTickets;
    private Firestore db;

    /**
     * The initialize() method runs at the start of the app and contains code to
     * set up the app.
     */
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

    /**
     * The handleCreateUser() method registers a new user into Firebase.
     */
    public void handleCreateUser() {
        String email = textFieldEmail.getText();
        String name = textFieldUserName.getText();
        String password = textFieldPassword.getText();

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setDisplayName(name)
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());

        } catch (FirebaseAuthException ex) {
            ex.printStackTrace();
        }
        textFieldEmail.clear();
        textFieldUserName.clear();
        textFieldPassword.clear();
    }

    /**
     * The handleProjectSelection() method loads the ListViews with tickets from
     * the project that the user selected.
     */
    public void handleProjectSelection() {
        toDoTickets.clear();
        doingTickets.clear();
        doneTickets.clear();
        try {
            String projectName = comboBoxProject.getValue().toString();
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("project", projectName)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                Ticket ticket = new Ticket(doc.getString("name"), doc.getString("description"), doc.getString("status"), doc.getString("type"));
                switch (ticket.getStatus()) {
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

    /**
     * The handleAddProject() method adds a new project into Firestore.
     */
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
        projects.add(name);
        textFieldProjectName.clear();
        textFieldProjectDesc.clear();
    }

    /**
     * The handleAddTask() method adds a new ticket into Firestore.
     */
    public void handleAddTask() {
        String name = textFieldTaskName.getText();
        String desc = textFieldTaskDesc.getText();
        String type = textFieldTaskType.getText();
        String status = comboBoxStatus.getValue().toString();
        String project = comboBoxProject.getValue().toString();

        DocumentReference docRef = db.collection("Tickets").document(UUID.randomUUID().toString());
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("description", desc);
        data.put("type", type);
        data.put("status", status);
        data.put("project", project);
        ApiFuture<WriteResult> result = docRef.set(data);
        textFieldTaskName.clear();
        textFieldTaskDesc.clear();
        textFieldTaskType.clear();
    }

    /**
     * The handleUpdateStatus() method changes the status of a ticket.
     */
    public void handleUpdateStatus() {
        try {
            String name = textFieldUpdate.getText();
            String status = comboBoxUpdate.getValue().toString();
            String docId = "";

            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            DocumentSnapshot doc = documents.get(0);
            docId = doc.getId();
            DocumentReference docRef = db.collection("Tickets").document(docId);
            ApiFuture<WriteResult> future = docRef.update("status", status);
            WriteResult result = future.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
        textFieldUpdate.clear();
    }

    /**
     * The handleDelete() method deletes a ticket from Firestore.
     */
    public void handleDelete() {
        try {
            String name = textFieldDelete.getText();
            String docId = "";

            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            DocumentSnapshot doc = documents.get(0);
            docId = doc.getId();
            ApiFuture<WriteResult> writeResult = db.collection("Tickets").document(docId).delete();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
        textFieldDelete.clear();
    }
    
}
