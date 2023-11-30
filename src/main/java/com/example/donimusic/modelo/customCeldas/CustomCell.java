package com.example.donimusic.modelo.customCeldas;

import com.example.donimusic.modelo.ListaDeCanciones;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CustomCell extends ListCell<ListaDeCanciones> {
    private final VBox vbox;
    private final Label label;
    public static Color colorTexto;

    public CustomCell() {
        vbox = new VBox();
        label = new Label();
        vbox.getChildren().add(label);
        vbox.setPrefHeight(50);

        // Letra
        String estilo = " -fx-font-size: 17px;";
        vbox.setStyle(estilo);
        Color textoInicial = Color.rgb(0x83, 0x83, 0x83);
        label.setTextFill(textoInicial);
    }

    @Override
    protected void updateItem(ListaDeCanciones item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            label.setText(item.getNombre());
            setGraphic(vbox);
            Color fondo = Color.rgb(0x21, 0x26, 0x28);
            setBackground(new Background(new BackgroundFill(fondo, null, null)));
        }

        selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                label.setTextFill(Color.WHITE);
            } else {
                Color texto = Color.rgb(0x83, 0x83, 0x83);
                label.setTextFill(texto);
            }
        });
    }
}
