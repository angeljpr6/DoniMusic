package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Conexiones.Conexion;
import com.example.donimusic.modelo.Conexiones.ConexionSqlite;
import com.example.donimusic.modelo.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
    private static final Connection a = ConexionSqlite.con;
    private static boolean logoCargado = false;
    private static Connection c = Conexion.con;
    public Label registroBtn;
    public Pane iconoError;
    public Pane errorUsuarioInexist;
    public Pane iconoError1;
    public TextField usuarioTextField;
    public PasswordField contrasenaTextField;
    @FXML
    public AnchorPane inicioLogo;
    public Pane imagenLogo;
    public Label artistaBtn;
    public CheckBox recordarCuenta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/IconoError.png")));
        ImageView imageViewError = new ImageView(imageError);

        iconoError.getChildren().add(imageViewError);
        if (!logoCargado) {
            inicioLogo.setVisible(true);
            verLogo();
            logoCargado = true;
        }

        if (hayDatosEnTabla()) {
            rellenarDatos();
            recordarCuenta.setSelected(true);
        }

    }

    /**
     * Método para mostrar 2 segundos el logo al iniciar
     */
    public void verLogo() {
        Image logoImg = new Image(String.valueOf(CrearCuenta.class.getResource("/Iconos/logoPequeño.PNG")));
        ImageView logo = new ImageView(logoImg);
        imagenLogo.getChildren().add(logo);
        inicioLogo.setVisible(true);
        Timeline timeline = new Timeline();

        //se muestra el panel del logo 2 segundos
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(2), event -> inicioLogo.setVisible(false))
        );
        timeline.play();
    }

    /**
     * Botón para abrir la ventana registro
     * @param mouseEvent
     * @throws IOException
     */
    public void AbrirInterfazRegistro(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/crearCuenta.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage crearCuentaStage = new Stage();
        crearCuentaStage.setTitle("Crear Cuenta");
        crearCuentaStage.setResizable(false);
        crearCuentaStage.setScene(scene);
        crearCuentaStage.show();
        Stage myStage = (Stage) this.inicioLogo.getScene().getWindow();
        myStage.close();
    }

    /**
     * Cambia el cursor a una mano
     * @param mouseEvent
     */
    public void cambiarCursorMano(MouseEvent mouseEvent) {
        registroBtn.setCursor(Cursor.HAND);
    }

    /**
     * Cambia el cursor al por defecto
     * @param mouseEvent
     */
    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        registroBtn.setCursor(Cursor.DEFAULT);
    }


    /**
     * Método para inicir sesión
     * @param mouseEvent
     * @throws IOException
     */
    public void iniciarSesion(MouseEvent mouseEvent) throws IOException {
        PreparedStatement stm;
        String textoNombre = usuarioTextField.getText();
        String textoCont = contrasenaTextField.getText();
        try {
            stm = c.prepareStatement("SELECT * FROM usuario");
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                String nombre = result.getString("nombreUsuario");
                String password = result.getString("contraseña");
                //Comprueba que los datos introducidos esten en la base de datos
                if (nombre.equals(textoNombre) && password.equals(textoCont)) {
                    Home.usuario = new Usuario(usuarioTextField.getText(), contrasenaTextField.getText());
                    if (recordarCuenta.isSelected()) {
                        comprobarYInsertarUsuario(usuarioTextField.getText(), contrasenaTextField.getText());
                    } else {
                        if (hayDatosEnTabla()) {
                            borrarUsuario();
                        }
                    }
                    //Abre el Home
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/home.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage homeStage = new Stage();
                    homeStage.setTitle("Home");
                    homeStage.setResizable(false);
                    homeStage.setScene(scene);
                    homeStage.show();
                    Stage myStage = (Stage) this.inicioLogo.getScene().getWindow();

                    myStage.close();
                } else errorUsuarioInexist.setVisible(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Botón para abrir la ventana de inicio de artista
     * @param mouseEvent
     * @throws IOException
     */
    public void abrirInicioArtista(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/inicioArtista.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage inicioArtistaStage = new Stage();
        inicioArtistaStage.setTitle("Inicio Artista");
        inicioArtistaStage.setResizable(false);
        inicioArtistaStage.setScene(scene);
        inicioArtistaStage.show();
        Stage myStage = (Stage) this.inicioLogo.getScene().getWindow();
        myStage.close();
    }

    public void cambiarCursorManoArtista(MouseEvent mouseEvent) {
        artistaBtn.setCursor(Cursor.HAND);
    }

    /**
     * Método para insertar un nuevo usuario en la base de datos
     * @param nombreUsuario
     * @param password
     */
    public void insertarUsuario(String nombreUsuario, String password) {
        try {
            PreparedStatement stm = a.prepareStatement("INSERT INTO usuario (nombreUsuario, contraseña) VALUES (?, ?)");
            stm.setString(1, nombreUsuario);
            stm.setString(2, password);
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para eliminar usuario de la base de datos local
     */
    public void borrarUsuario() {
        try {
            PreparedStatement stm = a.prepareStatement("DELETE FROM usuario");

            stm.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void comprobarYInsertarUsuario(String nombreUsuario, String password) {
        try {
            System.out.println("usuario ");
            rellenarDatos();

            hayDatosEnTabla();
            if (!hayDatosEnTabla()) {
                insertarUsuario(nombreUsuario, password);
            } else {
                borrarUsuario();
                insertarUsuario(nombreUsuario, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al comprobar usuario. Realizar acción Z.");
        }
    }

    private boolean hayDatosEnTabla() {
        String consulta = "SELECT COUNT(*) FROM usuario";
        try (PreparedStatement stm = a.prepareStatement(consulta);
             ResultSet result = stm.executeQuery()) {
            result.next();

            int count = result.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void rellenarDatos() {
        String consulta = "SELECT * FROM usuario";
        try (PreparedStatement stm = a.prepareStatement(consulta);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {

                String usuario = result.getString(1);
                String contrasena = result.getString(2);

                usuarioTextField.setText(usuario);
                contrasenaTextField.setText(contrasena);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
