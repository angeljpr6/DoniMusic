package com.example.donimusic.controlador;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public ScrollPane panelPlaylists;
    public Label tusPlaylistLabel;
    private VBox vboxTusPlaylist = new VBox();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rellenarPanelTusPlayList();
    }
    public void establecerEstilos(){

    }
    public void rellenarPanelTusPlayList(){

        double scrollPaneWidth = panelPlaylists.getPrefWidth()-15;
        panelPlaylists.setStyle("-fx-background-color: #212628;");

        for (int i = 1; i <= 100; i++) {
            Label label =new Label("Elemento " + i);
            label.setFont(tusPlaylistLabel.getFont());
            label.setStyle("-fx-text-fill: #838383;");
            vboxTusPlaylist.getChildren().add(label);

        }
        vboxTusPlaylist.setStyle("-fx-background-color:  #212628; -fx-min-width: "+scrollPaneWidth+"px;");

        String scrollbarStyle = "-fx-background-color: #FF0000;"; // Color de fondo de las barras
        scrollbarStyle += "-fx-background: #00FF00;"; // Color de las barras
        scrollbarStyle += "-fx-border-color: #0000FF;"; // Color del borde de las barras
        panelPlaylists.lookup(".scroll-bar:vertical").setStyle(scrollbarStyle);

        panelPlaylists.setContent(vboxTusPlaylist);
    }
}
