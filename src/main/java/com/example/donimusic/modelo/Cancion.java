package com.example.donimusic.modelo;
import com.example.donimusic.controlador.CrearCuenta;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cancion {
    private int id;
    private String nombre;
    private String nombreArtista;
    private String archivo;
    private int duracion;
    private String album;
    private static Media media=null;
    private static MediaPlayer mediaPlayer=null;
    private static Connection c= Conexion.con;



    public Cancion(int id, String nombre, String nombreArtista,String archivo, String album) {
        this.id = id;
        this.nombre = nombre;
        this.nombreArtista = nombreArtista;
        this.archivo=archivo;
        this.duracion = 1;
        this.album = album;
    }

    public Cancion() {
        this.duracion=1;
    }

    public static Cancion crearCancion(int id, String nombre,String archivo, String nombreArtista, String album){
        Cancion cancion= new Cancion(id,nombre,nombreArtista,archivo,album);
        return cancion;
    }

    /**
     * Este metodo sube una cancion con los datos que han sido pasados en la interfaz gráfica
     * La columna archivo sera la variable destinoCancion seguida del nombre de la variable
     *
     * @param cancion
     * @param rutaCancion esto es la ruta local del archivo.mp3
     */
    public boolean subirCancion(Cancion cancion,String rutaCancion){
        if (comprobarMp3(rutaCancion)){
            rutaCancion=pasarARutaValida(rutaCancion);
            // TODO: 24/11/2023 Realizar la insercion
            return true;
        }else return false;
    }

    public boolean comprobarMp3(String rutaCancion){
        String extension = String.valueOf(rutaCancion.charAt(rutaCancion.length()));
        extension += String.valueOf(rutaCancion.charAt(rutaCancion.length()-1));
        extension += String.valueOf(rutaCancion.charAt(rutaCancion.length()-2));
        extension = extension.toLowerCase();
        if (extension.equals("mp3")){
            return true;
        }else return false;
    }
    public static void descargarCancion(String rutaBaseDatos, int idCancion) {
        String stringCancion = String.valueOf(idCancion);
        String rutaGuardado = "C:\\Users\\angel\\IdeaProjects\\DoniMusic\\src\\main\\resources\\canciones\\"+ stringCancion +".mp3"; // Ruta local de guardado

        try {
            URL url = new URL(rutaBaseDatos);
            URLConnection connection = url.openConnection();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(rutaGuardado);

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            // Cierre de recursos utilizando try-with-resources
            try (in; fileOutputStream) {
                System.out.println("Descarga completa.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String pasarARutaValida(String rutaCancion){
        String rutaCancionAux = rutaCancion;
        rutaCancion = "file:///";
        rutaCancion += rutaCancionAux;
        rutaCancion = rutaCancion.replaceAll(" ","%20");
        return rutaCancion;
    }
    public void reproducirCancion(int id){
        String idCancion=String.valueOf(id);
        File archivo=new File(String.valueOf(Cancion.class.getResource("C:\\Users\\angel\\IdeaProjects\\DoniMusic\\src\\main\\resources\\canciones\\"+idCancion+".mp3")));
        media=new Media(archivo.toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static ArrayList<Cancion> buscarCancion(String busqueda){
        ArrayList<Cancion>canciones=new ArrayList<>();
        PreparedStatement stm;
        try {
            stm = c.prepareStatement("SELECT * FROM cancion WHERE nombreCancion LIKE '"+busqueda+"%';");
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                int id=result.getInt("cancionId");
                String nombre=result.getString("nombreCancion ");
                String archivo=result.getString("archivo");
                String nombreArtista= result.getString("artista");
                String album=result.getString("album");
                Cancion c1=new Cancion(id,nombre,nombreArtista,archivo,album);
                canciones.add(c1);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return canciones;

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

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }


    public String getArchivo() {
        return archivo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

}
