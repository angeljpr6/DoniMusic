package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Conexion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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


    public void registrarse(ActionEvent actionEvent) {
        errorContrasena.setVisible(false);
        errorUsuarioExist.setVisible(false);
        registroExitoso.setVisible(false);
        PreparedStatement stm;
        boolean comprobacion=false;
        try {
            stm = c.prepareStatement("SELECT * FROM usuario");
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                String nombre=result.getString("nombreUsuario");
                if(nombre.equals(usuarioRegis.getText())){
                    comprobacion=true;
                }
            }

            if(!contrasenaRegis.getText().equals(confirmContrasenaRegis.getText())){
                errorContrasena.setVisible(true);
            }else {
                if(comprobacion!=true){
                    stm=c.prepareStatement("insert into usuario values(?,?);");
                    stm.setString(1,usuarioRegis.getText());
                    stm.setString(2, contrasenaRegis.getText());
                    stm.execute();
                    registroExitoso.setVisible(true);
                }else {
                    errorUsuarioExist.setVisible(true);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
