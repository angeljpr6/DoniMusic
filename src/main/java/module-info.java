module com.example.donimusic {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.donimusic to javafx.fxml;
    exports com.example.donimusic;
}