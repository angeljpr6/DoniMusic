package com.example.donimusic.controlador;

import com.example.donimusic.modelo.Cancion;
import com.example.donimusic.modelo.ListaDeCanciones;
import com.example.donimusic.modelo.Usuario;
import com.example.donimusic.modelo.customCeldas.CustomCellFactoryCan;
import com.example.donimusic.modelo.customCeldas.CustomCellFactoryPlaylist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.donimusic.modelo.ListaDeCanciones.obtenerCancionesEnLista;

public class Home implements Initializable {
    public static Usuario usuario = new Usuario();
    public static Cancion cancionActual = null;
    public static ListaDeCanciones listaActual = null;
    public static boolean reproduciendo = false;
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
    public Label nomUsu;
    public Label nomUsuAjust;
    public Pane ajustesPane;
    public TextField antContra;
    public TextField nuevContra;
    public TextField nuevNombre;
    public Button acepUsuBtn;
    public Button acepContBtn;
    public Button ajustesBtn;
    public Button editarNomListBtn;
    public TextField nuevoNomList;
    public Pane nuevoNomListPane;
    public Button elimListbtn;
    ArrayList<Label> labelSeleccionado = new ArrayList<>();
    private TableColumn<String, String> columnaNombrePlaylist = new TableColumn<>("Nombre");
    private TableColumn<String, String> columnaArtista = new TableColumn<>("Artista");
    private ArrayList<Cancion> cancionesAnadirNewPlaylist = new ArrayList<>();
    private TableColumn<String, String> columnaNombrePlaylist1 = new TableColumn<>("Nombre");
    private TableColumn<String, String> columnaArtista1 = new TableColumn<>("Artista");
    private boolean continuar =true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomUsuAjust.setText(usuario.getNombre());
        nomUsu.setText(usuario.getNombre());
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
        inicializarIconoAjust();
        inicializarIconoLapiz();
        inicializarIconoPapelera();

    }

    /**
     * Se le pone al lapiz su icono correspondiente
     */
    public void inicializarIconoLapiz(){
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/lapiz.png")));
        ImageView imageView = new ImageView(image);

        editarNomListBtn.setGraphic(imageView);
    }

    /**
     * Se le pone al lapiz su icono correspondiente
     */
    public void inicializarIconoPapelera(){
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/papelera-de-reciclaje.png")));
        ImageView imageView = new ImageView(image);

        elimListbtn.setGraphic(imageView);
    }

    /**
     * Se le pone al boton de ajustes su imagen correspondiente
     */
    public void inicializarIconoAjust(){
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/configuraciones.png")));
        ImageView imageView = new ImageView(image);

        ajustesBtn.setGraphic(imageView);
    }

    /**
     * Se le pone al panel de añadir cancion en una nueva playlist su imagen correspondiente
     */
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
        columnaNombrePlaylist1.setResizable(true);
        columnaNombrePlaylist1.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaNombrePlaylist1.setMinWidth(600);

        // Configurar CellValueFactory para obtener el nombre de la canción
        columnaNombrePlaylist1.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Añadir una nueva columna para mostrar el artista de la canción
        columnaArtista1.setCellValueFactory(new PropertyValueFactory<>("nombreArtista"));
        columnaArtista1.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaArtista1.setMinWidth(142);


        // Agregar las columnas al TableView
        tablaBusquedaPrin.getColumns().addAll(columnaNombrePlaylist1, columnaArtista1);
    }

    public void inciarColumnAnadirPlayList() {
        columnaNombrePlaylist.setResizable(true);
        columnaNombrePlaylist.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaNombrePlaylist.setMinWidth(100);
        columnaNombrePlaylist.setMaxWidth(200);

        // Configurar CellValueFactory para obtener el nombre de la canción
        columnaNombrePlaylist.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Añadir una nueva columna para mostrar el artista de la canción
        columnaArtista.setCellValueFactory(new PropertyValueFactory<>("nombreArtista"));
        columnaArtista.setStyle("-fx-background-color: #383c41; -fx-text-fill: white;");
        columnaArtista.setMinWidth(100);
        columnaArtista.setMaxWidth(100);


        // Agregar las columnas al TableView
        tablaAnadirCancionPlayL.getColumns().addAll(columnaNombrePlaylist, columnaArtista);

    }

    /**
     * Se le pone al panel del logo su imagen correspondiente
     */
    public void inicializarLogo() {
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/logoPequeño.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(49);
        imageView.setFitHeight(49);

        logo.getChildren().add(imageView);
    }

    /**
     * A cada uno de los generos predeterminados se le añade su imagen
     */
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

    /**
     * Se le pone al boton de buscar principal su imagen correspondiente
     */
    public void inicializarBuscarBoton() {
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/lupa.png")));

        ImageView imageView = new ImageView(image);
        buscarBtn.setGraphic(imageView);

        ImageView imageView2 = new ImageView(image);
        buscarBtn2.setGraphic(imageView2);

        ImageView imageView3 = new ImageView(image);
        buscarBtn3.setGraphic(imageView3);

    }

    /**
     * Se cargan todos los iconos correpondientes de cada boton del panel de control de las canciones
     */
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

    /**
     * Se vincula el slider con el mediaPlayer de cancion para que este avance con la cancion
     */
    public void inicializarSlider() {
        MediaPlayer mediaPlayer = Cancion.mediaPlayer;

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {

            if (continuar) {

                Duration totalDuration = mediaPlayer.getTotalDuration();
                Duration currentDuration = mediaPlayer.getCurrentTime();

                double progress = currentDuration.toMillis() / totalDuration.toMillis() * 100.0;
                cancionSlider.setValue(progress);
            }
        });
    }

    /**
     * Cambia el color de un label a blanco
     * @param label
     */
    public void seleccionarLabel(Label label) {
        label.setStyle("-fx-text-fill: white;");
    }
    /**
     * Cambia el color de un label a #838383
     * @param label
     */
    public void deselecionarLabel(Label label) {
        label.setStyle("-fx-text-fill: #838383;");
    }

    /**
     * Se añade al circulo de atras una flecha de menor que y se pone en el centro del circulo
     */
    public void crearBotonAtras() {
        Image image = new Image(String.valueOf(IniciarSesion.class.getResource("/Iconos/menorQue.png")));
        ImageView imageView = new ImageView(image);

        double centerX = (atrasCircle.getPrefWidth() - image.getWidth()) / 2;
        double centerY = (atrasCircle.getPrefHeight() - image.getHeight()) / 2;

        imageView.setLayoutX(centerX);
        imageView.setLayoutY(centerY);
        atrasCircle.getChildren().add(imageView);
    }

    /**
     * Se le añade al panel que contiene las playlist del usuario cada una de sus playlist
     */
    public void rellenarPanelTusPlayList() {

        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = usuario.obtenerListasUsuario();

        /* Aqui establecemos una altura de 50 y luego la incrementamos para evitar
         * que se vean huecos en blanco
         */
        int altura = 50;

        /* Le añadimos a la lista una clase CellFactory que establecera los estilos de la lista
         * y como se comportara el al seleccionar un item de esta
         */
        playlistListView.setCellFactory(new CustomCellFactoryPlaylist());
        playlistListView.getItems().clear();

        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            playlistListView.getItems().add(l);
            playlistListView.setPrefHeight(altura);

            // Se incrementa la altura
            altura += 57;
        }
    }

    /**
     * Rellena el panel principal de las playlist con las canciones de la lista seleccionada
     * @param canciones
     */
    public void rellenarPlayList(List<Cancion> canciones) {
        anadirNuevaCancionPlBtn.setDisable(false);
        anadirNuevaCancionPlBtn.setVisible(true);

        elimListbtn.setVisible(true);
        elimListbtn.setDisable(false);

        playlistPrincipalPane.setVisible(true);
        nombrePlaylistPrin.setText(listaActual.getNombre());
        autorPlaylistPrin.setText(listaActual.getNombreCreador());

        /* Le añadimos a la lista una clase CellFactory que establecera los estilos de la lista
         * y como se comportara el al seleccionar un item de esta
         */
        playlistPrinListView.setCellFactory(new CustomCellFactoryCan(this));
        playlistPrinListView.getItems().clear();

        for (Cancion c : canciones) {
            playlistPrinListView.getItems().add(c);
        }
    }

    /**
     * Actualiza los datos del controloador de canciones para que muestre el nombre y el autor de la
     * cancion actual
     */
    public void actualizarCancionRep() {
        autorCancion.setText(cancionActual.getNombreArtista());
        nombreCancion.setText(cancionActual.getNombre());
    }

    /**
     * Cambia el curso a una mano
     * @param mouseEvent
     * @param node le introducimos un nodo para poder usar este metodo en otros botones y paneles
     */
    public void cambiarCursorMano(MouseEvent mouseEvent, Node node) {
        node.setCursor(Cursor.HAND);
    }

    /**
     * Establece la condiciones que se deben cumplir para que el boton atras funcione y a donde debe llevar
     * @param mouseEvent
     */
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

    /**
     * Cambia el cursor al cursor por defecto
     * @param mouseEvent
     */
    public void cambiarCursorDefault(MouseEvent mouseEvent) {
        atrasCircle.setCursor(Cursor.DEFAULT);
    }

    /**
     * Cuando seleccionas el texto de añadir favoritos dependiendo de si esta o no añadida cambiara por un texto u otro
     * @param mouseEvent
     */
    public void anadirFavoritos(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Quitar de favoritos")) {

            anadirFavLabel.setText("Añadir Favoritos");
        } else {
            anadirFavLabel.setText("Añadido");
        }
    }

    /**
     * Dependiendo de que se muestre en añadir favoritos al poner el raton encima se mostrara un texto u otro
     * @param mouseEvent
     */
    public void anadirFavEntered(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Añadido")) {
            anadirFavLabel.setText("Quitar de favoritos");
            anadirFavLabel.setStyle("-fx-text-fill: red;");
        } else {
            anadirFavLabel.setStyle("-fx-text-fill: white;");
            cambiarCursorMano(mouseEvent, anadirFavLabel);
        }
    }

    /**
     * Dependiendo de que se muestre en añadir favoritos al quitar el raton de encima se mostrara un texto u otro
     * @param mouseEvent
     */
    public void anadirFavExited(MouseEvent mouseEvent) {
        if (anadirFavLabel.getText().equals("Quitar de favoritos")) {
            anadirFavLabel.setText("Añadido");
            anadirFavLabel.setStyle("-fx-text-fill: #838383;");
        } else {
            anadirFavLabel.setStyle("-fx-text-fill: #838383;");
            cambiarCursorDefault(mouseEvent);
        }
    }

    /**
     * Cuando se pone el raton encima del boton atras este se vuelve mas claro y muestra el texto "Atrás"
     * @param mouseEvent
     */
    public void atrasEntered(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(1);
        atrasMensaje.setVisible(true);
        cambiarCursorMano(mouseEvent, atrasCircle);
    }

    /**
     * cuando se quita el raton de encima el boton atras vuelve a su estado original
     * @param mouseEvent
     */
    public void atrasExited(MouseEvent mouseEvent) {
        atrasCircle.setOpacity(0.3);
        atrasMensaje.setVisible(false);
        cambiarCursorDefault(mouseEvent);
    }

    /**
     * Se comprueba en que panel estamos situados para que al seleccionar una barra de busqueda se le quite el texto de Buscar
     * @param mouseEvent
     */
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

    /**
     * Cuando se seleciona el panel de rock se abre la playlist predeterminada de rock
     * @param mouseEvent
     */
    public void abrirRock(MouseEvent mouseEvent) {
        Usuario doniol=new Usuario("doniol","1234");
        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = doniol.obtenerListasUsuario();
        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            if (l.getId() == 1) {
                listaActual = l;
            }
        }
        List<Cancion> canciones = obtenerCancionesEnLista(1);
        rellenarPlayList(canciones);
        anadirNuevaCancionPlBtn.setDisable(true);
        anadirNuevaCancionPlBtn.setVisible(false);

        elimListbtn.setVisible(false);
        elimListbtn.setDisable(true);
    }

    public void fumonEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, fumonPane);
    }

    public void fumonExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    /**
     * Cuando se seleciona el panel de fumon se abre la playlist predeterminada de fumon
     * @param mouseEvent
     */
    public void abrirFumon(MouseEvent mouseEvent) {
        Usuario doniol=new Usuario("doniol","1234");
        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = doniol.obtenerListasUsuario();
        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            if (l.getId() == 1) {
                listaActual = l;
            }
        }
        List<Cancion> canciones = obtenerCancionesEnLista(1);
        rellenarPlayList(canciones);

        elimListbtn.setVisible(false);
        elimListbtn.setDisable(true);
        anadirNuevaCancionPlBtn.setDisable(true);
        anadirNuevaCancionPlBtn.setVisible(false);
    }

    public void enEspanolEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, enEspanolPane);
    }

    public void enEspanolExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    /**
     * Cuando se seleciona el panel de español se abre la playlist predeterminada de español
     * @param mouseEvent
     */
    public void abrirEspanol(MouseEvent mouseEvent) {
        Usuario doniol=new Usuario("doniol","1234");
        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = doniol.obtenerListasUsuario();
        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            if (l.getId() == 2) {
                listaActual = l;
            }
        }
        List<Cancion> canciones = obtenerCancionesEnLista(2);
        rellenarPlayList(canciones);

        elimListbtn.setVisible(false);
        elimListbtn.setDisable(true);

        anadirNuevaCancionPlBtn.setDisable(true);
        anadirNuevaCancionPlBtn.setVisible(false);
    }


    public void rapEntered(MouseEvent mouseEvent) {
        cambiarCursorMano(mouseEvent, rapPane);
    }

    public void rapExited(MouseEvent mouseEvent) {
        cambiarCursorDefault(mouseEvent);
    }

    /**
     * Cuando se seleciona el panel de rap se abre la playlist predeterminada de rap
     * @param mouseEvent
     */
    public void abrirRap(MouseEvent mouseEvent) {
        Usuario doniol=new Usuario("doniol","1234");
        ArrayList<ListaDeCanciones> listaDeCancionesArrayList = doniol.obtenerListasUsuario();
        for (ListaDeCanciones l : listaDeCancionesArrayList) {
            if (l.getId() == 1) {
                listaActual = l;
            }
        }
        List<Cancion> canciones = obtenerCancionesEnLista(1);
        rellenarPlayList(canciones);

        elimListbtn.setVisible(false);
        elimListbtn.setDisable(true);

        anadirNuevaCancionPlBtn.setDisable(true);
        anadirNuevaCancionPlBtn.setVisible(false);
    }

    /**
     * Se cambia el apartado seleccionado a crear playlist y se ajusta el programa para que aparezca
     * el panel de crearPlaylist y se desactivan el resto
     * @param mouseEvent
     */
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

    /**
     * Se comprueba que la playlist tenga un nombre y si es asi se crea la lista con las canciones seleccionadas
     * y entonces se cierra el panel y se habilitan el resto de paneles
     * @param mouseEvent
     */
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

            // Vacia el array de canciones que se añaden a la lista
            cancionesAnadirNewPlaylist.removeAll(cancionesAnadirNewPlaylist);
            anadirCancionPane.setVisible(false);
        }
    }

    /**
     * Se oculta el panel de crearPalylist y se habilita el resto
     * @param mouseEvent
     */
    public void cancelarNuevaPlaylist(MouseEvent mouseEvent) {
        crearPlaylistPane.setVisible(false);
        anadirCancionPane.setVisible(false);

        if (playlistPrincipalPane.isVisible()) {
            cambiarLabelSeleccionado(cancionesFavLabel);
        } else cambiarLabelSeleccionado(inicioLabel);

        VBox limpiarPane = new VBox();
        cancionesAnadidas.setContent(limpiarPane);

        // Vacia el array de canciones que se añaden a la lista
        cancionesAnadirNewPlaylist.removeAll(cancionesAnadirNewPlaylist);

        nombreNuevaPlaylist.setText("Nombre de la Playlist");
        controlAppPane.setDisable(false);
        playlistPrincipalPane.setDisable(false);
        inicioPane.setDisable(false);
        cancionPane.setDisable(false);
    }

    /**
     * Al seleccionar el boton de añadir cancion cuando estamos creando una nueva lista
     * se abre un panel para añadir canciones y deshabilitamos el otro
     * @param mouseEvent
     */
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

    /**
     * Salimos del panel de añadir cancion cuando estamos creando una nueva lista
     * @param mouseEvent
     */
    public void cancelarNuevaCancion(MouseEvent mouseEvent) {

        anadirCancionPane.setVisible(false);
        crearPlaylistPane.setDisable(false);
    }

    /**
     * Al selecionar el panel donde introducimos el nombre de la nueva lista se le quita el texto por defecto
     * @param mouseEvent
     */
    public void vaciarNombreNuevaPlaylist(MouseEvent mouseEvent) {
        if (nombreNuevaPlaylist.getText().equals("Nombre de la Playlist")) {
            nombreNuevaPlaylist.setText("");
        }
    }

    /**
     * Abrimos el panel de cancones favoritas
     * @param mouseEvent
     */
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

    /**
     * Ponemos el panel principal delante
     * @param mouseEvent
     */
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

    /**
     * Cambiamos el label que esta seleccionado
     * @param label
     */
    public void cambiarLabelSeleccionado(Label label) {
        deselecionarLabel(labelSeleccionado.get(0));
        seleccionarLabel(label);
        labelSeleccionado.remove(0);
        labelSeleccionado.add(label);
    }

    /**
     * Al pulsar el boton de pausa/play se realiza cambia de uno a otro
     * @param mouseEvent
     */
    public void pausarYReproducir(MouseEvent mouseEvent) {
        reproducirCancion();
    }

    /**
     * Descargamos la cancion y la reproducimos
     */
    public void inicializarCancion() {
        cancionActual.descargarCancion();
        cancionActual.reproducirCancion();
    }

    /**
     * Comprobamos si se esta reproduciendo o no una cancion para sabes si pausamos o reproducimos la cancion actual
     */
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

                // Si la cancion no existe la inicializamos
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
     *
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
        tablaBusquedaPrin.getColumns().add(columnaNombrePlaylist1);
        tablaBusquedaPrin.getColumns().add(columnaArtista1);
        tablaBusquedaPrin.setItems(listaCanciones);
        tablaBusquedaPrin.setVisible(true);
    }


    /**
     * Rellenamos el panel de la playlist con las canciones de la playlist actual
     * @param mouseEvent
     */
    public void seleccionarPlayList(MouseEvent mouseEvent) {

        ListaDeCanciones listaDeCanciones = (ListaDeCanciones) playlistListView.getSelectionModel().selectedItemProperty().getValue();
        listaActual = listaDeCanciones;
        List<Cancion> canciones = obtenerCancionesEnLista(listaDeCanciones.getId());
        rellenarPlayList(canciones);
        System.out.println(listaDeCanciones.getId());
    }

    /**
     * Pausamos la cancion actual pasamos a la siguiente y comenzamos a reproducirla
     * @param mouseEvent
     */
    public void siguienteCancion(MouseEvent mouseEvent) {
        reproducirCancion();
        cancionActual = listaActual.siguiente(cancionActual);
        reproducirCancion();
        int indiceSeleccionado = playlistPrinListView.getSelectionModel().getSelectedIndex();
        playlistPrinListView.getSelectionModel().select(indiceSeleccionado + 1);
    }

    /**
     * Pausamos la cancion actual pasamos a la anterior y comenzamos a reproducirla
     * @param mouseEvent
     */
    public void anteriorCancion(MouseEvent mouseEvent) {
        reproducirCancion();
        cancionActual = listaActual.atras(cancionActual);
        reproducirCancion();
        int indiceSeleccionado = playlistPrinListView.getSelectionModel().getSelectedIndex();
        playlistPrinListView.getSelectionModel().select(indiceSeleccionado - 1);
    }

    /**
     * Al seleccionar una cancion de la barra de busqueda principal se comienza a reproducir
     * @param mouseEvent
     */
    public void obtenerCancionBusqueda(MouseEvent mouseEvent) {
        Cancion c1 = (Cancion) tablaBusquedaPrin.getSelectionModel().getSelectedItem();
        if (reproduciendo == true) {
            reproducirCancion();
        }
        cancionActual = c1;

        reproducirCancion();
    }

    /**
     * Cierra la tabla de busqueda al clickar fuera
     * @param mouseEvent
     */
    public void cerrarBusqueda(MouseEvent mouseEvent) {
        tablaBusquedaPrin.setVisible(false);
    }

    /**
     * Boton para bucar canciones al cuando las vas a
     * añadir a una playlist
     * @param mouseEvent
     */
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


    /**
     * Selecciona la cancion que se haya seleccionado
     * en la busqueda de añadir canciones a la playlist
     * @param mouseEvent
     */
    public void seleccionarCancionAnadir(MouseEvent mouseEvent) {
        anadirBoton.setDisable(true);
        if (tablaAnadirCancionPlayL.getSelectionModel().getSelectedItem() != null) {
            anadirBoton.setDisable(false);
        }
    }

    /**
     * Al seleccionar una cancion dentro cuando estamos añadiendo una cancion a una lista ya creada
     * si no esta dentro se la añadira
     * @param actionEvent
     */
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

    /**
     * abrimos el panel de añadir una cancion a una lista ya creada y deshablitamos el resto
     * @param mouseEvent
     */
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

    /**
     * Salimos del panel de añadir una cancion a una lista ya creada y hablitamos el resto
     * @param mouseEvent
     */
    public void cancelarNuevaCancionPlayL(MouseEvent mouseEvent) {
        anadirCancionPlayLPane.setVisible(false);
        playlistPrincipalPane.setDisable(false);
        controlAppPane.setDisable(false);
    }

    /**
     * Salimos del panel de añadir una cancion a una lista ya creada, hablitamos el resto
     * y guardamos
     * @param mouseEvent
     */
    public void guardarNuevaCancionPlayL(MouseEvent mouseEvent) {
        if (tablaAnadirCancionPlayLPrin.getSelectionModel().getSelectedItem() != null) {
            Cancion c1 = (Cancion) tablaAnadirCancionPlayLPrin.getSelectionModel().getSelectedItem();
            if (!ListaDeCanciones.encontrarCancion(c1.getId(), listaActual.getId())) {
                ListaDeCanciones.addCancion(listaActual.getId(), c1.getId());
                List<Cancion> canciones = obtenerCancionesEnLista(listaActual.getId());
                rellenarPlayList(canciones);
            }
            cancelarNuevaCancionPlayL(mouseEvent);
        }

    }

    /**
     * Se crea lo necesario para la barra de busqueda de añadir cancion a una lista creada
     * @param mouseEvent
     */
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


    /**
     * Abre el panel de ajustes de usuario
     * @param mouseEvent
     */
    public void abrirAjustes(MouseEvent mouseEvent) {
        controlAppPane.setDisable(true);
        playlistPrincipalPane.setDisable(true);
        inicioPane.setDisable(true);
        cancionPane.setDisable(true);

        ajustesPane.setVisible(true);
    }

    /**
     * Habilita los campos para cambiar la contraseña del usuario
     * @param mouseEvent
     */
    public void cambContra(MouseEvent mouseEvent) {
        antContra.setDisable(false);
        nuevContra.setDisable(false);
        acepContBtn.setDisable(false);
    }

    /**
     * Si la contraseña actual es correcta se cambiara por la nueva contraseña
     * @param mouseEvent
     */
    public void acepContra(MouseEvent mouseEvent) {
        String antContAux=antContra.getText();
        if (antContAux.equals(usuario.getPassword())){
            usuario.cambiarContraseña(nuevContra.getText());
            usuario.setPassword(nuevContra.getText());
        }
    }

    /**
     * Habilita los campos para cambiar el nombre del usuario
     * @param mouseEvent
     */
    public void cambUsu(MouseEvent mouseEvent) {
        nuevNombre.setDisable(false);
        acepUsuBtn.setDisable(false);
    }

    /**
     * Cambia el nombre del usuario
     * @param mouseEvent
     */
    public void acepUsuNom(MouseEvent mouseEvent) {
        usuario.cambiarUsuario(nuevNombre.getText());
    }


    /**
     * Cerramos el panel de ajustes y habilitamos el resto
     * @param mouseEvent
     */
    public void cerrarAju(MouseEvent mouseEvent) {
        controlAppPane.setDisable(false);
        playlistPrincipalPane.setDisable(false);
        inicioPane.setDisable(false);
        cancionPane.setDisable(false);

        ajustesPane.setVisible(false);
    }

    /**
     * Método para eliminar cuenta del usuario
     * @param actionEvent
     */
    public void borrarCuenta(ActionEvent actionEvent) {
        if (usuario.eliminarCuenta()){
            try {
                abrirInterfazInicio();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Método para abrir la interfaz de inicio de sesion
     */
    public void abrirInterfazInicio() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/donimusic/iniciarSesion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage crearCuentaStage = new Stage();
        crearCuentaStage.setTitle("Iniciar Sesión");
        crearCuentaStage.setResizable(false);
        crearCuentaStage.setScene(scene);
        crearCuentaStage.show();
        Stage myStage = (Stage) this.buscarNCTextField.getScene().getWindow();
        myStage.close();
    }

    /**
     * Abrimos el panel para editar el nombre de una lista
     * @param mouseEvent
     */
    public void editarNomList(MouseEvent mouseEvent) {
        controlAppPane.setDisable(true);
        playlistPrincipalPane.setDisable(true);

        nuevoNomListPane.setVisible(true);
    }

    /**
     * Modifica en nmbre de una playList
     * @param mouseEvent
     */
    public void cambiarNombreList(MouseEvent mouseEvent) {
        if (!nuevoNomList.getText().isBlank()) {
            String nuevoNombreAux=nuevoNomList.getText();
            listaActual.cambiarNombreLista(nuevoNombreAux);
            listaActual.setNombre(nuevoNombreAux);
            controlAppPane.setDisable(false);
            playlistPrincipalPane.setDisable(false);

            nuevoNomListPane.setVisible(false);

            rellenarPlayList(obtenerCancionesEnLista(listaActual.getId()));
            rellenarPanelTusPlayList();
        }
    }

    public void eliminarLista(MouseEvent mouseEvent) {
        listaActual.eliminarLista();
        irInicio(mouseEvent);
        rellenarPanelTusPlayList();

    }

    public void cambiarSegundo(MouseEvent mouseEvent) {
        double segundo = cancionSlider.getValue();
        Duration duration = new Duration(segundo*1000);
        cancionActual.cambiarSegundo(duration);
        continuar =true;
    }

    public void deslSlider(MouseEvent mouseEvent) {
        continuar =false;
    }
}
