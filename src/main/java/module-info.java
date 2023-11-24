module com.example.donimusic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    opens com.example.donimusic to javafx.fxml;
    exports com.example.donimusic;
    exports com.example.donimusic.controlador;
    opens com.example.donimusic.controlador to javafx.fxml;
    exports com.example.donimusic.modelo;
    opens com.example.donimusic.modelo to javafx.fxml;
}