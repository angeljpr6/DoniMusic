package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Cancion;
import com.example.donimusic.modelo.ListaDeCanciones;
import com.example.donimusic.modelo.Usuario;
import com.example.donimusic.modelo.customCeldas.CustomCellFactoryCan;
import com.example.donimusic.modelo.customCeldas.CustomCellFactoryPlaylist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public static Usuario usuario = new Usuario();
    public static Cancion cancionActual = null;
    public static ListaDeCanciones listaActual = null;
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
    public TableView tablaAnadirCancionPlayL;
    public Button buscarBtn2;
    public Button anadirBoton;
    public TextField buscarNCTextField1;
    public Button guardarNuevaCancionPlayLBtn;
    public Button cancelarNuevaCancionPlayLBtn;
    public Pane anadirNuevaCancionPlBtn;
    public Pane anadirCancionPlayLPane;
    public Button buscarBtn3;
    public TextField buscarNCTextFieldPl;
    public TableView tablaAnadirCancionPlayLPrin;
    ArrayList<Label> labelSeleccionado = new ArrayList<>();
    public static boolean reproduciendo = false;
    private TableColumn<String, String> columnaNombrePlaylist = new TableColumn<>("Nombre");
    private TableColumn<String, String> columnaArtista = new TableColumn<>("Artista");
    private ArrayList<Cancion> cancionesAnadirNewPlaylist = new ArrayList<>();

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
        inciarColumnAnadirPlayList();

    }

    public void inicializarAnadirCancion() {
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/mas.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(49);
        imageView.setFitHeight(49);

        anadirNuevaCancionBtn.getChildren().add(imageView);

        ImageView imageView2 = new ImageView(image);

        imageView2.setFitWidth(49);
        imageView2.setFitHeight(49);
        anadirNuevaCancionPlBtn.getChildren().add(imageView2);

    }

    public void inicializarColumnaBusqPrinc() {
        columnaNombrePlaylist.setResizable(true);
        columnaNombrePlaylist.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaNombrePlaylist.setMinWidth(600);

        // Configurar CellValueFactory para obtener el nombre de la canción
        columnaNombrePlaylist.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Añadir una nueva columna para mostrar el artista de la canción
        columnaArtista.setCellValueFactory(new PropertyValueFactory<>("nombreArtista"));
        columnaArtista.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaArtista.setMinWidth(142);


        // Agregar las columnas al TableView
        tablaBusquedaPrin.getColumns().addAll(columnaNombrePlaylist, columnaArtista);
    }

    public void inciarColumnAnadirPlayList() {
        columnaNombrePlaylist.setResizable(true);
        columnaNombrePlaylist.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaNombrePlaylist.setMinWidth(1);
        columnaNombrePlaylist.setMaxWidth(100);

        // Configurar CellValueFactory para obtener el nombre de la canción
        columnaNombrePlaylist.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Añadir una nueva columna para mostrar el artista de la canción
        columnaArtista.setCellValueFactory(new PropertyValueFactory<>("nombreArtista"));
        columnaArtista.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaArtista.setMinWidth(1);
        columnaArtista.setMaxWidth(98);


        // Agregar las columnas al TableView
        tablaAnadirCancionPlayL.getColumns().addAll(columnaNombrePlaylist, columnaArtista);

    }

    public void inicializarLogo() {
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/logoPequeño.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(49);
        imageView.setFitHeight(49);

        logo.getChildren().add(imageView);
    }

    public void inicializarGeneros() {
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

    public void inicializarBuscarBoton() {
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/lupa.png")));

        ImageView imageView = new ImageView(image);
        buscarBtn.setGraphic(imageView);

        ImageView imageView2 = new ImageView(image);
        buscarBtn2.setGraphic(imageView2);

        ImageView imageView3 = new ImageView(image);
        buscarBtn3.setGraphic(imageView3);

    }

    public void inicializarControlarCancion() {

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

    public void inicializarSlider() {
        MediaPlayer mediaPlayer = Cancion.mediaPlayer;

        // Agregar un listener al tiempo actual de reproducción del MediaPlayer
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            // Obtener la duración total de la canción
            Duration totalDuration = mediaPlayer.getTotalDuration();

            // Obtener el tiempo actual de reproducción
            Duration currentDuration = mediaPlayer.getCurrentTime();

            // Calcular el progreso como un porcentaje
            double progress = currentDuration.toMillis() / totalDuration.toMillis() * 100.0;

            // Actualizar el valor del Slider con el progreso
            cancionSlider.setValue(progress);
        });

    }

    public void seleccionarLabel(Label label) {
        label.setStyle("-fx-text-fill: white;");
    }

    public void deselecionarLabel(Label label) {
        label.setStyle("-fx-text-fill: #838383;");
    }

    public void crearBotonAtras() {
        Image imageError = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/menorQue.png")));
        ImageView imageViewError = new ImageView(imageError);

        double centerX = (atrasCircle.getPrefWidth() - imageError.getWidth()) / 2;
        double centerY = (atrasCircle.getPrefHeight() - imageError.getHeight()) / 2;

        imageViewError.setLayoutX(centerX);
        imageViewError.setLayoutY(centerY);
        atrasCircle.getChildren().add(imageViewError);
    }

    public void rellenarPanelTusPlayList() {

        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = usuario.obtenerListasUsuario();
        int altura = 50;
        playlistListView.setCellFactory(new CustomCellFactoryPlaylist());
        playlistListView.getItems().clear();

        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            playlistListView.getItems().add(l);
            playlistListView.setPrefHeight(altura);
            altura += 57;
        }
    }

    public void rellenarPlayList(List<Cancion> canciones) {
        playlistPrincipalPane.setVisible(true);
        nombrePlaylistPrin.setText(listaActual.getNombre());
        autorPlaylistPrin.setText(listaActual.getNombreCreador());
        playlistPrinListView.setCellFactory(new CustomCellFactoryCan());
        playlistPrinListView.getItems().clear();

        for (Cancion c : canciones) {
            playlistPrinListView.getItems().add(c);
        }
    }

    public void actualizarCancionRep() {
        autorCancion.setText(cancionActual.getNombreArtista());
        nombreCancion.setText(cancionActual.getNombre());
    }

    public void cambiarCursorMano(MouseEvent mouseEvent, Node node) {
        node.setCursor(Cursor.HAND);
    }

    public void irAtras(MouseEvent mouseEvent) {
        if (cancionPane.isVisible()) {
            cancionPane.setVisible(false);
        } else if (playlistPrincipalPane.isVisible()) {
            playlistPrincipalPane.setVisible(false);
        } else {
            buscarTextField.setText("Buscar");
            atrasCircle.requestFocus();
        }
    }

    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        atrasCircle.setCursor(Cursor.DEFAULT);
    }

    public void anadirFavoritos(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Quitar de favoritos")) {

            anadirFavLabel.setText("Añadir Favoritos");
        } else {
            anadirFavLabel.setText("Añadido");
        }
    }

    public void anadirFavEntered(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Añadido")) {
            anadirFavLabel.setText("Quitar de favoritos");
            anadirFavLabel.setStyle("-fx-text-fill: red;");
        } else {
            anadirFavLabel.setStyle("-fx-text-fill: white;");
            cambiarCursorMano(mouseEvent, anadirFavLabel);
        }
    }

    public void anadirFavExited(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Quitar de favoritos")) {
            anadirFavLabel.setText("Añadido");
            anadirFavLabel.setStyle("-fx-text-fill: #838383;");
        } else {
            anadirFavLabel.setStyle("-fx-text-fill: #838383;");
            cambiarCursorDefault(mouseEvent);
        }
    }

    public void atrasEntered(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(1);
        atrasMensaje.setVisible(true);
        cambiarCursorMano(mouseEvent, atrasCircle);
    }

    public void atrasExited(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(0.3);
        atrasMensaje.setVisible(false);
        cambiarCursorDefault(mouseEvent);
    }

    public void introducirTextoBuscar(MouseEvent mouseEvent) {
        if (anadirCancionPane.isVisible()) {
            if (buscarNCTextField.getText().equals("Buscar")) {
                buscarNCTextField.setText("");
            }
        } else if (anadirCancionPlayLPane.isVisible()) {
            if (buscarNCTextFieldPl.getText().equals("Buscar")) {
                buscarNCTextFieldPl.setText("");
            }
        } else {
            if (buscarTextField.getText().equals("Buscar")) {
                buscarTextField.setText("");
            }
        }
    }

    public void rockEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, rockPane);
    }

    public void rockExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void fumonEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, fumonPane);
    }

    public void fumonExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void enEspanolEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, enEspanolPane);
    }

    public void enEspanolExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void rapEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, rapPane);
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
        cambiarCursorMano(mouseEvent, crearPlaylistLabel);
    }

    public void crearPlaylistExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void guardarNuevaPlaylist(MouseEvent mouseEvent) {
        if (!nombreNuevaPlaylist.getText().isBlank()) {
            ListaDeCanciones.crearLista(nombreNuevaPlaylist.getText(), usuario.getNombre());
            int id = ListaDeCanciones.obtenerIdLista(nombreNuevaPlaylist.getText(), usuario.getNombre());
            for (Cancion c : cancionesAnadirNewPlaylist) {
                ListaDeCanciones.addCancion(id, c.getId());
            }
            rellenarPanelTusPlayList();
            crearPlaylistPane.setVisible(false);
            if (playlistPrincipalPane.isVisible()) {
                cambiarLabelSeleccionado(cancionesFavLabel);
            } else cambiarLabelSeleccionado(inicioLabel);
            VBox vBoxAux = new VBox();
            cancionesAnadidas.setContent(vBoxAux);
            nombreNuevaPlaylist.setText("Nombre de la Playlist");
            controlAppPane.setDisable(false);
            playlistPrincipalPane.setDisable(false);
            inicioPane.setDisable(false);
            cancionPane.setDisable(false);
            rellenarPanelTusPlayList();
            cancionesAnadirNewPlaylist.removeAll(cancionesAnadirNewPlaylist);
            anadirCancionPane.setVisible(false);
        }
    }

    public void cancelarNuevaPlaylist(MouseEvent mouseEvent) {
        crearPlaylistPane.setVisible(false);
        anadirCancionPane.setVisible(false);

        if (playlistPrincipalPane.isVisible()) {
            cambiarLabelSeleccionado(cancionesFavLabel);
        } else cambiarLabelSeleccionado(inicioLabel);

        VBox limpiarPane = new VBox();
        cancionesAnadidas.setContent(limpiarPane);

        cancionesAnadirNewPlaylist.removeAll(cancionesAnadirNewPlaylist);

        nombreNuevaPlaylist.setText("Nombre de la Playlist");
        controlAppPane.setDisable(false);
        playlistPrincipalPane.setDisable(false);
        inicioPane.setDisable(false);
        cancionPane.setDisable(false);
    }

    public void anadirNuevaCancion(MouseEvent mouseEvent) {
        anadirCancionPane.setVisible(true);
        crearPlaylistPane.setDisable(true);
    }

    public void anadirNuevaCancionEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, anadirNuevaCancionBtn);
    }

    public void anadirNuevaCancionExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void guardarNuevaCancion(MouseEvent mouseEvent) {
        anadirCancionPane.setVisible(false);
    }

    public void cancelarNuevaCancion(MouseEvent mouseEvent) {

        anadirCancionPane.setVisible(false);
        crearPlaylistPane.setDisable(false);
    }

    public void vaciarNombreNuevaPlaylist(MouseEvent mouseEvent) {
        if (nombreNuevaPlaylist.getText().equals("Nombre de la Playlist")) {
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
        cambiarCursorMano(mouseEvent, cancionesFavLabel);
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
        cambiarCursorMano(mouseEvent, inicioLabel);
    }

    public void inicioExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void cambiarLabelSeleccionado(Label label) {
        deselecionarLabel(labelSeleccionado.get(0));
        seleccionarLabel(label);
        labelSeleccionado.remove(0);
        labelSeleccionado.add(label);
    }

    public void pausarYReproducir(MouseEvent mouseEvent) {
        reproducirCancion();
    }

    public void inicializarCancion() {
        cancionActual.descargarCancion();
        cancionActual.reproducirCancion();
    }

    public void reproducirCancion() {
        if (cancionActual != null) {
            actualizarCancionRep();
            if (reproduciendo) {
                Image imagePlay = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/boton-de-play.png")));
                ImageView imageViewPlay = new ImageView(imagePlay);
                botonReproducir.setGraphic(imageViewPlay);
                cancionActual.pausarCancion();
                reproduciendo = false;
            } else {
                reproduciendo = true;
                if (cancionActual.getRuta().equals("")) {
                    inicializarCancion();
                    inicializarSlider();
                } else cancionActual.playCancion();

                Image imagePlay = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/boton-de-pausa.png")));
                ImageView imageViewPlay = new ImageView(imagePlay);
                botonReproducir.setGraphic(imageViewPlay);
            }
        }
    }


    /**
     * Metodo a medias
     *
     * @param mouseEvent
     */
    public void buscarCancion(MouseEvent mouseEvent) {
        tablaBusquedaPrin.getColumns().clear();

        ObservableList<Cancion> listaCanciones = FXCollections.observableArrayList();
        ArrayList<Cancion> listaDeCancionesArrayList = Cancion.buscarCancion(buscarTextField.getText());
        for (Cancion l : listaDeCancionesArrayList) {
            listaCanciones.add(l);
        }
        tablaBusquedaPrin.getColumns().add(columnaNombrePlaylist);
        tablaBusquedaPrin.getColumns().add(columnaArtista);
        tablaBusquedaPrin.setItems(listaCanciones);
        tablaBusquedaPrin.setVisible(true);
    }


    public void seleccionarPlayList(MouseEvent mouseEvent) {

        ListaDeCanciones listaDeCanciones = (ListaDeCanciones) playlistListView.getSelectionModel().selectedItemProperty().getValue();
        listaActual = listaDeCanciones;
        List<Cancion> canciones = ListaDeCanciones.obtenerCancionesEnLista(listaDeCanciones.getId());
        rellenarPlayList(canciones);
        System.out.println(listaDeCanciones.getId());
    }

    public void siguienteCancion(MouseEvent mouseEvent) {
        reproducirCancion();
        cancionActual = listaActual.siguiente(cancionActual);
        reproducirCancion();
    }

    public void anteriorCancion(MouseEvent mouseEvent) {
        reproducirCancion();
        cancionActual = listaActual.atras(cancionActual);
        reproducirCancion();
    }

    public void obtenerCancionBusqueda(MouseEvent mouseEvent) {
        Cancion c1 = (Cancion) tablaBusquedaPrin.getSelectionModel().getSelectedItem();
        if (reproduciendo == true) {
            reproducirCancion();
        }
        cancionActual = c1;

        reproducirCancion();
    }

    public void cerrarBusqueda(MouseEvent mouseEvent) {
        tablaBusquedaPrin.setVisible(false);
    }

    public void buscarCancion2(MouseEvent mouseEvent) {
        tablaAnadirCancionPlayL.getColumns().clear();

        ObservableList<Cancion> listaCanciones = FXCollections.observableArrayList();
        ArrayList<Cancion> listaDeCancionesArrayList = Cancion.buscarCancion(buscarNCTextField.getText());
        for (Cancion l : listaDeCancionesArrayList) {
            listaCanciones.add(l);
        }
        tablaAnadirCancionPlayL.getColumns().add(columnaNombrePlaylist);
        tablaAnadirCancionPlayL.getColumns().add(columnaArtista);
        tablaAnadirCancionPlayL.setItems(listaCanciones);
        tablaAnadirCancionPlayL.setVisible(true);
    }

    public void seleccionarCancionAnadir(MouseEvent mouseEvent) {
        anadirBoton.setDisable(true);
        if (tablaAnadirCancionPlayL.getSelectionModel().getSelectedItem() != null) {
            anadirBoton.setDisable(false);
        }
    }

    public void anadirCancionNewPlaylist(ActionEvent actionEvent) {
        if (tablaAnadirCancionPlayL.getSelectionModel().getSelectedItem() != null) {
            Cancion c1 = (Cancion) tablaAnadirCancionPlayL.getSelectionModel().getSelectedItem();

            boolean existeCancion = false;
            for (Cancion c :
                    cancionesAnadirNewPlaylist) {
                if (c.getId() == c1.getId()) {
                    existeCancion = true;
                }
            }
            if (!existeCancion) {
                cancionesAnadirNewPlaylist.add(c1);
                VBox vBoxCanciones = new VBox();
                crearPlaylistPane.setDisable(false);

                for (Cancion c : cancionesAnadirNewPlaylist) {
                    HBox hboxDatosCancion = new HBox();
                    Label nombreCancion = new Label("  " + c.getNombre());
                    nombreCancion.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-min-height: 50px; -fx-min-width: 300px;");

                    Label nombreArtista = new Label(c.getNombreArtista() + " ");
                    nombreArtista.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-min-height: 50px; -fx-min-width: 250px;");
                    HBox.setHgrow(nombreArtista, Priority.ALWAYS);

                    hboxDatosCancion.getChildren().addAll(nombreCancion, nombreArtista);
                    vBoxCanciones.getChildren().add(hboxDatosCancion);
                }

                cancionesAnadidas.setContent(vBoxCanciones);
            }
        }
    }

    public void anadirNuevaCancionPlaylist(MouseEvent mouseEvent) {
        anadirCancionPlayLPane.setVisible(true);
        playlistPrincipalPane.setDisable(true);
        controlAppPane.setDisable(true);
    }

    public void anadirNuevaCancionPlExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    public void anadirNuevaCancionPlEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, anadirNuevaCancionPlBtn);
    }

    public void cancelarNuevaCancionPlayL(MouseEvent mouseEvent) {
        anadirCancionPlayLPane.setVisible(false);
        playlistPrincipalPane.setDisable(false);
        controlAppPane.setDisable(false);
    }

    public void guardarNuevaCancionPlayL(MouseEvent mouseEvent) {
        if (tablaAnadirCancionPlayLPrin.getSelectionModel().getSelectedItem() != null) {
            Cancion c1 = (Cancion) tablaAnadirCancionPlayLPrin.getSelectionModel().getSelectedItem();
            if (!ListaDeCanciones.encontrarCancion(c1.getId(), listaActual.getId())) {// TODO: 04/12/2023 cambiar el true por una comprobacion de si no existe la cancion en la lista
                ListaDeCanciones.addCancion(listaActual.getId(), c1.getId());
                List<Cancion> canciones = ListaDeCanciones.obtenerCancionesEnLista(listaActual.getId());
                rellenarPlayList(canciones);
            }
            cancelarNuevaCancionPlayL(mouseEvent);
        }

    }

    public void buscarCancion3(MouseEvent mouseEvent) {
        tablaAnadirCancionPlayLPrin.getColumns().clear();

        ObservableList<Cancion> listaCanciones = FXCollections.observableArrayList();
        ArrayList<Cancion> listaDeCancionesArrayList = Cancion.buscarCancion(buscarNCTextFieldPl.getText());
        for (Cancion l : listaDeCancionesArrayList) {
            listaCanciones.add(l);
        }
        tablaAnadirCancionPlayLPrin.getColumns().add(columnaNombrePlaylist);
        tablaAnadirCancionPlayLPrin.getColumns().add(columnaArtista);
        tablaAnadirCancionPlayLPrin.setItems(listaCanciones);
        tablaAnadirCancionPlayLPrin.setVisible(true);
    }
}
