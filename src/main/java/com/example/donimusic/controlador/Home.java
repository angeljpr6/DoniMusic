package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Cancion;
import com.example.donimusic.modelo.customCeldas.CustomCellFactoryCan;
import com.example.donimusic.modelo.customCeldas.CustomCellFactoryPlaylist;
import com.example.donimusic.modelo.ListaDeCanciones;
import com.example.donimusic.modelo.Usuario;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public Label inicioLabel;
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
    public Pane logo;
    public Pane anadirNuevaCancionBtn;
    public Label crearPlaylistLabel;
    public Pane crearPlaylistPane;
    public Pane anadirCancionPane;
    public TextField nombreNuevaPlaylist;
    public Label cancionesFavLabel;
    public Pane playlistPrincipalPane;
    public Pane inicioPane;
    public Pane cancionPane;
    public ScrollPane cancionesAnadidas;
    public TextField buscarNCTextField;
    public Pane controlAppPane;
    public TableView tablaBusquedaPrin;
    public ListView playlistListView;
    public ListView playlistPrinListView;
    public Button botonSiguiente;
    public Button botonAnterior;
    public Slider cancionSlider;
    public Label nombrePlaylistPrin;
    public Label autorPlaylistPrin;
    ArrayList<Label> labelSeleccionado = new ArrayList<>();
    public static Usuario usuario=new Usuario();
    public static Cancion cancionActual =null;
    public static ListaDeCanciones listaActual=null;
    private boolean reproduciendo= false;
    private TableColumn<String, String> columnaNombrePlaylist = new TableColumn<>("Nombre");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelSeleccionado.add(inicioLabel);
        crearBotonAtras();
        rellenarPanelTusPlayList();
        inicializarControlarCancion();
        inicializarBuscarBoton();
        inicializarGeneros();
        inicializarLogo();
        inicializarAnadirCancion();
        inicializarColumnaBusqPrinc();

    }
    public void inicializarAnadirCancion(){
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/mas.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(49);
        imageView.setFitHeight(49);

        anadirNuevaCancionBtn.getChildren().add(imageView);
    }
    public void inicializarColumnaBusqPrinc(){
        columnaNombrePlaylist.setResizable(true);
        columnaNombrePlaylist.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaNombrePlaylist.setMinWidth(742);
        columnaNombrePlaylist.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    }
    public void inicializarLogo(){
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/logoPequeño.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(49);
        imageView.setFitHeight(49);

        logo.getChildren().add(imageView);
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

        Image imagePlay = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/boton-de-play.png")));
        ImageView imageViewPlay = new ImageView(imagePlay);
        botonReproducir.setGraphic(imageViewPlay);
        Image imageSiguiente = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/siguiente-cancion.png")));
        ImageView imageViewSiguiente = new ImageView(imageSiguiente);
        botonSiguiente.setGraphic(imageViewSiguiente);
        Image imageAnterior = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/anterior-cancion.png")));
        ImageView imageViewAnterior = new ImageView(imageAnterior);
        botonAnterior.setGraphic(imageViewAnterior);
    }
    public void inicializarSlider(){
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

        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = usuario.obtenerListasUsuario();
        int altura=50;
        playlistListView.setCellFactory(new CustomCellFactoryPlaylist());

        for (ListaDeCanciones l : listaDeCancionesArrayList) {

            playlistListView.getItems().add(l);
            playlistListView.setPrefHeight(altura);
            altura+=57;
        }

    }
    public void rellenarPlayList(List<Cancion> canciones){

        playlistPrincipalPane.setVisible(true);
        nombrePlaylistPrin.setText(listaActual.getNombre());
        autorPlaylistPrin.setText(listaActual.getNombreCreador());

        playlistPrinListView.setCellFactory(new CustomCellFactoryCan());

        for (Cancion c : canciones) {
            playlistPrinListView.getItems().add(c);
        }
    }
    public void cambiarCursorMano(MouseEvent mouseEvent, Node node) {
        node.setCursor(Cursor.HAND);
    }
    public void irAtras(MouseEvent mouseEvent) {
        if (cancionPane.isVisible()){
            cancionPane.setVisible(false);
        }else if (playlistPrincipalPane.isVisible()){
            playlistPrincipalPane.setVisible(false);
        }else{
            buscarTextField.setText("Buscar");
            atrasCircle.requestFocus();
        }
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
        if (anadirCancionPane.isVisible()){
            if (buscarNCTextField.getText().equals("Buscar")){
                buscarNCTextField.setText("");
            }
        }else {
            if (buscarTextField.getText().equals("Buscar")) {
                buscarTextField.setText("");
            }
        }
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

    public void crearPlaylist(MouseEvent mouseEvent) {
        cambiarLabelSeleccionado(crearPlaylistLabel);
        crearPlaylistPane.setVisible(true);
        controlAppPane.setDisable(true);
        playlistPrincipalPane.setDisable(true);
        inicioPane.setDisable(true);
        cancionPane.setDisable(true);
    }

    public void crearPlaylistEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,crearPlaylistLabel);
    }

    public void crearPlaylistExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void guardarNuevaPlaylist(MouseEvent mouseEvent) {
        // TODO: 23/11/2023 añadir la playlist a la base de datos
        crearPlaylistPane.setVisible(false);
        if (playlistPrincipalPane.isVisible()){
            cambiarLabelSeleccionado(cancionesFavLabel);
        }else cambiarLabelSeleccionado(inicioLabel);
        VBox vBoxAux = new VBox();
        cancionesAnadidas.setContent(vBoxAux);
        nombreNuevaPlaylist.setText("Nombre de la Playlist");
        controlAppPane.setDisable(false);
        playlistPrincipalPane.setDisable(false);
        inicioPane.setDisable(false);
        cancionPane.setDisable(false);
    }

    public void cancelarNuevaPlaylist(MouseEvent mouseEvent) {
        crearPlaylistPane.setVisible(false);
        if (playlistPrincipalPane.isVisible()){
            cambiarLabelSeleccionado(cancionesFavLabel);
        }else cambiarLabelSeleccionado(inicioLabel);
        nombreNuevaPlaylist.setText("Nombre de la Playlist");
        controlAppPane.setDisable(false);
        playlistPrincipalPane.setDisable(true);
        inicioPane.setDisable(false);
        cancionPane.setDisable(false);
    }

    public void anadirNuevaCancion(MouseEvent mouseEvent) {
        anadirCancionPane.setVisible(true);
    }

    public void anadirNuevaCancionEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,anadirNuevaCancionBtn);
    }

    public void anadirNuevaCancionExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void guardarNuevaCancion(MouseEvent mouseEvent) {
        anadirCancionPane.setVisible(false);
    }

    public void cancelarNuevaCancion(MouseEvent mouseEvent) {
        anadirCancionPane.setVisible(false);
    }

    public void vaciarNombreNuevaPlaylist(MouseEvent mouseEvent) {
        if (nombreNuevaPlaylist.getText().equals("Nombre de la Playlist")){
            nombreNuevaPlaylist.setText("");
        }
    }

    public void abrirFavoritos(MouseEvent mouseEvent) {
        cambiarLabelSeleccionado(cancionesFavLabel);
        playlistPrincipalPane.setVisible(true);
        crearPlaylistPane.setVisible(false);
        anadirCancionPane.setVisible(false);
    }
    public void cancionesFavoritasEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,cancionesFavLabel);
    }
    public void cancionesFavoritasExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }
    public void irInicio(MouseEvent mouseEvent) {
        cambiarLabelSeleccionado(inicioLabel);
        crearPlaylistPane.setVisible(false);
        anadirCancionPane.setVisible(false);
        playlistPrincipalPane.setVisible(false);
    }
    public void inicioEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent,inicioLabel);
    }
    public void inicioExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }
    public void cambiarLabelSeleccionado(Label label){
        deselecionarLabel(labelSeleccionado.get(0));
        seleccionarLabel(label);
        labelSeleccionado.remove(0);
        labelSeleccionado.add(label);
    }

    public void pausarYReproducir(MouseEvent mouseEvent) {
        reproducirCancion();
    }
    public void inicializarCancion(){
        cancionActual.descargarCancion();
        cancionActual.reproducirCancion();
    }
    public void reproducirCancion(){
        if (cancionActual==null) {
            if (reproduciendo) {
                Image imagePlay = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/boton-de-play.png")));
                ImageView imageViewPlay = new ImageView(imagePlay);
                botonReproducir.setGraphic(imageViewPlay);
                cancionActual.pausarCancion(1);
                reproduciendo = false;
            } else {
                reproduciendo = true;
                if (cancionActual.getRuta().equals("")) {
                    inicializarCancion();
                } else cancionActual.playCancion();

                Image imagePlay = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/boton-de-pausa.png")));
                ImageView imageViewPlay = new ImageView(imagePlay);
                botonReproducir.setGraphic(imageViewPlay);
            }
        }
    }
    

    /**
     * Metodo a medias
     * @param mouseEvent
     */
    public void buscarCancion(MouseEvent mouseEvent) {

        ObservableList<String> playlists = FXCollections.observableArrayList();
        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = usuario.obtenerListasUsuario();
        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            playlists.add(l.getNombre());
        }
        tablaBusquedaPrin.getColumns().add(columnaNombrePlaylist);
        tablaBusquedaPrin.setItems(playlists);
        tablaBusquedaPrin.setVisible(true);
    }

    public void seleccionarPlayList(MouseEvent mouseEvent) {

        ListaDeCanciones listaDeCanciones = (ListaDeCanciones) playlistListView.getSelectionModel().selectedItemProperty().getValue();
        listaActual=listaDeCanciones;
        List<Cancion> canciones = ListaDeCanciones.obtenerCancionesEnLista(listaDeCanciones.getId());
        rellenarPlayList(canciones);
        System.out.println(listaDeCanciones.getId());
    }

    public void siguienteCancion(MouseEvent mouseEvent) {
        // TODO: 02/12/2023  
    }

    public void anteriorCancion(MouseEvent mouseEvent) {
        // TODO: 02/12/2023  
    }
}
