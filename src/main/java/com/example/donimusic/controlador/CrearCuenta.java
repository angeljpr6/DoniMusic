package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Conexion;
import com.example.donimusic.modelo.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class CrearCuenta implements Initializable {
    @FXML
    public TextField usuarioRegis;
    @FXML
    public PasswordField contrasenaRegis;
    @FXML
    public PasswordField confirmContrasenaRegis;
    @FXML
    public Pane errorUsuarioExist;
    @FXML
    public Pane errorContrasena;
    @FXML
    public Pane iconoError1;
    private static Connection c= Conexion.con;
    @FXML
    public Label registroExitoso;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }


    public void registrarse(ActionEvent actionEvent) throws IOException {
        errorContrasena.setVisible(false);
        errorUsuarioExist.setVisible(false);
        if (!contrasenaRegis.getText().isBlank() && !usuarioRegis.getText().isBlank()) {
            if (contrasenaRegis.getText().equals(confirmContrasenaRegis.getText())) {
                Home.usuario.setNombre(usuarioRegis.getText());
                Home.usuario.setPassword(contrasenaRegis.getText());
                if (Home.usuario.crearUsuario()) {
                    abrirHome();
                } else errorUsuarioExist.setVisible(true);
            } else {
                errorContrasena.setVisible(true);
            }
        }
    }
    public void abrirHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage homeStage = new Stage();
        homeStage.setTitle("Home");
        homeStage.setResizable(false);
        homeStage.setScene(scene);
        homeStage.show();
        Stage myStage = (Stage) this.contrasenaRegis.getScene().getWindow();
        myStage.close();
    }
}
