package com.airepur.repository;

import com.airepur.domain.AssolimentDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class AssolimentRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    public ArrayList<AssolimentDTO> getAssoliments() {

        ArrayList<AssolimentDTO> assoliments = new ArrayList<>();
        String query = "SELECT * FROM assoliment";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssolimentDTO newAssoliment = new AssolimentDTO(resultSet.getString("noma"), resultSet.getString("descripcio"));
                    assoliments.add(newAssoliment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assoliments;
    }

    public AssolimentDTO getAssoliment(String noma){

        String query = "SELECT * FROM assoliment a WHERE a.noma = ?";
        AssolimentDTO newAssoliment = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, noma);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    newAssoliment = new AssolimentDTO(resultSet.getString("noma"), resultSet.getString("descripcio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newAssoliment;
    }
}
