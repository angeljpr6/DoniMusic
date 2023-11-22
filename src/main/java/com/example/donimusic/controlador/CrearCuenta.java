package com.example.donimusic.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/IconoError.png")));
        ImageView imageViewError = new ImageView(imageError);

        iconoError1.getChildren().add(imageViewError);

    }

    public void registrarse(ActionEvent actionEvent) {
        errorContrasena.setVisible(false);
        if(!contrasenaRegis.getText().equals(confirmContrasenaRegis.getText())){
            errorContrasena.setVisible(true);
        }
    }
}
