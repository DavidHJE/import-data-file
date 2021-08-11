package com.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.controller.ConnectionController;

@SuppressWarnings("serial")
public class InscriptionForm extends JFrame {

	private JPanel contentPane;

	private JButton btnHome;

	private JLabel mailLabel;
	private JTextField mailField;

	private JLabel usernameLabel;
	private JTextField usernameField;

	private JLabel passwordLabel;
	private JPasswordField passwordField;

	private JLabel passwordConfirmLabel;
	private JPasswordField passwordConfirmField;

	private JButton btnConnexion;

	public InscriptionForm(final ConnectionController connection) {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 450);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		btnHome = new JButton("Accueil");
		btnHome.setBounds(400, 30, 150, 40);
		contentPane.add(btnHome);

		mailLabel = new JLabel("E-mail");
		mailLabel.setBounds(125, 120, 150, 30);
		contentPane.add(mailLabel);

		mailField = new JTextField();
		mailField.setBounds(275, 120, 200, 30);
		contentPane.add(mailField);

		usernameLabel = new JLabel("Nom d'utilisateur");
		usernameLabel.setBounds(125, 170, 150, 30);
		contentPane.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.setBounds(275, 170, 200, 30);
		contentPane.add(usernameField);

		passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setBounds(125, 220, 150, 30);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(275, 220, 200, 30);
		contentPane.add(passwordField);

		passwordConfirmLabel = new JLabel("Mot de passe");
		passwordConfirmLabel.setBounds(125, 270, 150, 30);
		contentPane.add(passwordConfirmLabel);

		passwordConfirmField = new JPasswordField();
		passwordConfirmField.setBounds(275, 270, 200, 30);
		contentPane.add(passwordConfirmField);

		btnConnexion = new JButton("S'inscrire");
		btnConnexion.setBounds(200, 340, 150, 40);
		contentPane.add(btnConnexion);

		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.openConnectionFrame();
			}
		});

		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.createCompte(mailField.getText().trim(), usernameField.getText().trim(),
						passwordField.getPassword(), passwordConfirmField.getPassword());
			}
		});

	}

}
