module com.group5.kando {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.group5.kando to javafx.fxml;
    exports com.group5.kando;
}
