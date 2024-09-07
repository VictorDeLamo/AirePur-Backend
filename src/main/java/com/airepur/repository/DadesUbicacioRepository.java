package com.airepur.repository;

import com.airepur.domain.DadesUbicacioDTO;
import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class DadesUbicacioRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private static final UsuariRepository u = new UsuariRepository();
    private static final LocalitzacioEstacioRepository l = new LocalitzacioEstacioRepository();
    private static final DadesEstacioRepository d = new DadesEstacioRepository();
    private DadesUbicacioDTO duDTO = null;
    private UsuariDTO uDTO = null;
    private LocalitzacioEstacioDTO lDTO = null;

    private void AssignDadesUbicacioObject(ResultSet resultSet) throws SQLException {
        uDTO = new UsuariDTO();
        lDTO = new LocalitzacioEstacioDTO();
        uDTO.setUsername(resultSet.getString("username"));
        lDTO.setCodiEstacio(resultSet.getString("codiestacio"));
        duDTO = new DadesUbicacioDTO(uDTO, lDTO, resultSet.getString("fecha"), resultSet.getString("hora"));
        duDTO.setQualitatAire(resultSet.getInt("qualitataire"));
        duDTO.setMunicipi(resultSet.getString("municipi"));
    }

    //Retorna totes les dadesUbicacio
    public ArrayList<DadesUbicacioDTO> getDadesUbicacions() {
        ArrayList<DadesUbicacioDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM dadesubicacio";

        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignDadesUbicacioObject(resultSet);
                    ja.add(duDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ja;
    }

    public DadesUbicacioDTO getInfoData(String username, String fecha) {
        ArrayList<DadesUbicacioDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM dadesubicacio d WHERE d.username = ? AND d.fecha = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, fecha);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DadesUbicacioDTO newDuDTO;
                    UsuariDTO newUsuari = new UsuariDTO();
                    LocalitzacioEstacioDTO localest = new LocalitzacioEstacioDTO();
                    newUsuari.setUsername(username);
                    localest.setCodiEstacio(resultSet.getString("codiestacio"));
                    newDuDTO = new DadesUbicacioDTO(newUsuari, localest, resultSet.getString("fecha"), resultSet.getString("hora"));
                    newDuDTO.setQualitatAire(resultSet.getInt("qualitataire"));
                    newDuDTO.setMunicipi(resultSet.getString("municipi"));
                    ja.add(newDuDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(ja, Comparator.comparing(DadesUbicacioDTO::getMunicipi));
        Integer maxMunicipis = 0;
        Integer index = 0;
        Integer indexDTO = 0;
        Integer ICAMaxim = 0;
        for (int i = index; i < ja.size(); i+=index) {
            String municipi = ja.get(i).getMunicipi();
            Integer ICA = 0;
            Integer cont = 0;

            while (index < ja.size() && ja.get(index).getMunicipi().equals(municipi)) {
                ++cont;
                ICA += ja.get(index).getQualitatAire();
                ++index;
            }
            ICA /= Math.max(cont,1);
            if (cont >= maxMunicipis && ICA > ICAMaxim) {
                maxMunicipis = cont;
                ICAMaxim = ICA;
                indexDTO = index;
            }
        }
        ja.get(indexDTO-1).setQualitatAire(ICAMaxim);
        return ja.get(indexDTO-1);

    }

    public ArrayList<DadesUbicacioDTO> getDadesUbicacioHistoric(String username) {

        ArrayList<DadesUbicacioDTO> dadesUbicacioUsuari = new ArrayList<>();
        String query = "SELECT * FROM dadesubicacio d WHERE d.username = ? AND TO_DATE(d.fecha, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '1 week' AND CURRENT_DATE";
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DadesUbicacioDTO newDuDTO = new DadesUbicacioDTO();
                    UsuariDTO newUsuari = new UsuariDTO();
                    LocalitzacioEstacioDTO localest = new LocalitzacioEstacioDTO();
                    newUsuari.setUsername(username);
                    localest.setCodiEstacio(resultSet.getString("codiestacio"));
                    newDuDTO = new DadesUbicacioDTO(newUsuari, localest, resultSet.getString("fecha"), resultSet.getString("hora"));
                    newDuDTO.setQualitatAire(resultSet.getInt("qualitataire"));
                    newDuDTO.setMunicipi(resultSet.getString("municipi"));
                    dadesUbicacioUsuari.add(newDuDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<DadesUbicacioDTO> dadesUbicacioInfoData = new ArrayList<>();
        Set<String> municipisUnics = new HashSet<>();

        for (int i = 0; i < dadesUbicacioUsuari.size(); ++i) {
            DadesUbicacioDTO duDTO = dadesUbicacioUsuari.get(i);
            DadesUbicacioDTO newduDTO = getInfoData(username, duDTO.getFecha());
            if (!municipisUnics.contains(newduDTO.getMunicipi())) {
                municipisUnics.add(newduDTO.getMunicipi());
                dadesUbicacioInfoData.add(newduDTO);
            }
        }
        return dadesUbicacioInfoData;
    }

    //Guarda la nova dadesUbicacio
    public boolean saveDadesUbicacio (String longitud, String latitud, String municipi, String
            username, String fecha, String hora) throws SQLException {

        String query = "INSERT INTO dadesubicacio values (?, ?, ?, ?, ?, ?)";
        LocalitzacioEstacioDTO lDTO = l.getLocalitzacioCoordenades(Float.parseFloat(longitud), Float.parseFloat(latitud));
        Integer ICA = d.getICAactual(lDTO.getCodiEstacio());
        try (Connection connection = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setInt(2, ICA);
            statement.setString(3, lDTO.getCodiEstacio());
            statement.setString(4, fecha);
            statement.setString(5, hora);
            statement.setString(6, municipi);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Insert correcte: " + username + " " + fecha + " " + hora);
        return false;
    }
}