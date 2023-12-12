package com.example.donimusic.modelo.customCeldas;

import com.example.donimusic.controlador.Home;
import com.example.donimusic.modelo.Cancion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomCellFactoryCan implements Callback<ListView<Cancion>, ListCell<Cancion>> {

    private final Home home;

    public CustomCellFactoryCan(Home home) {
        this.home = home;
    }

    @Override
    public ListCell<Cancion> call(ListView<Cancion> cancionListView) {
        return new CustomCellCan(home);
    }
}
