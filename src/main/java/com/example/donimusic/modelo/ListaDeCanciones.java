package com.example.donimusic.modelo;

import com.example.donimusic.modelo.Conexiones.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Representa una lista de canciones en un sistema de gestión de reproducción musical.
 */
public class ListaDeCanciones {

    private static Connection c = Conexion.con;
    int id;
    String nombre;
    String nombreCreador;
    private boolean reproductor = false;

    public ListaDeCanciones(int idLista, String nombre, String nombreCreador) {
        this.id = idLista;
        this.nombre = nombre;
        this.nombreCreador = nombreCreador;
    }
    /**
     * Crea una nueva lista de canciones y la asocia con el usuario especificado.
     *
     * @param nombreLista   Nombre de la nueva lista de canciones.
     * @param usuario       Nombre del usuario asociado a la lista.
     */
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

                    establecerRelacionPlayListUsuario(obtenerIdLista(nombreLista, usuario),usuario);
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
    /**
     * Obtiene el ID de la última lista creada por un usuario específico.
     *
     * @param nombreLista   Nombre de la lista de canciones.
     * @param nombreUsuario Nombre del usuario propietario de la lista.
     * @return El ID de la lista de canciones o -1 si no se encuentra.
     */
    public static int obtenerIdLista(String nombreLista, String nombreUsuario) {
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



                // Retornar el ID de la lista
                return idLista;
            } else {
                // Si no se encontró la lista, imprimir un mensaje (puedes ajustar según necesites)
                System.out.println("La lista no fue encontrada en la base de datos para el usuario " + nombreUsuario);
            }
        } catch (SQLException e) {
            // Manejar excepciones
            throw new RuntimeException(e);
        }

        // En caso de que no se haya encontrado la lista, puedes devolver un valor por defecto o lanzar una excepción, según tus necesidades.
        return -1; // Cambia esto según tus necesidades
    }
    /**
     * Establece la relación entre una lista de canciones y un usuario en la tabla playListUsuarios.
     *
     * @param idLista        ID de la lista de canciones.
     * @param nombreUsuario  Nombre del usuario asociado a la lista.
     */
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
    /**
     * Agrega una canción a una lista de canciones.
     *
     * @param idLista    ID de la lista de canciones.
     * @param idCancion  ID de la canción a agregar.
     */
    public static void addCancion(int idLista, int idCancion) {

        try {
            PreparedStatement stm = c.prepareStatement("INSERT INTO playListCanciones (listaId, cancionId) VALUES (?, ?)");
            stm.setInt(1, idLista);
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
    /**
     * Obtiene la lista de canciones asociadas a una lista específica.
     *
     * @param idLista ID de la lista de canciones.
     * @return Una lista de objetos Cancion asociada a la lista.
     */
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
                        int duracion=resultSet.getInt("duracion");

                        Cancion cancion = new Cancion(idCancion, nombre, artista, album, duracion);
                        cancionesEnLista.add(cancion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;
    }
    /**
     * Verifica si una canción está presente en una lista específica.
     *
     * @param idCancion ID de la canción a buscar.
     * @param idLista   ID de la lista de canciones.
     * @return true si la canción está en la lista, false de lo contrario.
     */
    public static boolean encontrarCancion(int idCancion, int idLista) {
        boolean cancionEncontrada = false;
        try {
            String sql = "SELECT 1 FROM cancion c JOIN playListCanciones plc ON c.cancionId = plc.cancionId WHERE c.cancionId  = ? AND plc.listaId = ?";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setInt(1, idCancion);
                stm.setInt(2, idLista);

                try (ResultSet resultSet = stm.executeQuery()) {
                    // Si la consulta devuelve resultados, la canción se encuentra en la lista
                    if (resultSet.next()) {
                        cancionEncontrada = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionEncontrada;
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

    public boolean isReproductor() {
        return reproductor;
    }

    public void setReproductor(boolean reproductor) {
        this.reproductor = reproductor;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }
    /**
     * Elimina la lista de canciones, incluyendo sus relaciones con usuarios y canciones asociadas.
     */
    public void eliminarLista() {
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM playListCanciones WHERE listaId = ?");
            stm.setInt(1, this.id);
            stm.execute();

            stm = c.prepareStatement("DELETE FROM playListUsuarios WHERE listaId = ?");
            stm.setInt(1, this.id);
            stm.execute();

            stm = c.prepareStatement("DELETE FROM lista WHERE listaId = ?");
            stm.setInt(1, this.id);
            stm.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Verifica si una lista de canciones existe en la base de datos.
     *
     * @param idLista ID de la lista de canciones.
     * @return true si la lista existe, false de lo contrario.
     */
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
    /**
     * Elimina una canción específica de una lista de canciones.
     *
     * @param idCancion ID de la canción a eliminar.
     */
    public void eliminarCancion(int idCancion) {
        try {
            PreparedStatement stm = c.prepareStatement("DELETE FROM playListCanciones WHERE listaId = ? AND cancionId = ?");
            stm.setInt(1, id);
            stm.setInt(2, idCancion);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Busca canciones por nombre en una lista de canciones específica.
     *
     * @param nombreCancion Nombre de la canción a buscar.
     * @param idLista       ID de la lista de canciones.
     * @return Una lista de objetos Cancion que coinciden con el nombre.
     */
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
                        int duracion=resultSet.getInt("duracion");

                        Cancion cancion = new Cancion(idCancion, nombre, artista, album,duracion);
                        cancionesEnLista.add(cancion);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cancionesEnLista;
    }
    /**
     * Reproduce la siguiente canción en la lista, considerando el modo de reproducción.
     *
     * @param cancion Canción actual.
     * @return La siguiente canción que se reproducirá.
     */
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
    /**
     * Reproduce la canción anterior en la lista, considerando el modo de reproducción.
     *
     * @param cancion Canción actual.
     * @return La canción anterior que se reproducirá.
     */
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
    /**
     * Reproduce una canción aleatoria de la lista.
     *
     * @param cancionesEnLista Lista de canciones disponibles.
     * @return La canción aleatoria que se reproducirá.
     */
    public Cancion llamarAleatorio(List<Cancion> cancionesEnLista) {
        // Verifica si la lista de canciones no está vacía
        if (!cancionesEnLista.isEmpty()) {
            int index = (int) (Math.random() * cancionesEnLista.size());
            return cancionesEnLista.get(index);
        }

        // Retorna null si no se pudo reproducir la canción aleatoria
        return null;
    }

    /**
     * * Obtiene el índice de una canción específica en la lista de canciones.
     * @param cancion
     * @param cancionesEnLista
     * @return
     */
    private int obtenerIndiceCancion(Cancion cancion, List<Cancion> cancionesEnLista) {
        // Recorre manualmente la lista para encontrar la posición de la canción
        for (int i = 0; i < cancionesEnLista.size(); i++) {
            if (cancionesEnLista.get(i).getId() == (cancion.getId())) {
                return i;
            }
        }
        return -1; // Retorna -1 si la canción no está en la lista
    }

    /**
     * Se cambia el nombre de la lista por el que es pasado por parametro
     * @param nuevoNombre
     */
    public void cambiarNombreLista(String nuevoNombre){
        try {
            PreparedStatement stm=c.prepareStatement("update lista set nombre= ? where listaId=?;");
            stm.setString(1, nuevoNombre);
            stm.setInt(2,id);
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cambiarOrdenRep() {
        if (this.reproductor == true) {
            this.reproductor = false;
        } else if (this.reproductor == false) {
            this.reproductor = true;
        }
        return this.reproductor;
    }
    /**
     * Muestra la información de las canciones en la lista.
     *
     * @param idLista Identificador de la lista de canciones.
     */
    public void mostrarListaDeCanciones(int idLista) {
        List<Cancion> cancionesEnLista = obtenerCancionesEnLista(idLista);


        for (Cancion cancion : cancionesEnLista) {
            System.out.println("ID: " + cancion.getId() +
                    ", Nombre: " + cancion.getNombre() +
                    ", Artista: " + cancion.getNombreArtista() +
                    ", Album: " + cancion.getAlbum());
        }
    }


}

