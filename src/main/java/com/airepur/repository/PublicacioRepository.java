package com.airepur.repository;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.PreguntaDTO;
import com.airepur.domain.PublicacioDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;



@Repository
public class PublicacioRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    public ArrayList<PublicacioDTO> getPublicacions() throws SQLException {
        ArrayList<PublicacioDTO> pubs = new ArrayList<>();
        String query = "SELECT * FROM publicacio ORDER BY fecha DESC, hora DESC";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    UsuariRepository ur = new UsuariRepository();
                    PublicacioDTO p = new PublicacioDTO(resultSet.getString("idpublicacio"), resultSet.getString("contingut"),
                            resultSet.getString("imatge"), resultSet.getString("fecha"), resultSet.getString("hora"),
                            ur.getUsuari(resultSet.getString("username")), resultSet.getString("urlAvatar"));
                    p.setAnswered(getPublicacio(resultSet.getString("idanswered")));
                    pubs.add(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pubs;
        }
    }

    public PublicacioDTO getPublicacio(String id) throws SQLException {
        String query = "SELECT * FROM publicacio WHERE idPublicacio = ?";
        PublicacioDTO p = new PublicacioDTO();

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UsuariRepository ur = new UsuariRepository();
                    p = new PublicacioDTO(resultSet.getString("idpublicacio"), resultSet.getString("contingut"),
                            resultSet.getString("imatge"), resultSet.getString("fecha"), resultSet.getString("hora"),
                            ur.getUsuari(resultSet.getString("username")),resultSet.getString("urlAvatar"));
                    p.setAnswered(getPublicacio(resultSet.getString("idanswered")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return p;
        }
    }

    public void savePublicacio(String username, String contingut, String imatge, String fecha, String hora, String idPublicacio, String idanswered, String urlAvatar) {
        if (idanswered == null){
            String query = "INSERT INTO publicacio values (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, idPublicacio);
                statement.setString(2, contingut);
                statement.setString(3, imatge);
                statement.setString(4, username);
                statement.setString(5, fecha);
                statement.setString(6, hora);
                statement.setString(7, null);
                statement.setString(8, urlAvatar);

                Integer correct = statement.executeUpdate();
                if (correct > 0) {
                    System.out.println("Publicacio amb codi: " + idPublicacio + " introduida amb exit.");
                } else {
                    System.out.println("Error al insertar LocalitzacioEstacio.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            String query = "INSERT INTO publicacio values (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, idPublicacio);
                statement.setString(2, contingut);
                statement.setString(3, imatge);
                statement.setString(4, username);
                statement.setString(5, fecha);
                statement.setString(6, hora);
                statement.setString(7, idanswered);
                statement.setString(8, urlAvatar);

                Integer correct = statement.executeUpdate();
                if (correct > 0) {
                    System.out.println("Publicacio amb codi: " + idPublicacio + " introduida amb exit.");
                } else {
                    System.out.println("Error al insertar Publicació.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deletePublicacio(String id) throws SQLException {
        String query = "DELETE FROM publicacio WHERE idPublicacio = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);

            Integer correct = statement.executeUpdate();
            if (correct > 0) {
                System.out.println("Publicacio amb codi: " + id + " eliminada amb exit.");
            } else {
                System.out.println("Error al eliminar Publicació.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}