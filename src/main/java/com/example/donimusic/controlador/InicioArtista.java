package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Conexiones.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class InicioArtista implements Initializable {

    @FXML
    private static Connection c = Conexion.con;
    public Pane iconoError;
    public Pane errorUsuarioInexist;
    public Pane iconoError1;
    public TextField usuarioTextField;
    public PasswordField contrasenaTextField;
    public Label noArtistaBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/IconoError.png")));
        ImageView imageViewError = new ImageView(imageError);

        iconoError.getChildren().add(imageViewError);

    }

    public void iniciarSesion(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/homeArtista.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage homeArtistaStage = new Stage();
        homeArtistaStage.setTitle("Home Artista");
        homeArtistaStage.setResizable(false);
        homeArtistaStage.setScene(scene);
        homeArtistaStage.show();
        Stage myStage = (Stage) this.iconoError.getScene().getWindow();
        myStage.close();
    }

    public void cambiarCursorMano(MouseEvent mouseEvent) {
        noArtistaBtn.setCursor(Cursor.HAND);
    }

    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        noArtistaBtn.setCursor(Cursor.DEFAULT);
    }

    public void abrirInicioSesion(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/iniciarSesion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage inicioSesionStage = new Stage();
        inicioSesionStage.setTitle("Inicio Sesion");
        inicioSesionStage.setResizable(false);
        inicioSesionStage.setScene(scene);
        inicioSesionStage.show();
        Stage myStage = (Stage) this.contrasenaTextField.getScene().getWindow();
        myStage.close();
    }
}
