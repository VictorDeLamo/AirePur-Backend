package com.airepur.repository;

import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

@Repository
public class HealthRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    public String getHealth() {
        // Definir el patrón para 'O'
        String[] oPattern = {
                "  ########  ",
                " ##      ## ",
                "##        ##",
                "##        ##",
                "##        ##",
                " ##      ## ",
                "  ########  "
        };

// Definir el patrón para 'K'
        String[] kPattern = {
                "##      ##  ",
                "##     ##   ",
                "##    ##    ",
                "##  ##      ",
                "##    ##    ",
                "##     ##   ",
                "##      ##  "
        };

        // Utilizar StringBuilder para concatenar los patrones
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < oPattern.length; i++) {
            sb.append("<pre>").append(oPattern[i]).append("     ").append(kPattern[i]).append("<pre>\n");
        }

        // Retornar el patrón combinado
        return sb.toString();
    }
}
