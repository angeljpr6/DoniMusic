package com.example.donimusic.modelo.customCeldas;

import com.example.donimusic.modelo.Cancion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomCellFactoryCan implements Callback<ListView<Cancion>, ListCell<Cancion>> {

    @Override
    public ListCell<Cancion> call(ListView<Cancion> cancionListView) {
        return new CustomCellCan();
    }
}
