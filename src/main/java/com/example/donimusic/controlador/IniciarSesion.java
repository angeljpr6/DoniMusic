package com.example.donimusic.controlador;

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
import java.util.ResourceBundle;

/*
 * Background color: #212628
 */
public class IniciarSesion implements Initializable {
    public Label registroBtn;
    public Pane iconoError;
    public Pane errorUsuarioInexist;
    public Pane iconoError1;
    public TextField usuarioTextField;
    public PasswordField contrasenaTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/IconoError.png")));
        ImageView imageViewError = new ImageView(imageError);

        iconoError.getChildren().add(imageViewError);
        /*
        Esto se tiene que incluir por separado cuando aparezca el error en especifico

        iconoError.getChildren().add(imageViewError);

        iconoError1.getChildren().add(imageViewError);
         */

    }

    public void AbrirInterfazRegistro(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/crearCuenta.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage crearCuentaStage = new Stage();
        crearCuentaStage.setTitle("Crear Cuenta");
        crearCuentaStage.setScene(scene);
        crearCuentaStage.show();
    }

    public void cambiarCursorMano(MouseEvent mouseEvent) {
        registroBtn.setCursor(Cursor.HAND);
    }

    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        registroBtn.setCursor(Cursor.DEFAULT);
    }

    public void iniciarSesion(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage crearCuentaStage = new Stage();
        crearCuentaStage.setTitle("Home");
        crearCuentaStage.setScene(scene);
        crearCuentaStage.show();
    }
}
