package com.airepur.service;

public class CalulICAService {

    public float calcularSubindiceParaContaminante(String contaminant, float concentracio) {
        if (concentracio == -1){
            return -1;
        }
        else {
            switch (contaminant) {
                case "PM10":
                    return calcularICA_PM10(concentracio);
                case "NO2":
                    return calcularICA_NO2(concentracio);
                case "O3":
                    return calcularICA_O3(concentracio);
                case "C6H6":
                    return calcularICA_C6H6(concentracio);
                case "H2S":
                    return calcularICA_H2S(concentracio);
                case "CO":
                    return calcularICA_CO(concentracio);
                case "SO2":
                    return calcularICA_SO2(concentracio);
                case "PM2.5":
                    return calcularICA_PM25(concentracio);
                default:
                    return -1; // Caso base o error
            }
        }
    }

    private float calcularICA_PM10(float concentracion) {
        if (concentracion <= 20) {
            return (50.0f / 20.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 40) {
            return (49.0f / 20.0f) * (concentracion - 21) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 50) {
            return (49.0f / 10.0f) * (concentracion - 41) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 100) {
            return (49.0f / 100.0f) * (concentracion - 51) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 150) {
            return (99.0f / 50.0f) * (concentracion - 101) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 300) {
            return (199.0f / 150.0f) * (concentracion - 151) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 300) {
            return 500.0f; //  Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_NO2(float concentracion) {
        if (concentracion <= 40) {
            return (50.0f / 40.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 90) {
            return (49.0f / 50.0f) * (concentracion - 41) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 120) {
            return (49.0f / 30.0f) * (concentracion - 91) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 230) {
            return (49.0f / 110.0f) * (concentracion - 121) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 340) {
            return (99.0f / 140.0f) * (concentracion - 231) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 500) {
            return (199.0f / 160.0f) * (concentracion - 341) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 500) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_O3(float concentracion) {
        if (concentracion <= 50) {
            return (50.0f / 50.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 100) {
            return (49.0f / 50.0f) * (concentracion - 51) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 130) {
            return (49.0f / 30.0f) * (concentracion - 101) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 240) {
            return (49.0f / 110.0f) * (concentracion - 131) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 380) {
            return (99.0f / 140.0f) * (concentracion - 241) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 500) {
            return (199.0f / 120.0f) * (concentracion - 381) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 500) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_C6H6(float concentracion) {
        if (concentracion <= 5) {
            return (50.0f / 5.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 10) {
            return (49.0f / 5.0f) * (concentracion - 6) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 20) {
            return (49.0f / 10.0f) * (concentracion - 11) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 50) {
            return (49.0f / 30.0f) * (concentracion - 21) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 100) {
            return (99.0f / 50.0f) * (concentracion - 51) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 150) {
            return (199.0f / 50.0f) * (concentracion - 101) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 150) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_H2S(float concentracion) {
        if (concentracion <= 25) {
            return (50.0f / 25.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 50) {
            return (49.0f / 25.0f) * (concentracion - 26) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 100) {
            return (49.0f / 50.0f) * (concentracion - 51) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 200) {
            return (49.0f / 100.0f) * (concentracion - 101) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 500) {
            return (99.0f / 300.0f) * (concentracion - 201) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 600) {
            return (199.0f / 100.0f) * (concentracion - 501) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 600) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_CO(float concentracion) {
        if (concentracion <= 2) {
            return (50.0f / 2.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 5) {
            return (49.0f / 3.0f) * (concentracion - 3) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 10) {
            return (49.0f / 5.0f) * (concentracion - 6) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 20) {
            return (49.0f / 10.0f) * (concentracion - 11) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 50) {
            return (99.0f / 30.0f) * (concentracion - 21) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 100) {
            return (199.0f / 50.0f) * (concentracion - 51) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 100) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_SO2(float concentracion) {
        if (concentracion <= 100) {
            return (50.0f / 100.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 200) {
            return (49.0f / 100.0f) * (concentracion - 101) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 350) {
            return (49.0f / 150.0f) * (concentracion - 201) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 500) {
            return (49.0f / 150.0f) * (concentracion - 351) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 750) {
            return (99.0f / 250.0f) * (concentracion - 501) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 1250) {
            return (199.0f / 500.0f) * (concentracion - 751) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 1250) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }

    private float calcularICA_PM25(float concentracion) {
        if (concentracion <= 10) {
            return (50.0f / 10.0f) * (concentracion - 0) + 0.0f; // Escala proporcionalmente dentro del rango de ICA de 0 a 50
        } else if (concentracion <= 20) {
            return (49.0f / 10.0f) * (concentracion - 11) + 51.0f; // Escala proporcionalmente dentro del rango de ICA de 51 a 100
        } else if (concentracion <= 25) {
            return (49.0f / 5.0f) * (concentracion - 21) + 101.0f; // Escala proporcionalmente dentro del rango de ICA de 101 a 150
        } else if (concentracion <= 50) {
            return (49.0f / 25.0f) * (concentracion - 26) + 151.0f; // Escala proporcionalmente dentro del rango de ICA de 151 a 200
        } else if (concentracion <= 75) {
            return (99.0f / 25.0f) * (concentracion - 51) + 201.0f; // Escala proporcionalmente dentro del rango de ICA de 201 a 300
        } else if (concentracion <= 150) {
            return (199.0f / 75.0f) * (concentracion - 76) + 301.0f; // Escala proporcionalmente dentro del rango de ICA de 301 a 500
        } else if (concentracion > 150) {
            return 500.0f; // Un valor alto arbitrario para indicar una calidad del aire muy pobre
        } else {
            return -1.0f; // Valor fuera de rango o ICA no calculable
        }
    }
}
