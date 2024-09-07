package com.airepur.repository;

import com.airepur.controller.DadesEstacioController;
import com.airepur.domain.DiaQualitatAPIDTO;
import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.NivellDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class DiaQualitatAPIRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private static DadesEstacioController deController = new DadesEstacioController();


    public ArrayList<DiaQualitatAPIDTO> getInfoAPI() {

        ArrayList<DiaQualitatAPIDTO> dqDTO = new ArrayList<>();
        String query = "SELECT * FROM localitzacioestacio WHERE municipi = 'Barcelona'";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DiaQualitatAPIDTO dqNew = new DiaQualitatAPIDTO(deController.getICAactual(resultSet.getString("codiestacio")),
                            resultSet.getFloat("longitud"), resultSet.getFloat("latitud"));

                    dqDTO.add(dqNew);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dqDTO;
    }
}
