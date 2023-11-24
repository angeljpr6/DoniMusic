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

public class Cancion {
    private int id;
    private String nombre;
    private String nombreArtista;
    private int duracion;
    private String album;

    public Cancion(int id, String nombre, String nombreArtista, int duracion, String album) {
        this.id = id;
        this.nombre = nombre;
        this.nombreArtista = nombreArtista;
        this.duracion = duracion;
        this.album = album;
    }

    public Cancion() {
    }

    public static Cancion crearCancion(int id, String nombre, String nombreArtista, int duracion, String album){
        Cancion cancion= new Cancion(id,nombre,nombreArtista,duracion,album);
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
        Media audio=new Media(archivo.toURI().toString());
        MediaPlayer reproductor=new MediaPlayer(audio);
        reproductor.play();
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
