package com.example.donimusic.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

    public void crearLista(String nombreUsuario) {
        try {
            PreparedStatement stm = c.prepareStatement("INSERT INTO lista (nombre, nombreUsuario) VALUES (?, ?)");
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

    public void eliminarLista() {
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM lista WHERE nombre = ?");
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

    public void addCancion(int idCancion) {
        try {
            PreparedStatement stm = c.prepareStatement("INSERT INTO playListCanciones (listaId, cancionId) VALUES (?, ?)");
            stm.setInt(1, id);
            stm.setInt(2, idCancion);

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

    public void eliminarCancion(int idCancion) {
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM playListCanciones WHERE listaId = ? AND cancionId = ?");
            stm.setInt(1, id);
            stm.setInt(2, idCancion);

            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Canción eliminada de la lista exitosamente.");
            } else {
                System.out.println("No se encontró la canción en la lista.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cancion> buscarCancion(String nombreCancion, int idLista) {
        List<Cancion> cancionesEnLista = new ArrayList<>();
        try {
            String sql = "SELECT c.* FROM cancion c JOIN playListCanciones plc ON c.cancionId = plc.cancionId WHERE c.nombreCancion = ? AND plc.listaId = ?";
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

                        Cancion cancion = new Cancion(idCancion, nombre, artista, archivo, album);
                        cancionesEnLista.add(cancion);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;
    }

    public int siguiente(int idLista, int posicionActual) {
       List<Cancion> cancionesEnLista = obtenerCancionesEnLista(idLista);

        if (!cancionesEnLista.isEmpty()) {
            if (posicionActual < cancionesEnLista.size() - 1) {
                posicionActual++;
            } else {
                posicionActual = 0;
            }

            reproducirCancionActual(cancionesEnLista.get(posicionActual));
        } else {
            System.out.println("No hay canciones en la lista para reproducir.");
        }

        return posicionActual;
    }


    public int atras(int idLista, int posicionActual) {
        List<Cancion>   cancionesEnLista = obtenerCancionesEnLista(idLista);

        if (!cancionesEnLista.isEmpty()) {
            if (posicionActual > 0) {
                posicionActual--;
            } else {
                posicionActual = cancionesEnLista.size() - 1;
            }

            reproducirCancionActual(cancionesEnLista.get(posicionActual));
        } else {
            System.out.println("No hay canciones en la lista para reproducir.");
        }

        return posicionActual;
    }
    public void cambiarOrdenRep(int idLista) {
        List<Cancion> cancionesEnLista = obtenerCancionesEnLista(idLista);

        if (!cancionesEnLista.isEmpty()) {
            // Mezcla el orden de las canciones en la lista de forma aleatoria
            Collections.shuffle(cancionesEnLista);

            // Reproduce la primera canción en la nueva lista mezclada
            reproducirCancionActual(cancionesEnLista.get(0));
        } else {
            System.out.println("No hay canciones en la lista para reproducir.");
        }
    }
    private List<Cancion> obtenerCancionesEnLista(int idLista) {
        List<Cancion> cancionesEnLista = new ArrayList<>();
        try {
            String sql = "SELECT c.* FROM cancion c JOIN playListCanciones plc ON c.cancionId = plc.cancionId WHERE plc.listaId = ?";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setInt(1, idLista);

                try (ResultSet resultSet = stm.executeQuery()) {
                    while (resultSet.next()) {
                        int idCancion = resultSet.getInt("cancionId");
                        String nombre = resultSet.getString("nombreCancion");
                        String album = resultSet.getString("album");
                        String archivo = resultSet.getString("archivo");
                        String artista = resultSet.getString("artista");

                        Cancion cancion = new Cancion(idCancion, nombre, artista, archivo, album);
                        cancionesEnLista.add(cancion);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;
    }
    private void reproducirCancionActual(Cancion cancion) {
       //No se que poner aqui si ya hay un metodo o algo pero si funciona bien tendría que salier el sout digo en los metodos anteriores por si pueden hacer pruebas

        System.out.println("Reproduciendo: " + cancion.getNombre());
    }
}

