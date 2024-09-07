package com.airepur.repository;

import com.airepur.domain.NivellDTO;
import com.airepur.domain.PlantaDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public class PlantaRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private static final UsuariRepository usuariRepository = new UsuariRepository();
    private static final NivellRepository nivellRepository = new NivellRepository();

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    public ArrayList<PlantaDTO> getPlantes() {
        ArrayList<PlantaDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM planta";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlantaDTO pDTO = new PlantaDTO(resultSet.getString("lastanswer"), resultSet.getInt("puntuacioalnivell"),
                            resultSet.getInt("puntuaciototal"), nivellRepository.getNivell(resultSet.getInt("nivell")),
                            usuariRepository.getUsuari(resultSet.getString("username")), resultSet.getInt("consecutive_days"));
                    ja.add(pDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ja;
    }

    public String toPlant(String username) {
        String query = "INSERT INTO planta VALUES (?, ?, ?, ?, ?, ?)";
        String answer = "ok";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 1);
            statement.setString(2, username);
            statement.setString(3, "never");
            statement.setInt(4, 0);
            statement.setInt(5, 0);
            statement.setInt(6, 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            answer = "error";
        }
        return answer;
    }

    public PlantaDTO getPlantaUsuari(String username) {
        PlantaDTO pDTO = null;
        String query = "SELECT * FROM planta p WHERE p.username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pDTO = new PlantaDTO(resultSet.getString("lastanswer"), resultSet.getInt("puntuacioalnivell"),
                            resultSet.getInt("puntuaciototal"), nivellRepository.getNivell(resultSet.getInt("nivell")),
                            usuariRepository.getUsuari(resultSet.getString("username")), resultSet.getInt("consecutive_days"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pDTO;
    }

    public String updateDataPlanta(String username, Integer puntuacio) {
        PlantaDTO pDTO = getPlantaUsuari(username);
        NivellDTO nDTO = pDTO.getNivell();
        Integer sumaPuntuacioTotal = pDTO.getPuntuacioTotal() + puntuacio;
        Integer cd;
        String today = LocalDate.now().toString();
        if (Objects.equals(pDTO.getLastAnswer(), LocalDate.now().minusDays(1).toString())) {
            cd = pDTO.getConsecutivedays() + 1;
        } else {
            cd = 1;
        }

        AssolimentAconseguitRepository ar = new AssolimentAconseguitRepository();
        if (cd == 2) {
            ar.saveAssolimentAconseguit(username, "2 seguits");
        } else if (cd == 3) {
            ar.saveAssolimentAconseguit(username, "3 seguits");
        } else if (cd == 4) {
            ar.saveAssolimentAconseguit(username, "4 seguits");
        } else if (cd == 5) {
            ar.saveAssolimentAconseguit(username, "5 seguits");
        } else if (cd == 6) {
            ar.saveAssolimentAconseguit(username, "6 seguits");
        } else if (cd == 7) {
            ar.saveAssolimentAconseguit(username, "7 seguits");
        } else if (cd == 8) {
            ar.saveAssolimentAconseguit(username, "8 seguits");
        } else if (cd == 9) {
            ar.saveAssolimentAconseguit(username, "9 seguits");
        } else if (cd == 10) {
            ar.saveAssolimentAconseguit(username, "10 seguits");
        } else if (cd == 30) {
            ar.saveAssolimentAconseguit(username, "30 seguits");
        } else if (cd == 50) {
            ar.saveAssolimentAconseguit(username, "50 seguits");
        } else if (cd == 100) {
            ar.saveAssolimentAconseguit(username, "100 seguits");
        } else if (cd == 200) {
            ar.saveAssolimentAconseguit(username, "200 seguits");
        } else if (cd == 365) {
            ar.saveAssolimentAconseguit(username, "365 seguits");
        }

        String answer = "ok";

        if (nDTO.getPuntuacioNivell() <= puntuacio + pDTO.getPuntuacioAlNivell()) {
            if (nDTO.getNivell() + 1 == 2) {
                ar.saveAssolimentAconseguit(username, "Novell");
            } else if (nDTO.getNivell() + 1 == 3) {
                ar.saveAssolimentAconseguit(username, "Principiant");
            } else if (nDTO.getNivell() + 1 == 4) {
                ar.saveAssolimentAconseguit(username, "Aprenent");
            } else if (nDTO.getNivell() + 1 == 5) {
                ar.saveAssolimentAconseguit(username, "Aficionat");
            } else if (nDTO.getNivell() + 1 == 6) {
                ar.saveAssolimentAconseguit(username, "Experimentat");
            } else if (nDTO.getNivell() + 1 == 7) {
                ar.saveAssolimentAconseguit(username, "Destacat");
            } else if (nDTO.getNivell() + 1 == 8) {
                ar.saveAssolimentAconseguit(username, "Expert");
            } else if (nDTO.getNivell() + 1 == 9) {
                ar.saveAssolimentAconseguit(username, "Mestre");
            } else if (nDTO.getNivell() + 1 == 10) {
                ar.saveAssolimentAconseguit(username, "Llegenda");
            }

            String query = "UPDATE planta SET nivell = ?, lastanswer = ?, puntuaciototal = ?, puntuacioalnivell = ?, consecutive_days = ? WHERE username = ?";

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, nDTO.getNivell() + 1);
                statement.setString(2, today);
                statement.setInt(3, sumaPuntuacioTotal);
                statement.setInt(4, (puntuacio + pDTO.getPuntuacioAlNivell()) - nDTO.getPuntuacioNivell());
                statement.setInt(5, cd);
                statement.setString(6, username);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                answer = "error";
            }
        } else {

            String query = "UPDATE planta SET lastanswer = ?, puntuaciototal = ?, puntuacioalnivell = ?, consecutive_days = ? WHERE username = ?";

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, today);
                statement.setInt(2, sumaPuntuacioTotal);
                statement.setInt(3, puntuacio + pDTO.getPuntuacioAlNivell());
                statement.setInt(4, cd);
                statement.setString(5, username);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                answer = "error";
            }
        }
        return answer;
    }
}
