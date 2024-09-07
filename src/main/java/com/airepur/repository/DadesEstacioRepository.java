package com.airepur.repository;

import com.airepur.domain.DadesEstacioDTO;
import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.SuggestedPlaceDTO;
import com.airepur.service.CalulICAService;
import com.airepur.utils.Config;
import com.airepur.utils.SuggestedPlacesComparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

@Repository
public class DadesEstacioRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private static final LocalitzacioEstacioRepository l = new LocalitzacioEstacioRepository();
    private DadesEstacioDTO deDTO = null;
    private LocalitzacioEstacioDTO leDTO = null;

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    protected void AssignDadesEstacioObject(ResultSet resultSet) throws SQLException {
        leDTO = new LocalitzacioEstacioDTO();
        deDTO = new DadesEstacioDTO(leDTO, resultSet.getString("fecha"),
                resultSet.getString("contaminant"), resultSet.getString("unitat"));
        leDTO.setCodiEstacio(resultSet.getString("codiestacio"));
        deDTO.setQuantitat(resultSet.getFloat("quantitat"));
        deDTO.setH01(resultSet.getFloat("h01"));
        deDTO.setH02(resultSet.getFloat("h02"));
        deDTO.setH03(resultSet.getFloat("h03"));
        deDTO.setH04(resultSet.getFloat("h04"));
        deDTO.setH05(resultSet.getFloat("h05"));
        deDTO.setH06(resultSet.getFloat("h06"));
        deDTO.setH07(resultSet.getFloat("h07"));
        deDTO.setH08(resultSet.getFloat("h08"));
        deDTO.setH09(resultSet.getFloat("h09"));
        deDTO.setH10(resultSet.getFloat("h10"));
        deDTO.setH11(resultSet.getFloat("h11"));
        deDTO.setH12(resultSet.getFloat("h12"));
        deDTO.setH13(resultSet.getFloat("h13"));
        deDTO.setH14(resultSet.getFloat("h14"));
        deDTO.setH15(resultSet.getFloat("h15"));
        deDTO.setH16(resultSet.getFloat("h16"));
        deDTO.setH17(resultSet.getFloat("h17"));
        deDTO.setH18(resultSet.getFloat("h18"));
        deDTO.setH19(resultSet.getFloat("h19"));
        deDTO.setH20(resultSet.getFloat("h20"));
        deDTO.setH21(resultSet.getFloat("h21"));
        deDTO.setH22(resultSet.getFloat("h22"));
        deDTO.setH23(resultSet.getFloat("h23"));
        deDTO.setH24(resultSet.getFloat("h24"));
    }

    //Retorna totes les dades
    public ArrayList<DadesEstacioDTO> getDadesEstacions() throws SQLException {
        ArrayList<DadesEstacioDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM dadesestacio";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignDadesEstacioObject(resultSet);
                    ja.add(deDTO);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ja;
        }
    }

    //Retorna un contaminant concret i les seves mesures d'una estació en una data
    public DadesEstacioDTO getDadesEstacioPK(String codiEstacio, String fecha, String contaminant) {
        String query = "SELECT * FROM dadesestacio d WHERE d.codiestacio = ? AND d.fecha = ? AND d.contaminant = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codiEstacio);
            statement.setString(2, fecha);
            statement.setString(3, contaminant);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignDadesEstacioObject(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deDTO;
    }

    //Retorna tots els contaminants d'una estació el dia actual (anterior per motius d'actualització de les dadesobertes)
    public ArrayList<DadesEstacioDTO> getDadesEstacioLocalitzacioAvui(String codiestacio) throws SQLException {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        String data = yesterday + "T00:00:00.000";
        //String data = "2024-03-18T00:00:00.000";

        ArrayList<DadesEstacioDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM dadesestacio d WHERE d.codiestacio = ? AND d.fecha = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codiestacio);
            statement.setString(2, data);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getFloat("quantitat") != -1.0f) {
                        AssignDadesEstacioObject(resultSet);
                        ja.add(deDTO);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ja;
    }

    //Retorna tots els contaminants d'una estació des de fecha al dia actual (anterior per motius d'actualització de les dadesobertes)
    public ArrayList<DadesEstacioDTO> getDadesEstacioLocalitzacioDesdeData(String codiEstacio, String fecha, String contaminant) {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        String data_ahir = yesterday + "T00:00:00.000";

        ArrayList<DadesEstacioDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM dadesestacio d WHERE d.codiestacio = ? AND d.contaminant = ? AND TO_TIMESTAMP( d.fecha , 'YYYY-MM-DD\"T\"HH24:MI:SS.MS') >= TO_TIMESTAMP( ?, 'YYYY-MM-DD')\n" +
                "AND TO_TIMESTAMP(d.fecha, 'YYYY-MM-DD\"T\"HH24:MI:SS.MS') <= TO_TIMESTAMP(?, 'YYYY-MM-DD') ORDER BY d.fecha DESC;";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codiEstacio);
            statement.setString(2, contaminant);
            statement.setString(3, fecha);
            statement.setString(4, data_ahir);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getFloat("quantitat") != -1.0f) {
                        AssignDadesEstacioObject(resultSet);
                        ja.add(deDTO);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ja;
    }

    //Retorna el ICA actual de la estació codiEstacio
    public Integer getICAactual(String codiEstacio) throws SQLException {
        CalulICAService c = new CalulICAService();
        ArrayList<DadesEstacioDTO> ja;
        ja = getDadesEstacioLocalitzacioAvui(codiEstacio);
        float ICA = -1;
        for (int i = 0; i < ja.size(); ++i) {
            String contaminant = ja.get(i).getContaminant();
            float icaC = -1.0f;
            if (contaminant.equals("PM10") || contaminant.equals("PM2.5") || contaminant.equals("O3")) {
                icaC = c.calcularSubindiceParaContaminante(contaminant, ja.get(i).getQuantitat());
            } else if (contaminant.equals("NO2") || contaminant.equals("SO2") || contaminant.equals("CO") ||
                    contaminant.equals("C6H6") || contaminant.equals("H2S")) {
                for (int j = 1; j < 25; ++j) {
                    try {
                        Method method = ja.get(i).getClass().getMethod("getH" + (j < 10 ? "0" + j : j));
                        Float value = (Float) method.invoke(ja.get(i));
                        float f = c.calcularSubindiceParaContaminante(contaminant, value);
                        if (f > icaC) icaC = f;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (ICA < icaC) ICA = icaC;
        }
        return Math.round(ICA);
    }

    //Retorna la llista de Localitzacions més properes ordenades en funcio de la seva qualitat ICA
    public ArrayList<SuggestedPlaceDTO> getSuggestedplaces(float longitud, float latitud) throws SQLException, JSONException {
        LocalitzacioEstacioRepository les = new LocalitzacioEstacioRepository();
        JSONArray listLE = les.getLocalitzacionsProx(longitud, latitud);
        ArrayList<SuggestedPlaceDTO> result = new ArrayList<>();
        String ciutat;
        int ica = 0;
        Float distancia;

        int x = 0;
        for (int i = 0; i < 5 + x && i < listLE.length(); ++i) {
            ciutat = listLE.getJSONObject(i).getString("municipi");
            ica = getICAactual(listLE.getJSONObject(i).getString("codi"));
            distancia = Float.parseFloat(listLE.getJSONObject(i).getString("distancia"));
            if (ica != -1) {
                SuggestedPlaceDTO suggestedPlace = new SuggestedPlaceDTO(ciutat, ica, distancia);
                result.add(suggestedPlace);
            } else {
                if (x < 10) {
                    x += 1;
                }
            }
        }
        Collections.sort(result, new SuggestedPlacesComparator());
        return result;
    }

    //Guarda el nou objecte DadesEstacio
    public DadesEstacioDTO saveDadesEstacio(DadesEstacioDTO dadesEstacio) {

        String query = "INSERT INTO dadesestacio values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, dadesEstacio.getLocalitzacioEstacioDTO().getCodiEstacio());
            statement.setString(2, dadesEstacio.getFecha());
            statement.setFloat(3, dadesEstacio.getH01());
            statement.setFloat(4, dadesEstacio.getH02());
            statement.setFloat(5, dadesEstacio.getH03());
            statement.setFloat(6, dadesEstacio.getH04());
            statement.setFloat(7, dadesEstacio.getH05());
            statement.setFloat(8, dadesEstacio.getH06());
            statement.setFloat(9, dadesEstacio.getH07());
            statement.setFloat(10, dadesEstacio.getH08());
            statement.setFloat(11, dadesEstacio.getH09());
            statement.setFloat(12, dadesEstacio.getH10());
            statement.setFloat(13, dadesEstacio.getH11());
            statement.setFloat(14, dadesEstacio.getH12());
            statement.setFloat(15, dadesEstacio.getH13());
            statement.setFloat(16, dadesEstacio.getH14());
            statement.setFloat(17, dadesEstacio.getH15());
            statement.setFloat(18, dadesEstacio.getH16());
            statement.setFloat(19, dadesEstacio.getH17());
            statement.setFloat(20, dadesEstacio.getH18());
            statement.setFloat(21, dadesEstacio.getH19());
            statement.setFloat(22, dadesEstacio.getH20());
            statement.setFloat(23, dadesEstacio.getH21());
            statement.setFloat(24, dadesEstacio.getH22());
            statement.setFloat(25, dadesEstacio.getH23());
            statement.setFloat(26, dadesEstacio.getH24());
            statement.setString(27, dadesEstacio.getContaminant());
            statement.setFloat(28, dadesEstacio.getQuantitat());
            statement.setString(29, dadesEstacio.getUnitat());

            Integer correct = statement.executeUpdate();
            if (correct > 0) {
                System.out.println("DadesUbicacio amb codi: " + dadesEstacio.getLocalitzacioEstacioDTO().getCodiEstacio() + " introduida amb exit.");
            } else {
                System.out.println("Error al insertar DadesUbicacio.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dadesEstacio;
    }

}
