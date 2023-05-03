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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TeamMemberHomePageController class contains GUI logic for the team 
 * member's homepage.
 * @author Chris Masoud
 * @author Matthew Desouza
 */
public class TeamMemberHomePageController {
    private static final Logger log = LoggerFactory.getLogger(TeamMemberHomePageController.class);

    @FXML
    private ComboBox comboBoxProject;
    @FXML
    private ListView listViewToDo;
    @FXML
    private ListView listViewDoing;
    @FXML
    private ListView listViewDone;
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
            log.warn("InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
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
            log.warn("InterruptedException: {}", ex.getMessage());
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
        } catch (RuntimeException ex) {
            log.warn("No project selected! RuntimeException: {}", ex.getMessage());
        }
    }

    /**
     * The handleAddTask() method adds a new ticket into Firestore.
     */
    public void handleAddTask() {
        try {
            log.info("Adding new task.");
            String name = textFieldTaskName.getText();
            String desc = textFieldTaskDesc.getText();
            String type = textFieldTaskType.getText();
            String status = comboBoxStatus.getValue().toString();
            String project = comboBoxProject.getValue().toString();

            log.info("Querying Firestore.");
            DocumentReference docRef = db.collection("Tickets").document(UUID.randomUUID().toString());
            Map<String, String> data = new HashMap<>();
            data.put("name", name);
            data.put("description", desc);
            data.put("type", type);
            data.put("status", status);
            data.put("project", project);
            ApiFuture<WriteResult> result = docRef.set(data);
        } catch (RuntimeException ex) {
            log.warn("No project selected! RuntimeException: {}", ex.getMessage());
        }


        textFieldTaskName.clear();
        textFieldTaskDesc.clear();
        textFieldTaskType.clear();
    }

    /**
     * The handleUpdateStatus() method changes the status of a ticket.
     */
    public void handleUpdateStatus() {
        try {
            log.info("Updating status.");
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
            log.warn("InterruptedException: {}", ex.getMessage());
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
            ex.printStackTrace();
        }
        textFieldUpdate.clear();
    }

    /**
     * The handleDelete() method deletes a ticket from Firestore.
     */
    public void handleDelete() {
        try {
            log.info("Deleting ticket.");
            String name = textFieldDelete.getText();
            String docId = "";

            ApiFuture<QuerySnapshot> query = db.collection("Tickets")
                    .whereEqualTo("name", name)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            DocumentSnapshot doc = documents.get(0);
            docId = doc.getId();
            log.warn("Deleting ticket with ID: {}", docId);
            ApiFuture<WriteResult> writeResult = db.collection("Tickets").document(docId).delete();
        } catch (InterruptedException ex) {
            log.warn("InterruptedException: {}", ex.getMessage());
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            log.warn("ExecutionException: {}", ex.getMessage());
            ex.printStackTrace();
        }
        textFieldDelete.clear();
    }
}
