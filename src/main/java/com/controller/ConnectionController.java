package com.controller;

import java.util.Arrays;

import javax.swing.JOptionPane;

import com.dao.UserDao;
import com.model.User;
import com.view.ConnectionForm;
import com.view.InscriptionForm;

public class ConnectionController {
	private InscriptionForm inscriptionFrame;
	private ConnectionForm connectionFrame;

	public ConnectionController() {
		try {
			connectionFrame = new ConnectionForm(this);
			connectionFrame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openConnectionFrame() {
		try {
			connectionFrame = new ConnectionForm(this);
			connectionFrame.setVisible(true);
			inscriptionFrame.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void openInscriptionFrame() {
		try {
			inscriptionFrame = new InscriptionForm(this);
			inscriptionFrame.setVisible(true);
			connectionFrame.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createCompte(String email, String username, char[] password, char[] passwordConfirm) {
		if (email.equals("")) {
			JOptionPane.showMessageDialog(inscriptionFrame, "L'email ne doit pas être vide !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (username.equals("")) {
			JOptionPane.showMessageDialog(inscriptionFrame, "Le nom d'utilisateur ne doit pas être vide !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!Arrays.equals(password, passwordConfirm)) {
			JOptionPane.showMessageDialog(inscriptionFrame, "Les deux mots de passe ne correspondent pas !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (password.length == 0 || passwordConfirm.length == 0) {
			JOptionPane.showMessageDialog(inscriptionFrame, "Le mot de passe ne doit pas être vide !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean isUsernameUnique = false;

		try {
			isUsernameUnique = UserDao.isUsernameUnique(username);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (!isUsernameUnique) {
			JOptionPane.showMessageDialog(inscriptionFrame,
					"Le nom d'utilisateur déjà utilisé. Veuillez choisir un autre nom d'utilisateur", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean isEmailUnique = false;

		try {
			isEmailUnique = UserDao.isEmailUnique(email);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (!isEmailUnique) {
			JOptionPane.showMessageDialog(inscriptionFrame,
					"L'email déjà utilisé. Veuillez choisir un autre nom d'utilisateur", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			UserDao.create(username, email, passwordConfirm);

			System.out.println("ok inscription");
			inscriptionFrame.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void connection(String username, char[] password) {
		try {
			User user = UserDao.getUser(username, password);
			
			if (user == null) {
				JOptionPane.showMessageDialog(connectionFrame, "Nom d'utilisateur ou mot de passe incorrecte !",
						"Erreur", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("ok connection");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
