package com.example.donimusic.modelo;

import com.example.donimusic.controlador.Home;

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

    public boolean crearUsuario() {
        PreparedStatement stm;
        try {

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

    public ArrayList<ListaDeCanciones> obtenerListasUsuario() {
        ArrayList<ListaDeCanciones> arrayListaDeCanciones = new ArrayList<>();
        try {
            String sql = "SELECT * " +
                    "FROM lista " +
                    "JOIN playListUsuarios ON lista.listaId = playListUsuarios.listaId " +
                    "WHERE playListUsuarios.nombreUsuario = ?;";
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setString(1, Home.usuario.getNombre());
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
