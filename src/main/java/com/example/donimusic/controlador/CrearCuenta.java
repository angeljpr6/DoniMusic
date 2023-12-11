package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Conexiones.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class CrearCuenta implements Initializable {
    private static Connection c = Conexion.con;
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
    @FXML
    public Label registroExitoso;
    @FXML
    public Label salirRegistro;
    @FXML
    public AnchorPane registroInterfaz;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * Boton de registrarse
     * @param actionEvent
     * @throws IOException
     */
    public void registrarse(ActionEvent actionEvent) throws IOException {
        errorContrasena.setVisible(false);
        errorUsuarioExist.setVisible(false);
        //Se comprueba que los credenciales no esten en blanco
        if (!contrasenaRegis.getText().isBlank() && !usuarioRegis.getText().isBlank()) {
            if (contrasenaRegis.getText().equals(confirmContrasenaRegis.getText())) {
                Home.usuario.setNombre(usuarioRegis.getText());
                Home.usuario.setPassword(contrasenaRegis.getText());
                //se abre el home si el usuario se registra correctamente
                if (Home.usuario.crearUsuario()) {
                    abrirHome();
                } else errorUsuarioExist.setVisible(true);
            } else {
                errorContrasena.setVisible(true);
            }
        }
    }

    /**
     * Método para abrir home
     * @throws IOException
     */
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

    /**
     * Botón para volver a iniciar sesion
     * @param mouseEvent
     * @throws IOException
     */
    public void AbrirInterfazInicio(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/iniciarSesion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage crearCuentaStage = new Stage();
        crearCuentaStage.setTitle("Crear Cuenta");
        crearCuentaStage.setResizable(false);
        crearCuentaStage.setScene(scene);
        crearCuentaStage.show();
        Stage myStage = (Stage) this.registroInterfaz.getScene().getWindow();
        myStage.close();


    }

    /**
     * Método para cambiar el tipo de cursor
     * @param mouseEvent
     */
    public void cambiarCursorMano(MouseEvent mouseEvent) {
        salirRegistro.setCursor(Cursor.HAND);

    }


}
