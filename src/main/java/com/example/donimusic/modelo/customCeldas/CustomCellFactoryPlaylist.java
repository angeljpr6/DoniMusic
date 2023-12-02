package com.example.donimusic.modelo.customCeldas;

import com.example.donimusic.modelo.ListaDeCanciones;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomCellFactoryPlaylist implements Callback<ListView<ListaDeCanciones>, ListCell<ListaDeCanciones>> {
    @Override
    public ListCell<ListaDeCanciones> call(ListView<ListaDeCanciones> listView) {
        return new CustomCellPlaylist();
    }
}

