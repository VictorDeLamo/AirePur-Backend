package com.airepur.repository;

import com.airepur.domain.PreguntaDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class PreguntaRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    Random random = new Random();

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    public ArrayList<PreguntaDTO> getPreguntes(String idioma) throws SQLException {
        ArrayList<PreguntaDTO> pregs = new ArrayList<>();
        String query = "SELECT * FROM pregunta WHERE idioma = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, idioma);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pregs.add(new PreguntaDTO(resultSet.getInt("nump"), resultSet.getString("contingut"), "privat",
                            resultSet.getString("opcioa"), resultSet.getString("opciob"), resultSet.getString("opcioc"),
                            resultSet.getString("opciod")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pregs;
        }
    }

    public PreguntaDTO getPregunta(Integer nump) throws SQLException {
        PreguntaDTO preg = null;
        String query = "SELECT * FROM pregunta WHERE nump = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nump);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    preg = new PreguntaDTO(resultSet.getInt("nump"), resultSet.getString("contingut"), "privat",
                            resultSet.getString("opcioa"), resultSet.getString("opciob"), resultSet.getString("opcioc"),
                            resultSet.getString("opciod"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preg;
    }

    public boolean esPreguntaCorrecta(Integer nump, Integer opcioMarcada) throws SQLException {
        boolean esCorrecta = false;
        String query = "SELECT * FROM pregunta WHERE nump = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nump);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getInt("correcta") == opcioMarcada + 1) {
                    esCorrecta = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return esCorrecta;
    }

    public ArrayList<PreguntaDTO> getPreguntesRandoms(String idioma) throws SQLException {
        ArrayList<PreguntaDTO> pregs = getPreguntes(idioma);
        Integer pregsSize = pregs.size();

        ArrayList<PreguntaDTO> pDTO = new ArrayList<>();

        if (pregsSize == 0) {
            System.out.println("Error: No hi ha preguntes");
            return new ArrayList<>();
        }

        List<Integer> numerosAleatorios = new ArrayList<>();
        while (numerosAleatorios.size() < 3) {
            int numeroAleatorio = random.nextInt(pregsSize);
            if (!numerosAleatorios.contains(numeroAleatorio)) {
                numerosAleatorios.add(numeroAleatorio);
            }
        }

        for (Integer numerosAleatorio : numerosAleatorios) {
            pDTO.add(pregs.get(numerosAleatorio));
        }

        return pDTO;
    }
}
