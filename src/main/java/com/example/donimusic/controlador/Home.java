package com.example.donimusic.controlador;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    public Label nombrePlaylistHist;
    public Label autorPlaylistHist;
    public Label anadirFavLabel;
    public Button buscarBtn;
    public TextField buscarTextField;
    public Pane rockImagen;
    public Pane fumonImagen;
    public Pane rockPane;
    public Pane fumonPane;
    public Pane enEspanolPane;
    public Pane rapPane;
    public Pane enEspanolImagen;
    public Pane rapImagen;
    private VBox vboxTusPlaylist = new VBox();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        crearBotonAtras();
        rellenarPanelTusPlayList();
        inicializarControlarCancion();
        inicializarBuscarBoton();
        inicializarGeneros();

    }
    public void inicializarGeneros(){
        //Rock
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/imagenes/imagenRock.jpeg")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(142);
        imageView.setFitHeight(139);

        rockImagen.getChildren().add(imageView);

        //Fumon
        Image image2 = new Image(String.valueOf(IniciarSesion.class.getResource("/imagenes/fumones.jpg")));
        ImageView imageView2 = new ImageView(image2);

        imageView2.setFitWidth(142);
        imageView2.setFitHeight(139);

        fumonImagen.getChildren().add(imageView2);

        //En español
        Image image3 = new Image(String.valueOf(IniciarSesion.class.getResource("/imagenes/nano.jpeg")));
        ImageView imageView3 = new ImageView(image3);

        imageView3.setFitWidth(142);
        imageView3.setFitHeight(139);

        enEspanolImagen.getChildren().add(imageView3);

        //Rap
        Image image4 = new Image(String.valueOf(IniciarSesion.class.getResource("/imagenes/rap.png")));
        ImageView imageView4 = new ImageView(image4);

        imageView4.setFitWidth(142);
        imageView4.setFitHeight(139);

        rapImagen.getChildren().add(imageView4);

    }
    public void inicializarBuscarBoton(){
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/lupa.png")));
        ImageView imageView = new ImageView(image);
        buscarBtn.setGraphic(imageView);
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
    public void cambiarCursorMano(MouseEvent mouseEvent, Node node) {
        node.setCursor(Cursor.HAND);
    }
    public void irAtras(MouseEvent mouseEvent) {
    }
    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        atrasCircle.setCursor(Cursor.DEFAULT);
    }

    public void anadirFavoritos(MouseEvent mouseEvent) {
        anadirFavLabel.setText("Añadido");
    }

    public void anadirFavEntered(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Añadido")){
            anadirFavLabel.setText("Quitar de favoritos");
            anadirFavLabel.setStyle("-fx-text-fill: red;");
        }else {
            anadirFavLabel.setStyle("-fx-text-fill: white;");
            cambiarCursorMano(mouseEvent, anadirFavLabel);
        }
    }
    public void anadirFavExited(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Quitar de favoritos")){
            anadirFavLabel.setText("Añadido");
            anadirFavLabel.setStyle("-fx-text-fill: #838383;");
        }else {
            anadirFavLabel.setStyle("-fx-text-fill: #838383;");
            cambiarCursorDefault(mouseEvent);
        }
    }

    public void atrasEntered(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(1);
        atrasMensaje.setVisible(true);
        cambiarCursorMano(mouseEvent,atrasCircle);
    }
    public void atrasExited(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(0.3);
        atrasMensaje.setVisible(false);
        cambiarCursorDefault(mouseEvent);
    }

    public void introducirTextoBuscar(MouseEvent mouseEvent) {
        buscarTextField.setText("");
    }

    public void rockEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,rockPane);
    }

    public void rockExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void fumonEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,fumonPane);
    }

    public void fumonExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void enEspanolEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,enEspanolPane);
    }

    public void enEspanolExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void rapEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,rapPane);
    }

    public void rapExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }
}
