package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.controller.ConnectionController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConnectionForm extends JFrame {

	private JPanel contentPane;
	
	private JButton btnInscription;
	private JLabel usernameLabel;
	private JTextField usernameField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton btnConnexion;

	private JLabel connexionLink;

	public ConnectionForm(final ConnectionController controller) {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 450);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		btnInscription = new JButton("S'inscrire");
		btnInscription.setBounds(400, 30, 150, 40);
		contentPane.add(btnInscription);
		
		usernameLabel = new JLabel("Nom d'utilisateur");
		usernameLabel.setBounds(125, 125, 150, 30);
		contentPane.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.setBounds(275, 125, 200, 30);
		contentPane.add(usernameField);
		
		passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setBounds(125, 195, 150, 30);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(275, 195, 200, 30);
		contentPane.add(passwordField);
		
		btnConnexion = new JButton("Se connecter");
		btnConnexion.setBounds(225, 270, 150, 40);
		contentPane.add(btnConnexion);

		connexionLink = new JLabel("Mot de passe oublié ?");
		connexionLink.setHorizontalAlignment(SwingConstants.CENTER);
		connexionLink.setForeground(Color.blue.darker());
		connexionLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		connexionLink.setBounds(200, 320, 200, 30);
		contentPane.add(connexionLink);
		
		btnInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openInscriptionFrame();
			}
		});
		
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.connection(usernameField.getText().trim(),
						passwordField.getPassword());
			}
		});
		
		connexionLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
	}

}
