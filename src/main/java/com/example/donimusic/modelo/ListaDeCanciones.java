package com.example.donimusic.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaDeCanciones {

    int id;
    String nombre;
    String nombreCreador;
    private static Connection c= Conexion.con;
    public ListaDeCanciones(String nombre, String nombreCreador) {
        this.nombre = nombre;
        this.nombreCreador = nombreCreador;
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

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public  void crearLista(String nombreUsuario){
        try {
            PreparedStatement stm = c.prepareStatement("INSERT INTO listaCancion (nombre, nombreUsuario) VALUES (?, ?)");
            stm.setString(1, nombre);
            stm.setString(2, nombreUsuario);

            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Lista de canciones creada exitosamente.");
            } else {
                System.out.println("No se pudo crear la lista de canciones.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void eliminarLista(){
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM listaCancion WHERE nombre = ?");
            stm.setString(1, nombre);


            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Lista de canciones eliminada exitosamente.");
            } else {
                System.out.println("La lista de canciones no existe en la base de datos.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void addCancion(int idCancion){
        try {
            PreparedStatement stm = c.prepareStatement("INSERT INTO listaCancion (cancionId) SELECT cancionId FROM cancion WHERE cancionId = ?");
            stm.setInt(1, idCancion);

            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("La canción se añadió correctamente a la lista.");
            } else {
                System.out.println("No se pudo añadir la canción a la lista.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void  eliminarCancion(int idCancion){

        try {
            String sql = "DELETE FROM listaCancion WHERE cancionId = (SELECT cancionId FROM cancion WHERE cancionId = ?)";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setInt(1, idCancion);

                int filasAfectadas = stm.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Canción eliminada de la lista exitosamente.");
                } else {
                    System.out.println("No se encontró la canción en la lista.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cancion>  buscarCancion(String nombreCancion, int idLista ) {
        List<Cancion> cancionesEnLista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cancion WHERE nombreCancion =? AND cancionId IN (SELECT cancionId FROM listaCancion WHERE cancionId = ?);" ;
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setString(1, nombreCancion);
                stm.setInt(2, idLista);

                try (ResultSet resultSet = stm.executeQuery()) {
                    while (resultSet.next()) {

                        int idCancion = resultSet.getInt("cancionId");
                        String nombre = resultSet.getString("nombreCancion");
                        String album = resultSet.getString("album");
                        String archivo = resultSet.getString("archivo");
                        String artista = resultSet.getString("artista");

                        // Crear objeto Cancion y agregarlo a la lista
                        Cancion cancion = new Cancion(idCancion, nombre, artista, archivo,album);
                        cancionesEnLista.add(cancion);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;

    }
    public void  atras(){

    }
    public void  cambiarOrdenRep(){

    }
}
