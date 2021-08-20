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
public class CodeResetPassword extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;
	private JButton resetButton;
	private JLabel emailLabel;
	private JTextField emailField;

	/**
	 * Create the dialog.
	 */
	public CodeResetPassword() {
		
		setTitle("Code");
		setResizable(false);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setSize(530, 250);
		setLocationRelativeTo(null);

		contentPanel.setLayout(null);
		setContentPane(contentPanel);
		
		emailLabel = new JLabel("Votre code");
		emailLabel.setBounds(50, 70, 200, 25);
		contentPanel.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setBounds(270, 70, 200, 25);
		contentPanel.add(emailField);
		
		cancelButton = new JButton("Annuler");
		cancelButton.setBounds(50, 170, 200, 25);
		contentPanel.add(cancelButton);

		resetButton = new JButton("Reset Passworld");
		resetButton.setBounds(270, 170, 200, 25);
		contentPanel.add(resetButton);
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SendMail.sendTo(emailField.getText());
			}
		});
	}

}
