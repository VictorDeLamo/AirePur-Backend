package com.airepur;


import com.airepur.utils.Config;

import org.json.JSONException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws JSONException, IOException, SQLException {

		SpringApplication.run(BackendApplication.class, args);


	}
}
