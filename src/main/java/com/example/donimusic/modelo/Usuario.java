package com.example.donimusic.modelo;

import com.example.donimusic.modelo.Conexiones.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario {
    private static Connection c = Conexion.con;
    String nombre;
    String password;

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario() {
    }

    /**
     * Método para crear usuario
     * @return
     */
    public boolean crearUsuario() {
        PreparedStatement stm;
        try {
            //Se inserta en la base de datos los datos del usuario

            stm = c.prepareStatement("insert into usuario values(?,?);");
            stm.setString(1, nombre);
            stm.setString(2, password);
            stm.execute();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.out.println("El nombre de usuario ya existe en la base de datos.");
                return false;
            } else {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Método que devuelve las palylist del usuario
     * @return
     */
    public ArrayList<ListaDeCanciones> obtenerListasUsuario() {
        ArrayList<ListaDeCanciones> arrayListaDeCanciones = new ArrayList<>();
        try {
            String sql = "SELECT * " +
                    "FROM lista " +
                    "JOIN playListUsuarios ON lista.listaId = playListUsuarios.listaId " +
                    "WHERE playListUsuarios.nombreUsuario = ?;";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setString(1, nombre);
                String nombreLista = "", autorLista = "";
                int idLista = 0;

                try (ResultSet resultSet = stm.executeQuery()) {
                    while (resultSet.next()) {

                        autorLista = resultSet.getString("nombreUsuario");
                        nombreLista = resultSet.getString("nombre");
                        idLista = resultSet.getInt("listaId");
                        arrayListaDeCanciones.add(new ListaDeCanciones(idLista, nombreLista, autorLista));

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayListaDeCanciones;
    }

    /**
     * Método para cambiar la contraseña en la base de datos
     * @param contrasena
     */
    public void cambiarContraseña(String contrasena){
        String name=nombre;
        try {
            PreparedStatement stm=c.prepareStatement("update usuario set contraseña=? where nombreUsuario=?;");
            stm.setString(1, contrasena);
            stm.setString(2,name);
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Método para cambiar el nombre de usuario en la base de datos
     * @param usuario
     */
    public void cambiarUsuario(String usuario){
        String name=nombre;
        try {
            PreparedStatement stm=c.prepareStatement("update usuario set nombreUsuario= ? where nombreUsuario=?;");
            stm.setString(1, usuario);
            stm.setString(2,name);
            stm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarCuenta(){
        String name=nombre;
        try {
            PreparedStatement stm=c.prepareStatement("DELETE FROM playListCanciones WHERE listaId IN (SELECT listaId FROM lista WHERE nombreUsuario = ?);");
            stm.setString(1,name);
            stm.execute();
            stm=c.prepareStatement("DELETE FROM playlistusuarios WHERE listaId IN (SELECT listaId FROM lista WHERE nombreUsuario = ?);");
            stm.setString(1,name);
            stm.execute();
            stm=c.prepareStatement("delete from lista where nombreUsuario=?;");
            stm.setString(1,name);
            stm.execute();
            stm=c.prepareStatement("delete from usuario where nombreUsuario=?;");
            stm.setString(1,name);
            stm.execute();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
