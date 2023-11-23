package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Conexion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    @FXML
    public AnchorPane inicioLogo;
    private static Connection c= Conexion.con;
    public Pane imagenLogo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/IconoError.png")));
        ImageView imageViewError = new ImageView(imageError);

        iconoError.getChildren().add(imageViewError);
        verLogo();
        /*
        Esto se tiene que incluir por separado cuando aparezca el error en especifico

        iconoError.getChildren().add(imageViewError);

        iconoError1.getChildren().add(imageViewError);
         */

    }

    public void verLogo(){
        Image logoImg = new Image(String.valueOf(CrearCuenta.class.getResource("/Iconos/logoPequeño.PNG")));
        ImageView logo = new ImageView(logoImg);
        imagenLogo.getChildren().add(logo);
        inicioLogo.setVisible(true);
        Timeline timeline = new Timeline();

        // Añadir un KeyFrame para ocultar el inicioLogo después de 5 segundos
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(2), event -> inicioLogo.setVisible(false))
        );

        // Iniciar la animación
        timeline.play();
    }

    public void AbrirInterfazRegistro(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/crearCuenta.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage crearCuentaStage = new Stage();
        crearCuentaStage.setTitle("Crear Cuenta");
        crearCuentaStage.setResizable(false);
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
        /*PreparedStatement stm;

        try {
            stm = c.prepareStatement("SELECT * FROM usuario");
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                String nombre=result.getString("nombreUsuario");
                String password=result.getString("contraseña");

                if(nombre.equals(usuarioTextField.getText()) && password.equals(contrasenaTextField)){
                    //aqui va lo que ta
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage homeStage = new Stage();
        homeStage.setTitle("Home");
        homeStage.setResizable(false);
        homeStage.setScene(scene);
        homeStage.show();
    }
}
