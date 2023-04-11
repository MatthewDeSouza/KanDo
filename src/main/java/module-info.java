module com.groupfive.kando {
    requires javafx.controls;
    requires javafx.fxml;
    //requires com.google.api.apicommon;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires google.cloud.firestore;

    opens com.groupfive.kando to javafx.fxml;
    exports com.groupfive.kando;
    exports com.groupfive.kando.backend.exception;
    exports com.groupfive.kando.backend.classes;
    exports com.groupfive.kando.backend.status;
    opens com.groupfive.kando.backend.classes to javafx.fxml;
}
