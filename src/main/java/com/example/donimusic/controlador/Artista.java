package com.example.donimusic.controlador;

import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Artista {
    String artista;
    int numCanciones;

    private Connection conexion;

    public Artista(String artista, int numCanciones) {
        this.artista = artista;
        this.numCanciones = numCanciones;
    }

    public void subirCancion(String cancionNombre, Blob cancion) {
        String sql = "INSERT INTO cancion (nombreCancion, archivo, artista) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, cancionNombre);
            pstmt.setBlob(2, cancion);
            pstmt.setString(3, artista);


            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void eliminarCancion(){

    }
    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getNumCanciones() {
        return numCanciones;
    }

    public void setNumCanciones(int numCanciones) {
        this.numCanciones = numCanciones;
    }
}
