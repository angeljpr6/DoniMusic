package com.example.donimusic.modelo;

import com.example.donimusic.modelo.Conexiones.Conexion;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Cancion {
    public static MediaPlayer mediaPlayer = null;
    private static Media media = null;
    private static Connection c = Conexion.con;
    private int id;
    private String nombre;
    private String nombreArtista;
    private String ruta;
    private int duracion;
    private String album;


    public Cancion(int id, String nombre, String nombreArtista, String album) {
        this.id = id;
        this.nombre = nombre;
        this.nombreArtista = nombreArtista;
        this.ruta = "";
        this.album = album;
    }

    public Cancion() {

    }


    /**
     * Métedo para buscar canciones por nombre
     * @param busqueda
     * @return
     */
    public static ArrayList<Cancion> buscarCancion(String busqueda) {
        ArrayList<Cancion> canciones = new ArrayList<>();
        PreparedStatement stm;
        try {
            //se busca en los nombres de las canciones segun el nombre que se le pasa al metodo
            stm = c.prepareStatement("SELECT * FROM cancion WHERE nombreCancion LIKE '" + busqueda + "%';");
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                int id = result.getInt("cancionId");
                String nombre = result.getString("nombreCancion");
                String archivo = result.getString("archivo");
                String nombreArtista = result.getString("artista");
                String album = result.getString("album");
                Cancion c1 = new Cancion(id, nombre, nombreArtista, album);
                canciones.add(c1);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return canciones;

    }

    /**
     * Método para descargar el mp3 de la base de datos
     */
    public void descargarCancion() {
        File tempFile = null;
        try {
            PreparedStatement stm = c.prepareStatement("select archivo from cancion where cancionId=?;");
            stm.setInt(1, this.id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                Blob mp3Blob = resultSet.getBlob("archivo");
                // Crear un archivo temporal
                tempFile = File.createTempFile("tempFile", ".mp3");
                tempFile.deleteOnExit();
                // Escribir el Blob en el archivo temporal
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                InputStream inputStream = mp3Blob.getBinaryStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                ;
            }
            this.ruta = tempFile.getAbsolutePath();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Método para reproducir la cancion llamando a los
     * métodos necesarios
     */
    public void reproducirCancion() {
        this.descargarCancion();
        File archivo = new File(this.ruta);
        media = new Media(archivo.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * Método para pausar la cancion
     */
    public void pausarCancion() {
        mediaPlayer.pause();
    }
    /**
     * Método para pausar la cancion
     */
    public void playCancion() {
        mediaPlayer.play();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}
