package com.groupfive.kando;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
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
 * The ManagerHomePageController class contains GUI logic for the manager's
 * homepage.
 *
 * @author Chris Masoud
 * @author Matthew Desouza
 */
public class ManagerHomePageController {
    private static final Logger log = LoggerFactory.getLogger(ManagerHomePageController.class);

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

    /**
     * The initialize() method runs at the start of the app and contains code to
     * set up the app.
     */
    public void initialize() {
        log.info("Initializing ManagerHomePageController");
        statusList = comboBoxStatus.getItems();
        statusList2 = comboBoxUpdate.getItems();
        projects = comboBoxProject.getItems();
        toDoTickets = listViewToDo.getItems();
        doingTickets = listViewDoing.getItems();
        doneTickets = listViewDone.getItems();
        db = FirestoreClient.getFirestore();
        try {
            log.info("Retrieving projects from database");
            ApiFuture<QuerySnapshot> query = db.collection("Projects").get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                String name = document.get("name").toString();
                log.info("Adding project [{}] to list", name);
                projects.add(name);
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException while retrieving projects from database. InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException while retrieving projects from database. ExecutionException: {}", ex.getMessage());
        }
        statusList.add("To Do");
        statusList.add("Doing");
        statusList.add("Done");
        statusList2.add("To Do");
        statusList2.add("Doing");
        statusList2.add("Done");

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
            log.info("Retrieving tickets from database");
            String projectName = comboBoxProject.getValue().toString();
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("project", projectName)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                log.info("Adding ticket [{}] to list", doc.getString("name"));
                Ticket ticket = new Ticket(doc.getString("name"), doc.getString("description"), doc.getString("status"), doc.getString("type"));
                switch (ticket.getStatus()) {
                    case "To Do":
                        log.info("Adding ticket [{}] to To Do list", doc.getString("name"));
                        toDoTickets.add(ticket);
                        break;
                    case "Doing":
                        log.info("Adding ticket [{}] to Doing list", doc.getString("name"));
                        doingTickets.add(ticket);
                        break;
                    case "Done":
                        log.info("Adding ticket [{}] to Done list", doc.getString("name"));
                        doneTickets.add(ticket);
                        break;
                    default:
                        log.info("Adding ticket [{}] to To Do list", doc.getString("name"));
                        toDoTickets.add(ticket);
                        break;
                }
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException while retrieving tickets from database. InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException while retrieving tickets from database. ExecutionException: {}", ex.getMessage());
        } catch (RuntimeException ex) {
            log.warn("No project selected! RuntimeException: {}", ex.getMessage());
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
        log.info("Adding project [{}] to database", name);

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
            log.warn("Adding project [{}] to database was cancelled", name);
        } else if (result.isDone()) {
            log.info("Adding project [{}] to database was successful", name);
        } else {
            log.warn("Adding project [{}] to database was unsuccessful", name);
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
        log.info("Adding ticket [{}] to database", name);

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
            log.warn("Adding ticket [{}] to database was cancelled", name);
        } else if (result.isDone()) {
            log.info("Adding ticket [{}] to database was successful", name);
        } else {
            log.warn("Adding ticket [{}] to database was unsuccessful", name);
        }
    }

    /**
     * The handleUpdateStatus() method changes the status of a ticket.
     */
    public void handleUpdateStatus() {
        log.info("Updating ticket status");
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
            if (future.get() == null) {
                log.warn("Updating ticket status was unsuccessful");
            } else {
                log.info("Updating ticket status was successful");
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException while updating ticket status. InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException while updating ticket status. ExecutionException: {}", ex.getMessage());
        }
        textFieldUpdate.clear();
    }

    /**
     * The handleDelete() method deletes a ticket from Firestore.
     */
    public void handleDelete() {
        log.info("Deleting ticket");
        try {
            String name = textFieldDelete.getText();
            String docId = "";

            log.info("Retrieving ticket [{}] from database", name);
            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            DocumentSnapshot doc = documents.get(0);
            docId = doc.getId();
            ApiFuture<WriteResult> writeResult = db.collection("Tickets").document(docId).delete();
            if (writeResult.get() == null) {
                log.warn("Deleting ticket was unsuccessful");
            } else {
                log.info("Deleting ticket was successful");
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException while deleting ticket. InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException while deleting ticket. ExecutionException: {}", ex.getMessage());
        }
        textFieldDelete.clear();
    }
}
