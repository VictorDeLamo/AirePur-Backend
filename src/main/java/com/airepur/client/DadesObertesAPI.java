package com.airepur.client;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DadesObertesAPI {

    //Per cada estació i un contaminant en una data, les seves mesures a cada hora (taula dadesestacio de la bd)
    public JSONArray getDadesObertesxIdDataContIMesures(int k) throws IOException, JSONException {
        JSONArray result = new JSONArray();
        int off = 376 + k*120;
        String offString = "" + off;
        URL url = new URL("https://analisi.transparenciacatalunya.cat/resource/tasf-thgu.json?$limit=120&$" +
                "offset=" + offString + "&$select=codi_eoi,data,contaminant,h01,h02,h03,h04,h05,h06,h07,h08,h09,h10,h11,h12," +
                "h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,unitats,longitud,latitud&$group=codi_eoi,data,contaminant,h01,h02," +
                "h03,h04,h05,h06,h07,h08,h09,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,unitats,longitud,latitud&$order=data%20desc");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int responsecode=con.getResponseCode();
        if(responsecode!=200) {
            System.out.println("Error"+ responsecode);
        } else {
            StringBuilder info = new StringBuilder();
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                info.append(sc.nextLine());
            }
            sc.close();

            result = new JSONArray(String.valueOf(info));
        }
        return result;
    }


    //Per cada estacioLocalitzacio el seu codi, nom_comarca, municipi, longitud i latitud (taula localitzacionsestacio de la bd)
    public JSONArray getEstacions() throws IOException, JSONException {
        JSONArray result = new JSONArray();
        URL url = new URL("https://analisi.transparenciacatalunya.cat/resource/tasf-thgu.json?data=2024-01-01T00:00:00.000&$select=codi_eoi,nom_comarca,municipi,longitud,latitud&$group=codi_eoi,nom_comarca,municipi,longitud,latitud");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responsecode = con.getResponseCode();
        if (responsecode != 200) {
            System.out.println("Error" + responsecode);
        } else {
            StringBuilder info = new StringBuilder();
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                info.append(sc.nextLine());
            }
            sc.close();

            result = new JSONArray(String.valueOf(info));
        }
        return result;
    }

    //getDadesObertesxIdDataContIMesures amb la  data del dia anterior a l'actual (per fer inserts programats cada dia)
    public JSONArray getAhirDadesObertes(String data) throws IOException, JSONException {

        JSONArray result = new JSONArray();
        URL url = new URL("https://analisi.transparenciacatalunya.cat/resource/tasf-thgu.json?$select=codi_eoi,data,contaminant,h01,h02,h03," +
                "h04,h05,h06,h07,h08,h09,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,unitats,longitud,latitud&$group=codi_eoi," +
                "data,contaminant,h01,h02,h03,h04,h05,h06,h07,h08,h09,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,unitats,longitud," +
                "latitud&$order=data%20desc&$where=data=%27" + data + "%27");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int responsecode = con.getResponseCode();

        if (responsecode != 200) {
            System.out.println("Error" + responsecode);
        } else {
            StringBuilder info = new StringBuilder();
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                info.append(sc.nextLine());
            }
            sc.close();

            result = new JSONArray(String.valueOf(info));
        }
        return result;
    }
}
