package com.example.donimusic;

import com.example.donimusic.modelo.Conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Conexion con = new Conexion();
        con.conectar();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("iniciarSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DoniMusic");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}