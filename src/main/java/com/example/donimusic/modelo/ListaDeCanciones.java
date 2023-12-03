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

    public static void crearLista(String nombreLista, String usuario) {
        try {
            // El " Statement.RETURN_GENERATED_KEYS" especifica que se deben devolver las claves generadas automáticamente.
            PreparedStatement stm = c.prepareStatement("INSERT INTO lista (nombre, nombreUsuario) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, nombreLista);
            stm.setString(2, usuario);

            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el id de la lista recién creada que contiene las claves generadas automáticamente por la base de datos.
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    // Asignar este ID a la variable 'id' para su posterior uso.
                    int id = rs.getInt(1);

                    // Llamar al método para establecer la relación en la tabla playListUsuarios
                    obtenerIdLista(nombreLista, usuario);
                } else {
                    System.out.println("No se pudo obtener el ID de la lista de canciones.");
                }
            } else {
                System.out.println("No se pudo crear la lista de canciones.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void obtenerIdLista(String nombreLista, String nombreUsuario) {
        try {
            // Preparar una sentencia SQL para obtener el ID de la última lista recién creada por el usuario
            PreparedStatement stm = c.prepareStatement("SELECT listaId FROM lista WHERE nombre = ? AND nombreUsuario = ? ORDER BY listaId DESC LIMIT 1");
            stm.setString(1, nombreLista);
            stm.setString(2, nombreUsuario);

            // Ejecutar la consulta y obtener un conjunto de resultados
            ResultSet rs = stm.executeQuery();

            // Verificar si se encontró una fila en los resultados
            if (rs.next()) {
                // Obtener el ID de la lista
                int idLista = rs.getInt("listaId");

                // Llamar al método para establecer la relación en la tabla playListUsuarios
                establecerRelacionPlayListUsuario(idLista, nombreUsuario);
            } else {
                // Si no se encontró la lista, imprimir un mensaje (puedes ajustar según necesites)
                System.out.println("La lista no fue encontrada en la base de datos para el usuario " + nombreUsuario);
            }
        } catch (SQLException e) {
            // Manejar excepciones
            throw new RuntimeException(e);
        }
    }


    private static void establecerRelacionPlayListUsuario(int idLista, String nombreUsuario) {
        try {
            // Preparar una sentencia SQL para insertar una fila en la tabla playListUsuarios
            PreparedStatement stm = c.prepareStatement("INSERT INTO playListUsuarios (listaId, nombreUsuario) VALUES (?, ?)");
            stm.setInt(1, idLista);
            stm.setString(2, nombreUsuario);

            // Ejecutar la sentencia SQL de inserción
            int filasAfectadas = stm.executeUpdate();

            // Verificar si la inserción fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Relación playListUsuarios establecida correctamente.");
            } else {
                System.out.println("No se pudo establecer la relación en playListUsuarios.");
            }
        } catch (SQLException e) {
            // Manejar excepciones
            throw new RuntimeException(e);
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

        if (!reproductor) { // Si el reproductor está desactivado
            // Verifica si la lista de canciones no está vacía
            if (!cancionesEnLista.isEmpty()) {
                int index = obtenerIndiceCancion(cancion, cancionesEnLista);

                // Verifica si la canción está en la lista
                if (index != -1) {
                    // Verifica si hay una canción siguiente en la lista
                    if (index < cancionesEnLista.size() - 1) {
                        // Si hay una canción siguiente, incrementa el índice
                        index++;
                        return cancionesEnLista.get(index);
                    } else {
                        // Si estamos en la última canción, vuelve al inicio de la lista
                        return cancionesEnLista.get(0);
                    }
                }
            }
        } else {
            // Llamada al método para reproducir canción aleatoria
            return llamarAleatorio(cancionesEnLista);
        }

        // Retorna null si no se pudo reproducir la siguiente canción
        return null;
    }

    public Cancion atras(Cancion cancion) {
        // Obtiene la lista de canciones asociadas a la lista de reproducción actual
        List<Cancion> cancionesEnLista = obtenerCancionesEnLista(this.id);

        if (!reproductor) { // Si el reproductor está desactivado
            // Verifica si la lista de canciones no está vacía
            if (!cancionesEnLista.isEmpty()) {
                int index = obtenerIndiceCancion(cancion, cancionesEnLista);

                // Verifica si la canción está en la lista
                if (index != -1) {
                    // Verifica si hay una canción anterior en la lista
                    if (index > 0) {
                        // Si hay una canción anterior, decrementa el índice
                        index--;
                        return cancionesEnLista.get(index);
                    } else {
                        // Si estamos en la primera canción, vuelve al final de la lista
                        index = cancionesEnLista.size() - 1;
                        return cancionesEnLista.get(index);
                    }
                }
            }
        } else {
            // Llamada al método para reproducir canción aleatoria
            return llamarAleatorio(cancionesEnLista);
        }

        // Retorna null si no se pudo reproducir la canción anterior
        return null;
    }

    public Cancion llamarAleatorio(List<Cancion> cancionesEnLista) {
        // Verifica si la lista de canciones no está vacía
        if (!cancionesEnLista.isEmpty()) {
            int index = (int) (Math.random() * cancionesEnLista.size());
            return cancionesEnLista.get(index);
        }

        // Retorna null si no se pudo reproducir la canción aleatoria
        return null;
    }

    private int obtenerIndiceCancion(Cancion cancion, List<Cancion> cancionesEnLista) {
        // Recorre manualmente la lista para encontrar la posición de la canción
        for (int i = 0; i < cancionesEnLista.size(); i++) {
            if (cancionesEnLista.get(i).getId()==cancion.getId()) {
                return i;
            }
        }
        return -1; // Retorna -1 si la canción no está en la lista
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

