package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.controller.SendMail;

@SuppressWarnings("serial")
public class ResetPassword extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;
	private JButton resetButton;
	private JLabel emailLabel;
	private JTextField emailField;
	private JButton codeResetButton;

	/**
	 * Create the dialog.
	 */
	public ResetPassword() {
		
		setTitle("Changer de mot de passe");
		setResizable(false);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setSize(530, 250);
		setLocationRelativeTo(null);

		contentPanel.setLayout(null);
		setContentPane(contentPanel);
		
		emailLabel = new JLabel("Votre email");
		emailLabel.setBounds(50, 70, 200, 25);
		contentPanel.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setBounds(270, 70, 200, 25);
		contentPanel.add(emailField);
		
		codeResetButton = new JButton("J'ai recu mon code");
		codeResetButton.setBounds(115, 120, 300, 25);
		contentPanel.add(codeResetButton);
		
		cancelButton = new JButton("Annuler");
		cancelButton.setBounds(50, 170, 200, 25);
		contentPanel.add(cancelButton);

		resetButton = new JButton("Envoie mail");
		resetButton.setBounds(270, 170, 200, 25);
		contentPanel.add(resetButton);
		
		codeResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CodeResetPassword dialog = new CodeResetPassword();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMail.sendTo(emailField.getText());
			}
		});
	}

}
