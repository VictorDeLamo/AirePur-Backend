package com.airepur.utils;

import com.airepur.domain.SuggestedPlaceDTO;

import java.util.Comparator;

public class SuggestedPlacesComparator implements Comparator<SuggestedPlaceDTO> {
    @Override
    public int compare(SuggestedPlaceDTO place1, SuggestedPlaceDTO place2) {
        // Obtener los valores de ICA y distancia de cada lugar
        int ica1 = place1.getICA();
        int ica2 = place2.getICA();
        double dist1 = place1.getDistancia();
        double dist2 = place2.getDistancia();

        // Si la diferencia en "ICA" es mayor o igual a 5, prioriza la diferencia en "ICA"
        if (Math.abs(ica1 - ica2) >= 5) {
            return Integer.compare(ica1, ica2); // Orden ascendente por "ICA"
        } else {
            // Si la distancia supera los 10 km, se prioriza la que está más cerca
            if (dist1 < 10 && dist2 < 10) {
                return Double.compare(dist1, dist2); // Orden ascendente por distancia
            } else {
                return Double.compare(dist1, dist2); // Orden ascendente por distancia
            }
        }
    }
}
