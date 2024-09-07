package com.airepur.repository;

import com.airepur.domain.ReportDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.service.MailService;
import com.airepur.utils.Config;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class ReportRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private ReportDTO rDTO = null;
    private void AssignReportObject(ResultSet resultSet) throws SQLException{
        rDTO = new ReportDTO(resultSet.getInt("idreport"), resultSet.getString("missatge"),
                resultSet.getString("fecha"), resultSet.getString("hora"), resultSet.getString("usernamereportat"),
                resultSet.getString("usernamereportador"), resultSet.getString("idpublicacio"));
    }
    public String saveReport(String missatge, String fecha, String hora, String usuariReportador, String usuariReportat, String idpublicacio) {
        String query = "INSERT INTO report (missatge, fecha, hora, usernamereportador, usernamereportat, idpublicacio) VALUES (?, ?, ?, ?, ?, ?)";
        String validacion = "ok";
        System.out.println(validacion+ "1");
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, missatge);
            statement.setString(2, fecha);
            statement.setString(3, hora);
            statement.setString(4, usuariReportador);
            statement.setString(5, usuariReportat);
            statement.setString(6, idpublicacio);
            System.out.println(validacion+ "2");


            statement.executeUpdate();
            System.out.println(validacion+ "3");
        } catch (PSQLException e) {
            if ("23503".equals(e.getSQLState())) {
                String detalle = e.getServerErrorMessage().getDetail();
                if (detalle.contains("usernamereportat")) {
                    validacion = "usernamereportat";
                } else if (detalle.contains("usernamereportador")) {
                    validacion = "usernamereportador";
                }
                else{
                    validacion = "error";
                }
            } else {
                e.printStackTrace();
                validacion = "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            validacion = "error";
        }
        return validacion;
    }

    public ArrayList<ReportDTO> getReports() {
        ArrayList<ReportDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM report";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignReportObject(resultSet);
                    ja.add(rDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ja;
    }


    public boolean validToken(String token) {
        String query = "SELECT u.administrador FROM usuari u WHERE u.token = ?";
        boolean admin = false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,token);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    admin = resultSet.getBoolean("administrador");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            admin = false;
        }
        return admin;
    }

    public String deleteReport(String idreport) {
        String query = "DELETE FROM report WHERE idreport = ?";
        String validar = "ok";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, idreport);
            int rows = statement.executeUpdate();
            if(rows <= 0) validar = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            validar = "error";
        }
        return validar;
    }

    public String deleteUser(String username, String text) {
        //buscar el correro del usuario
        String email = seachCorreo(username);
        //enviar correo
        MailService correo = new MailService();
        correo.deleteUser(email, text);
        //borrar cuanta
        String query = "DELETE FROM usuari WHERE username = ?";
        String validar = "ok";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,username);
            int rows = statement.executeUpdate();
            if(rows <= 0) validar= "error";
        } catch (SQLException e) {
            e.printStackTrace();
            validar = "error";
        }
        return validar;
    }

    private String seachCorreo(String username) {
        String query = "SELECT u.email FROM usuari u WHERE u.username = ?";
        String email = "no existe";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    email = resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            email = "error";
        }
        return email;
    }

    public String deletePublicacio(String username, String text, String pub) {
        //buscar el correro del usuario
        String email = seachCorreo(username);
        //enviar correo
        MailService correo = new MailService();
        correo.deletePost(email);
        //borrar publicacio
        System.out.println("si");
        String query = "DELETE FROM publicacio WHERE idpublicacio = ?";
        String validar = "ok";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pub);
            int rows = statement.executeUpdate();
            if(rows <= 0) validar= "error";
        } catch (SQLException e) {
            e.printStackTrace();
            validar = "error";
        }
        return validar;
    }
}