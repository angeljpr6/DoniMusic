package com.example.donimusic.modelo.customCeldas;

import com.example.donimusic.controlador.Home;
import com.example.donimusic.modelo.Cancion;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CustomCellCan extends ListCell<Cancion> {
    private final Home home;
    private final HBox hbox;
    private final Label nombreLabel;
    private final Label artistLabel;
    private final Label duracionLabel;

    public CustomCellCan(Home home) {
        this.home = home;
        hbox = new HBox();

        nombreLabel = new Label();
        artistLabel = new Label();
        duracionLabel = new Label();

        nombreLabel.setPrefWidth(393);
        artistLabel.setPrefWidth(348);
        duracionLabel.setPrefWidth(57);

        //añado los label al hBox
        hbox.getChildren().add(nombreLabel);
        hbox.getChildren().add(artistLabel);
        hbox.getChildren().add(duracionLabel);
        hbox.setPrefHeight(50);

        // Letra
        String estilo = " -fx-font-size: 17px;";
        hbox.setStyle(estilo);
        Color textoInicial = Color.rgb(0x83, 0x83, 0x83);
        nombreLabel.setTextFill(textoInicial);
        artistLabel.setTextFill(textoInicial);
        duracionLabel.setTextFill(textoInicial);
    }

    @Override
    protected void updateItem(Cancion item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            nombreLabel.setText(item.getNombre() + " ");
            artistLabel.setText(item.getNombreArtista() + " ");
            duracionLabel.setText(item.getDuracion() + "`s ");
            setGraphic(hbox);
            Color fondo = Color.rgb(0x21, 0x26, 0x28);
            setBackground(new Background(new BackgroundFill(fondo, null, null)));
        }

        selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                nombreLabel.setTextFill(Color.WHITE);
                artistLabel.setTextFill(Color.WHITE);
                duracionLabel.setTextFill(Color.WHITE);
                if (item != null) {
                    if (Home.cancionActual != null) {
                        Home.cancionActual.setRuta("");
                    }
                    if (Home.reproduciendo) {
                        home.reproducirCancion();
                    }
                    Home.cancionActual = item;
                    home.reproducirCancion();
                } else {

                }

            } else {
                Color texto = Color.rgb(0x83, 0x83, 0x83);
                nombreLabel.setTextFill(texto);
                artistLabel.setTextFill(texto);
                duracionLabel.setTextFill(texto);
            }
        });
    }
}
