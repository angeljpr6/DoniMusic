package com.example.donimusic.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaDeCanciones {

    int id;
    String nombre;
    String nombreCreador;

    private boolean reproductor =false;

    private static Connection c = Conexion.con;

    public ListaDeCanciones(int idLista, String nombre, String nombreCreador) {
        this.id = idLista;
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

    public boolean isReproductor() {
        return reproductor;
    }

    public void setReproductor(boolean reproductor) {
        this.reproductor = reproductor;
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

    public int crearLista(String nombreUsuario) {
        try {
            PreparedStatement stm = c.prepareStatement("INSERT INTO lista (nombre, nombreUsuario) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, nombre);
            stm.setString(2, nombreUsuario);

            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {


                // Obtener el id de la lista recién creada
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }

                return id;
            } else {
                //No se pudo crear la lista de canciones
                return -1; // Retorna un valor que indique que no se pudo crear la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void eliminarLista() {
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM lista WHERE nombre = ?");
            stm.setString(1, this.nombre);


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
            e.printStackTrace();
        }
    }

    private boolean existeLista(int idLista) {
        try {
            String sql = "SELECT COUNT(*) FROM lista WHERE listaId = ?";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setInt(1, idLista);

                try (ResultSet resultSet = stm.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarCancion(int idCancion) {
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM playListCanciones WHERE listaId = ? AND cancionId = ?");
            stm.setInt(1, id);
            stm.setInt(2, idCancion);

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
                        String artista = resultSet.getString("artista");

                        Cancion cancion = new Cancion(idCancion, nombre, artista, album);
                        cancionesEnLista.add(cancion);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;
    }

    public Cancion siguiente(Cancion cancion) {

        // Obtiene la lista de canciones asociadas a la lista de reproducción actual
        List<Cancion> cancionesEnLista = obtenerCancionesEnLista(this.id);
        if(this.reproductor==false){


        // Verifica si la lista de canciones no está vacía
        if (!cancionesEnLista.isEmpty()) {
            // Obtiene el índice de la canción proporcionada en la lista de canciones
            int index = cancionesEnLista.indexOf(cancion);

            // Verifica si la canción está en la lista (index != -1)
            if (index != -1) {
                // Verifica si hay una canción siguiente en la lista
                if (index < cancionesEnLista.size() - 1) {
                    // Si hay una canción siguiente, incrementa el índice
                    index++;
                    Cancion siguienteCancion = cancionesEnLista.get(index);
                    return siguienteCancion;
                } else {
                    // Si estamos en la última canción, vuelve al inicio de la lista
                    Cancion siguienteCancion = cancionesEnLista.get(0);
                    return siguienteCancion;
                }
            }

            }
        }
        else {
            llamaraleatorio(cancion);
        }

        // Retorna null si no se pudo reproducir la siguiente canción
        return null;
    }


    public Cancion atras(Cancion cancion) {
        // Obtiene la lista de canciones asociadas a la lista de reproducción actual
        List<Cancion> cancionesEnLista = obtenerCancionesEnLista(this.id);
        if(this.reproductor==false) {
            // Verifica si la lista de canciones no está vacía
            if (!cancionesEnLista.isEmpty()) {
                // Obtiene el índice de la canción proporcionada en la lista de canciones
                int index = cancionesEnLista.indexOf(cancion);

                // Verifica si la canción está en la lista (index != -1)
                if (index != -1) {
                    // Verifica si hay una canción siguiente en la lista
                    if (index > 0) {
                        // Si hay una canción siguiente, incrementa el índice
                        index--;
                        Cancion previaCancion = cancionesEnLista.get(index);
                        return previaCancion;
                    } else {
                        // Si estamos en la última canción, vuelve al inicio de la lista
                        index = cancionesEnLista.size() - 1;
                        Cancion previaCancion = cancionesEnLista.get(index);
                        return previaCancion;
                    }

                }
            }
        }
        else {
            llamaraleatorio(cancion);
        }

        // Retorna null si no se pudo reproducir la siguiente canción
        return null;
    }

public Cancion llamaraleatorio(Cancion cancion){
    // Obtiene la lista de canciones asociadas a la lista de reproducción actual
    List<Cancion> cancionesEnLista = obtenerCancionesEnLista(this.id);

    // Verifica si la lista de canciones no está vacía
    if (!cancionesEnLista.isEmpty()) {
        // Obtiene el índice de la canción proporcionada en la lista de canciones
        int index = cancionesEnLista.indexOf(cancion);

        // Verifica si la canción está en la lista (index != -1)
        if (index != -1) {
            // Genera un índice aleatorio dentro del rango de la lista
            int randomIndex = (int) (Math.random() * cancionesEnLista.size());

            // Verifica si el índice aleatorio es igual al índice actual
            while (randomIndex == index) {
                randomIndex = (int) (Math.random() * cancionesEnLista.size());
            }

            // Reproduce la canción aleatoria
            Cancion cancionAleatoria = cancionesEnLista.get(randomIndex);
            return cancionAleatoria;
        }
    }

    // Retorna null si no se pudo reproducir la canción aleatoria
    return null;
}

    public boolean cambiarOrdenRep() {
        if (this.reproductor == true) {
            this.reproductor=false;
        }
        else if (this.reproductor == false) {
            this.reproductor=true;
        }
        return this.reproductor;
    }
    public static List<Cancion> obtenerCancionesEnLista(int idLista) {
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

                        Cancion cancion = new Cancion(idCancion, nombre, artista, album);
                        cancionesEnLista.add(cancion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;
    }
    public void mostrarListaDeCanciones(int idLista) {
        List<Cancion> cancionesEnLista = obtenerCancionesEnLista(idLista);


        for (Cancion cancion : cancionesEnLista) {
            System.out.println("ID: " + cancion.getId() +
                    ", Nombre: " + cancion.getNombre() +
                    ", Artista: " + cancion.getNombreArtista() +
                    ", Album: " + cancion.getAlbum() );
        }
    }
    public Cancion encontrarCancion(String nombreCancion, int idLista) {
        Cancion cancionEncontrada = null;
        try {
            String sql = "SELECT c.* FROM cancion c JOIN playListCanciones plc ON c.cancionId = plc.cancionId WHERE c.nombreCancion = ? AND plc.listaId = ?";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setString(1, nombreCancion);
                stm.setInt(2, idLista);

                try (ResultSet resultSet = stm.executeQuery()) {
                    if (resultSet.next()) {
                        int idCancion = resultSet.getInt("cancionId");
                        String nombre = resultSet.getString("nombreCancion");
                        String album = resultSet.getString("album");
                        String archivo = resultSet.getString("archivo");
                        String artista = resultSet.getString("artista");

                        cancionEncontrada = new Cancion(idCancion, nombre, artista, album);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionEncontrada;
    }


}

