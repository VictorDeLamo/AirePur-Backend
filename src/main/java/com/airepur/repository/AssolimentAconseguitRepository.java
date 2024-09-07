package com.airepur.repository;

import com.airepur.domain.AssolimentAconseguitDTO;
import com.airepur.domain.AssolimentDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AssolimentAconseguitRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;
    public ArrayList<AssolimentAconseguitDTO> getAssoliments() {
        ArrayList<AssolimentAconseguitDTO> assoliments = new ArrayList<>();
        String query = "SELECT * FROM assolimentaconseguit";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssolimentAconseguitDTO newAssoliment = new AssolimentAconseguitDTO(resultSet.getString("username"), resultSet.getString("noma"));
                    assoliments.add(newAssoliment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assoliments;
    }

    public AssolimentAconseguitDTO getAssoliment(String username, String noma) {
        String query = "SELECT * FROM assolimentaconseguit a WHERE a.noma = ? and a.username = ?";
        AssolimentAconseguitDTO newAssoliment = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, noma);
            statement.setString(2, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    newAssoliment = new AssolimentAconseguitDTO(resultSet.getString("username"), resultSet.getString("noma"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newAssoliment;
    }

    public ArrayList<AssolimentAconseguitDTO> getAssolimentsUsuari(String username) {
        ArrayList<AssolimentAconseguitDTO> assoliments = new ArrayList<>();
        String query = "SELECT * FROM assolimentaconseguit a WHERE a.username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssolimentAconseguitDTO newAssoliment = new AssolimentAconseguitDTO(resultSet.getString("username"), resultSet.getString("noma"));
                    assoliments.add(newAssoliment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assoliments;
    }

    public int[] getAssolimentsListUsuari(String username) {
        AssolimentRepository ar = new AssolimentRepository();
        ArrayList<AssolimentDTO> as = ar.getAssoliments();
        ArrayList<AssolimentAconseguitDTO> ac = getAssolimentsUsuari(username);

        int[] result = new int[as.size()];

        Set<String> aconseguitsNomASet = new HashSet<>();
        for (AssolimentAconseguitDTO aconseguit : ac) {
            aconseguitsNomASet.add(aconseguit.getNomA());
        }

        for (int i = 0; i < as.size(); i++) {
            AssolimentDTO assoliment = as.get(i);
            if (aconseguitsNomASet.contains(assoliment.getNom())) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }

        return result;
    }


    public void saveAssolimentAconseguit(String username, String noma) {
        String query = "INSERT INTO assolimentaconseguit values (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, noma);

            Integer correct = statement.executeUpdate();
            if (correct > 0) {
                System.out.println("Assoliment: " + noma + " aconseguit per l'usuari: " + username);
            } else {
                System.out.println("Error al insertar assolimentaconseguit.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
