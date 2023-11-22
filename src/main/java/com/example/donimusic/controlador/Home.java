package com.example.donimusic.controlador;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public ScrollPane panelPlaylists;
    public Label tusPlaylistLabel;
    public Pane atrasCircle;
    public Label atrasMensaje;
    public Label autorCancion;
    public Button botonReproducir;
    public Label nombreCancion;
    private VBox vboxTusPlaylist = new VBox();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        crearBotonAtras();
        rellenarPanelTusPlayList();
        inicializarControlarCancion();

    }
    public void inicializarControlarCancion(){

        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/boton-de-pausa-de-video.png")));
        ImageView imageView = new ImageView(image);
        botonReproducir.setGraphic(imageView);
    }
    public void seleccionarLabel(Label label){
        label.setStyle("-fx-text-fill: white;");
    }
    public void deselecionarLabel(Label label){
        label.setStyle("-fx-text-fill: #838383;");
    }
    public void crearBotonAtras(){
        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/menorQue.png")));
        ImageView imageViewError = new ImageView(imageError);

        double centerX = (atrasCircle.getPrefWidth() - imageError.getWidth()) / 2;
        double centerY = (atrasCircle.getPrefHeight() - imageError.getHeight()) / 2;

        imageViewError.setLayoutX(centerX);
        imageViewError.setLayoutY(centerY);
        atrasCircle.getChildren().add(imageViewError);
    }
    public void rellenarPanelTusPlayList(){

        double scrollPaneWidth = panelPlaylists.getPrefWidth()-15;
        panelPlaylists.setStyle("-fx-background-color: #212628;");

        for (int i = 1; i <= 100; i++) {
            VBox cancionBox = new VBox();
            Label label =new Label("Elemento " + i);
            label.setFont(tusPlaylistLabel.getFont());
            label.setStyle("-fx-text-fill: #838383; -fx-min-height: 50px;");
            vboxTusPlaylist.getChildren().add(label);
            vboxTusPlaylist.getChildren().add(cancionBox);

        }
        vboxTusPlaylist.setStyle("-fx-background-color:  #212628; -fx-min-width: "+scrollPaneWidth+"px;");

        /*String scrollbarStyle = "-fx-background-color: #FF0000;"; // Color de fondo de las barras
        scrollbarStyle += "-fx-background: #00FF00;"; // Color de las barras
        scrollbarStyle += "-fx-border-color: #0000FF;"; // Color del borde de las barras
        panelPlaylists.lookup(".scroll-bar:vertical").setStyle(scrollbarStyle);*/

        panelPlaylists.setContent(vboxTusPlaylist);
    }
    public void cambiarCursorMano(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(1);
        atrasMensaje.setVisible(true);
        atrasCircle.setCursor(Cursor.HAND);
    }
    public void irAtras(MouseEvent mouseEvent) {
    }
    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(0.3);
        atrasMensaje.setVisible(false);
        atrasCircle.setCursor(Cursor.DEFAULT);
    }
}
