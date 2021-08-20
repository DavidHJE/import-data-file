package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
	private static Database instance;
	private Connection connexion;

	private Database() {
		try {
			Dotenv dotenv = Dotenv.configure().load();

			Class.forName("org.postgresql.Driver");
			this.connexion = DriverManager.getConnection(dotenv.get("DATABASE_URL"), dotenv.get("DATABASE_USER"),
					dotenv.get("DATABASE_PASS"));

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}

		return instance;
	}

	public Connection getConnexion() {
		return connexion;
	}

}
