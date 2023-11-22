package com.example.donimusic.controlador;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

        Image imageError = new Image("C:\\Users\\angel\\IdeaProjects\\DoniMusic\\src\\main\\resources\\Iconos\\IconoError.png");
        ImageView imageViewError = new ImageView(imageError);

        /*
        Esto se tiene que incluir por separado cuando aparezca el error en especifico

        iconoError.getChildren().add(imageViewError);

        iconoError1.getChildren().add(imageViewError);
         */

    }

    public void AbrirInterfazRegistro(MouseEvent mouseEvent) {
        System.out.println("Pepep");
    }

    public void cambiarCursorMano(MouseEvent mouseEvent) {
        registroBtn.setCursor(Cursor.HAND);
    }

    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        registroBtn.setCursor(Cursor.DEFAULT);
    }

    public void iniciarSesion(MouseEvent mouseEvent) {
        errorUsuarioInexist.setVisible(true);
    }
}
