module com.groupfive.kando {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires proto.google.cloud.firestore.v1;

    opens com.groupfive.kando to javafx.fxml;
    exports com.groupfive.kando;
    exports com.groupfive.kando.backend.classes;
    opens com.groupfive.kando.backend.classes to javafx.fxml;
}
