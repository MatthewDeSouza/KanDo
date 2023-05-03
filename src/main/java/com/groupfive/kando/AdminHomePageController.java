package com.groupfive.kando;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.groupfive.kando.backend.classes.Ticket;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The AdminHomePageController class contains GUI logic for the administrator's
 * homepage.
 *
 * @author Chris Masoud
 * @author Matthew Desouza
 */
public class AdminHomePageController {
    private static final Logger log = LoggerFactory.getLogger(AdminHomePageController.class);

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
        log.info("Initializing AdminHomePageController");
        statusList = comboBoxStatus.getItems();
        log.info("statusList: {}", statusList);
        statusList2 = comboBoxUpdate.getItems();
        log.info("statusList2: {}", statusList2);
        projects = comboBoxProject.getItems();
        log.info("projects: {}", projects);
        toDoTickets = listViewToDo.getItems();
        log.info("toDoTickets: {}", toDoTickets);
        doingTickets = listViewDoing.getItems();
        log.info("doingTickets: {}", doingTickets);
        doneTickets = listViewDone.getItems();
        log.info("doneTickets: {}", doneTickets);

        db = FirestoreClient.getFirestore();
        try {
            log.info("Getting projects from Firestore.");
            ApiFuture<QuerySnapshot> query = db.collection("Projects").get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                projects.add(document.get("name").toString());
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
        }
        log.info("Initializing status lists.");
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

        log.info("Creating new user: {}", email);
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setDisplayName(name)
                .setDisabled(false);

        UserRecord userRecord;
        log.info("Adding user to Firebase.");
        try {
            log.info("Creating user record.");
            userRecord = FirebaseAuth.getInstance().createUser(request);
            log.info("Successfully created new user: {}", userRecord.getUid());

        } catch (FirebaseAuthException ex) {
            log.warn("FirebaseAuthException: {}", ex.getMessage());
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
        log.info("Getting tickets from Firestore.");
        try {
            String projectName = comboBoxProject.getValue().toString();
            log.info("Querying Firestore for tickets from project: {}", projectName);
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("project", projectName)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                Ticket ticket = new Ticket(doc.getString("name"), doc.getString("description"), doc.getString("status"), doc.getString("type"));
                switch (ticket.getStatus()) {
                    case "Doing":
                        log.info("Adding ticket to doingTickets: {}", ticket);
                        doingTickets.add(ticket);
                        break;
                    case "Done":
                        log.info("Adding ticket to doneTickets: {}", ticket);
                        doneTickets.add(ticket);
                        break;
                    default:
                        log.info("Adding ticket to toDoTickets: {}", ticket);
                        toDoTickets.add(ticket);
                        break;
                }
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
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

        log.info("Adding project to Firestore.");
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
        if (result.isCancelled()) {
            log.warn("Adding project [{}] to Firestore was cancelled.", name);
        } else if (result.isDone()) {
            log.info("Successfully added project [{}] to Firestore.", name);
        }
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

        log.info("Adding ticket to Firestore.");
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
        if (result.isCancelled()) {
            log.warn("Adding ticket [{}] to Firestore was cancelled.", name);
        } else if (result.isDone()) {
            log.info("Successfully added ticket [{}] to Firestore.", name);
        }
    }

    /**
     * The handleUpdateStatus() method changes the status of a ticket.
     */
    public void handleUpdateStatus() {
        try {
            String name = textFieldUpdate.getText();
            String status = comboBoxUpdate.getValue().toString();
            String docId = "";

            log.info("Updating ticket status in Firestore.");
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            DocumentSnapshot doc = documents.get(0);
            docId = doc.getId();
            DocumentReference docRef = db.collection("Tickets").document(docId);
            ApiFuture<WriteResult> future = docRef.update("status", status);
            WriteResult result = future.get();
            if (result == null) {
                log.warn("Updating ticket [{}] status in Firestore was cancelled.", name);
            } else {
                log.info("Successfully updated ticket [{}] status in Firestore.", name);
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
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

            log.info("Deleting ticket [{}] from Firestore.", name);
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            DocumentSnapshot doc = documents.get(0);
            docId = doc.getId();
            ApiFuture<WriteResult> writeResult = db.collection("Tickets").document(docId).delete();
            if (writeResult.isCancelled()) {
                log.warn("Deleting ticket [{}] from Firestore was cancelled.", name);
            } else if (writeResult.isDone()) {
                log.info("Successfully deleted ticket [{}] from Firestore.", name);
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
        }
        textFieldDelete.clear();
    }
}
