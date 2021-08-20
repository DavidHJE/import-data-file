package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.model.Database;

public class UserDao {

	public static void create(String username, String email, char[] password) throws SQLException {
		Database db = Database.getInstance();
		Connection connexion = db.getConnexion();

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashPassword = bCryptPasswordEncoder.encode(new String(password));

		PreparedStatement pst = connexion.prepareStatement(
				"INSERT INTO public.\"Utilisateurs\"( identifiant, mot_de_passe, email) VALUES (?, ?, ?);");
		pst.setString(1, username);
		pst.setString(2, hashPassword);
		pst.setString(3, email);
		pst.execute();
	}

	public static void update(Integer id, String username, String email) throws Exception {
		Database db = Database.getInstance();
		Connection connexion = db.getConnexion();

		PreparedStatement pst = connexion
				.prepareStatement("UPDATE public.\"Utilisateurs\" SET identifiant=?, email=? WHERE id=?;");
		pst.setString(1, username);
		pst.setString(2, email);
		pst.setInt(3, id);
		pst.execute();
	}

	public static void changePassword(Integer id, char[] password) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashPassword = bCryptPasswordEncoder.encode(new String(password));

		Database db = Database.getInstance();
		Connection connexion = db.getConnexion();

		PreparedStatement pst = connexion
				.prepareStatement("UPDATE public.\"Utilisateurs\" SET mot_de_passe=? WHERE id=?;");
		pst.setString(1, hashPassword);
		pst.setInt(2, id);
		pst.execute();
	}

	public static void delete(Integer id) throws Exception {

		Database db = Database.getInstance();
		Connection connexion = db.getConnexion();

		PreparedStatement pst = connexion.prepareStatement("DELETE FROM public.\"Utilisateurs\" WHERE id=?;");
		pst.setInt(1, id);
		pst.execute();
	}

	public static boolean isUsernameUnique(String name) throws Exception {
		Database db = Database.getInstance();
		Connection connexion = db.getConnexion();

		PreparedStatement pst = connexion
				.prepareStatement("SELECT * FROM public.\"Utilisateurs\" WHERE identifiant=?;");
		pst.setString(1, name);
		ResultSet resultSet = pst.executeQuery();

		if (resultSet.next()) {
			return false;
		} else {
			return true;
		}

	}

	public static boolean isEmailUnique(String email) throws Exception {
		Database db = Database.getInstance();
		Connection connexion = db.getConnexion();

		PreparedStatement pst = connexion.prepareStatement("SELECT * FROM public.\"Utilisateurs\" WHERE email=?;");
		pst.setString(1, email);
		ResultSet resultSet = pst.executeQuery();

		if (resultSet.next()) {
			return false;
		} else {
			return true;
		}

	}
}
