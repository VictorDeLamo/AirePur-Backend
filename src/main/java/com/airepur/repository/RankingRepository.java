package com.airepur.repository;

import com.airepur.domain.InfoComunitatDTO;
import com.airepur.domain.RankingComunitatDTO;
import com.airepur.domain.RankingUsuariDTO;
import com.airepur.utils.Config;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class RankingRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private RankingUsuariDTO ruDTO = null;
    private RankingComunitatDTO rcDTO = null;
    private InfoComunitatDTO icDTO = null;

    private void AssignRankingUserObject(ResultSet resultSet) throws SQLException {
        ruDTO = new RankingUsuariDTO(resultSet.getString("username"),resultSet.getInt("puntuaciototal"),resultSet.getInt("posicion"));
    }
    private void AssignRankingComunitatObject(ResultSet resultSet) throws SQLException {
        rcDTO = new RankingComunitatDTO(resultSet.getString("municipi"),resultSet.getInt("puntuacion"));
    }

    private void AssignInfoComunitatObject(ResultSet resultSet) throws SQLException {
        icDTO = new InfoComunitatDTO(resultSet.getString("codiestacio"),resultSet.getString("municipi"),resultSet.getFloat("longitud"), resultSet.getFloat("latitud"),resultSet.getInt("nivell"));
    }

    public RankingUsuariDTO infoUser(String username){
        JSONObject res = new JSONObject();
        String query = "SELECT * FROM ranking_usuarios u WHERE u.username = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignRankingUserObject(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ruDTO;
    }
    public ArrayList<RankingUsuariDTO> rankingUsers() {
        ArrayList<RankingUsuariDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM ranking_usuarios LIMIT 100";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignRankingUserObject(resultSet);
                    ja.add(ruDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ja;
    }
    private void refreshView(){
        String query = "REFRESH MATERIALIZED VIEW ranking_comunitas";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RankingComunitatDTO> rankingComunitats() {
        ArrayList<RankingComunitatDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM ranking_comunitas";
        refreshView();
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignRankingComunitatObject(resultSet);
                    ja.add(rcDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ja;
    }

    public ArrayList<InfoComunitatDTO> infoComunitat() {
        ArrayList<InfoComunitatDTO> ja = new ArrayList<>();
        String query = "SELECT ru.codiestacio, ROUND(AVG(ru.nivell)) AS nivell, l.longitud, l.latitud, l.municipi FROM ranking_usuarios ru JOIN localitzacioestacio l ON ru.codiestacio = l.codiestacio GROUP BY ru.codiestacio, l.longitud, l.latitud, l.municipi;";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignInfoComunitatObject(resultSet);
                    ja.add(icDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ja;
    }
}
