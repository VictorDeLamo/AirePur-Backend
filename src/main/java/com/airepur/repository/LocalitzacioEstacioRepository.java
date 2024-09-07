package com.airepur.repository;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.utils.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

@Repository
public class LocalitzacioEstacioRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;
    private LocalitzacioEstacioDTO leDTO = null;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    //Retorna totes les localitzacions estacio
    public ArrayList<LocalitzacioEstacioDTO> getLocalitzacions() throws SQLException {
        ArrayList<LocalitzacioEstacioDTO> localitzacions = new ArrayList<>();
        String query = "SELECT * FROM localitzacioestacio"; //query

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) { //s'executa la query
                while (resultSet.next()) {
                    //assignació del resultat a objecte LocalitzacioEstacioDTO
                    leDTO = new LocalitzacioEstacioDTO(resultSet.getString("codiestacio"),
                            resultSet.getString("comarca"), resultSet.getString("municipi"),
                            resultSet.getFloat("longitud"), resultSet.getFloat("latitud"));
                    //afegir objecte a ArrayList de LocalitzacioEstacioDTO
                    localitzacions.add(leDTO);
                }
            }
        }
        return localitzacions;
    }

    //Retorna la localitzacio estacio amb codiEstacio
    public LocalitzacioEstacioDTO getLocalitzacio(String codiEstacio) throws SQLException {
        String query = "SELECT * FROM localitzacioestacio u WHERE u.codiestacio = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codiEstacio);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    leDTO = new LocalitzacioEstacioDTO(resultSet.getString("codiestacio"),
                            resultSet.getString("comarca"), resultSet.getString("municipi"),
                            resultSet.getFloat("longitud"), resultSet.getFloat("latitud"));
                }
            }
        }
        return leDTO;
    }

    //Retorna la localitzacio estacio més pròxima a les coordenades longitud i latitud
    public LocalitzacioEstacioDTO getLocalitzacioCoordenades(float longitud, float latitud) throws SQLException {
        ArrayList<LocalitzacioEstacioDTO> listloc = getLocalitzacions();
        float mindist = Float.MAX_VALUE;
        String codi = null;
        String municipi = null;
        Float longi = 0.0f;
        Float lat = 0.0f;
        for (int i = 0; i < listloc.size(); ++i) {
            float lo = listloc.get(i).getLongitud();
            float la = listloc.get(i).getLatitud();
            float distance = Haversine(longitud, latitud, lo, la);
            if (distance < mindist) {
                mindist = distance;
                codi = listloc.get(i).getCodiEstacio();
                municipi = listloc.get(i).getMunicipi();
                longi = lo;
                lat = la;
            }
        }
        leDTO = new LocalitzacioEstacioDTO(codi, municipi);
        leDTO.setLongitud(longi);
        leDTO.setLatitud(lat);
        return leDTO;
    }

    public float Haversine(float longitud1, float latitud1, float longitud2, float latitud2) {
        //Radi terra en km
        final float R = (float) 6371.0;

        // Convertir grados a radianes
        float latDistance = (float) Math.toRadians(latitud2 - latitud1);
        float lonDistance = (float) Math.toRadians(longitud2 - longitud1);
        float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitud1)) * Math.cos(Math.toRadians(latitud2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
        float c = 2 * (float) Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    //Retorna les 10 localitzacio estacio més pròximes a les cordeenades longitud i latitud
    public JSONArray getLocalitzacionsProx(float longitud, float latitud) throws JSONException, SQLException {
        ArrayList<LocalitzacioEstacioDTO> listloc = getLocalitzacions();
        PriorityQueue<JSONObject> smallestDistances = new PriorityQueue<>(Comparator.comparing(o -> {
            try {
                return -Float.parseFloat(o.getString("distancia"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }));
        for (int i = 0; i < listloc.size(); ++i) {
            float lo = listloc.get(i).getLongitud();
            float la = listloc.get(i).getLatitud();
            float distance = Haversine(longitud, latitud, lo, la);
            JSONObject ciudadJSON = new JSONObject();
            ciudadJSON.put("codi", listloc.get(i).getCodiEstacio());
            ciudadJSON.put("municipi", listloc.get(i).getMunicipi());
            ciudadJSON.put("distancia", distance);
            // Agregar la ciudad actual a la cola de prioridad si aún no se han agregado 10 ciudades
            if (smallestDistances.size() < 10) {
                smallestDistances.offer(ciudadJSON);
            } else {
                // Si ya hay 10 ciudades, comparar la distancia actual con la mayor de las 10
                JSONObject maxdistCiudad = smallestDistances.peek();
                if (distance < Float.parseFloat(maxdistCiudad.getString("distancia"))) {
                    // Si la distancia actual es menor que la mayor de las 10, eliminarla y agregar la ciudad actual
                    smallestDistances.poll();
                    smallestDistances.offer(ciudadJSON);
                }
            }
        }
        // Agregar los elementos del smallestDistances al stack
        Stack<JSONObject> stack = new Stack<>();
        while (!smallestDistances.isEmpty()) {
            stack.push(smallestDistances.poll());
        }
        // Agregar los elementos del stack al resultArray
        JSONArray resultArray = new JSONArray();
        while (!stack.isEmpty()) {
            resultArray.put(stack.pop());
        }
        return resultArray;
    }

    //Guarda la nova localitzacio estacio (insert a la bd)
    public LocalitzacioEstacioDTO saveLocalitzacioEstacio(LocalitzacioEstacioDTO localitzacioEstacio) throws SQLException {
        String query = "INSERT INTO localitzacioestacio values (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, localitzacioEstacio.getCodiEstacio());
            statement.setString(2, localitzacioEstacio.getComarca());
            statement.setString(3, localitzacioEstacio.getMunicipi());
            statement.setFloat(4, localitzacioEstacio.getLongitud());
            statement.setFloat(5, localitzacioEstacio.getLatitud());

            Integer correct = statement.executeUpdate();
            if (correct > 0) {
                System.out.println("LocalitzacioEstacio amb codi: " + localitzacioEstacio.getCodiEstacio() + " introduida amb exit.");
            } else {
                System.out.println("Error al insertar LocalitzacioEstacio.");
            }
        }
        return localitzacioEstacio;
    }

    public Map<String, String> getLocalitzacioMunicipis() throws SQLException {
        Map<String, String> lista = new HashMap<>();
        String query = "SELECT DISTINCT ON (l.municipi) l.municipi, l.codiestacio\n" +
                "FROM localitzacioestacio l \n" +
                "ORDER BY l.municipi, l.codiestacio; ";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    lista.put(resultSet.getString("municipi"), resultSet.getString("codiestacio"));
                }
            }
        }
        return lista;
    }
}
