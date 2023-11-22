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
    public PasswordField contraseñaRegis;
    @FXML
    public PasswordField confirmContraseñaRegis;
    @FXML
    public Pane errorUsuarioExist;
    @FXML
    public Pane errorContraseña;
    @FXML
    public Pane iconoError1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/IconoError.png")));
        ImageView imageViewError = new ImageView(imageError);

        iconoError1.getChildren().add(imageViewError);

    }

    public void registrarse(ActionEvent actionEvent) {
        errorContraseña.setVisible(false);
        if(!contraseñaRegis.getText().equals(confirmContraseñaRegis.getText())){
            errorContraseña.setVisible(true);
        }
    }
}
