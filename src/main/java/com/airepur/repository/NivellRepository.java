package com.airepur.repository;

import com.airepur.domain.NivellDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class NivellRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    public ArrayList<NivellDTO> getNivells() {

        ArrayList<NivellDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM nivell";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    NivellDTO nDTO = new NivellDTO(resultSet.getInt("nivell"), resultSet.getInt("puntuacionivell"));
                    ja.add(nDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ja;
    }

    public NivellDTO getNivell(Integer nivell) {

        NivellDTO nDTO = null;
        String query = "SELECT * FROM nivell n WHERE n.nivell = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nivell);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    nDTO = new NivellDTO(resultSet.getInt("nivell"), resultSet.getInt("puntuacionivell"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nDTO;
    }
}
