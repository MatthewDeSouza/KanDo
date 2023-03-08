module com.group5.kando {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.api.apicommon;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires google.cloud.firestore;

    opens com.group5.kando to javafx.fxml;
    exports com.group5.kando;
}
