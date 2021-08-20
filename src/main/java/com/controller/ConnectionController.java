package com.controller;

import java.util.Arrays;

import javax.swing.JOptionPane;

import com.dao.UserDao;
import com.view.InscriptionForm;

public class ConnectionController {
	private InscriptionForm inscriptionFrame;

	public ConnectionController() {
		try {
			inscriptionFrame = new InscriptionForm(this);
			inscriptionFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openConnectionFrame() {
		try {
			inscriptionFrame.setVisible(false);
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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
