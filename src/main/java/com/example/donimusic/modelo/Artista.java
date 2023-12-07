package com.example.donimusic.modelo;


public class Artista {
    private String nombre;
    private int numCanciones;

    public Artista(String nombre, int numCanciones) {
        this.nombre = nombre;
        this.numCanciones = numCanciones;
    }

    /**
     * Este metodo sube una cancion con los datos que han sido pasados en la interfaz gráfica
     * La columna archivo sera la variable destinoCancion seguida del nombre de la variable
     *
     * @param cancion
     * @param rutaCancion esto es la ruta local del archivo.mp3
     */
    public boolean subirCancion(Cancion cancion, String rutaCancion) {
        if (comprobarMp3(rutaCancion)) {
            rutaCancion = pasarARutaValida(rutaCancion);
            // TODO: 24/11/2023 Realizar la insercion
            return true;
        } else return false;
    }

    public void eliminarCancion(Cancion cancion) {

    }

    public boolean comprobarMp3(String rutaCancion) {
        String extension = String.valueOf(rutaCancion.charAt(rutaCancion.length()));
        extension += String.valueOf(rutaCancion.charAt(rutaCancion.length() - 1));
        extension += String.valueOf(rutaCancion.charAt(rutaCancion.length() - 2));
        extension = extension.toLowerCase();
        if (extension.equals("mp3")) {
            return true;
        } else return false;
    }

    public String pasarARutaValida(String rutaCancion) {
        String rutaCancionAux = rutaCancion;
        rutaCancion = "file:///";
        rutaCancion += rutaCancionAux;
        rutaCancion = rutaCancion.replaceAll(" ", "%20");
        return rutaCancion;
    }


}
