package com.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TableCreationWindow extends JFrame {

	private JPanel contentPane;
	private JTable TableImportfields;
	private JScrollPane scrollPane;
	private JTextField TableNameField;
	
	String absolutepath;
	static Integer nbColumn;
	static String columnNames = "";
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableCreationWindow frame = new TableCreationWindow(nbColumn,columnNames);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	
	public TableCreationWindow (final Integer nbColumn, final String columnNames) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton BtnReturn = new JButton("Retour");
		BtnReturn.setBounds(20, 11, 89, 23);
		BtnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow mainWindow = new MainWindow();
				mainWindow.setVisible(true);
				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(BtnReturn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 45, 742, 354);
		contentPane.add(scrollPane);
		
		TableImportfields = new JTable();
		TableImportfields.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Champ fichier", "Champ table", "Identifiant unique", "Doit \u00EAtre unique", "Peut \u00EAtre null ?", "Type", ""
			}
		));
		scrollPane.setViewportView(TableImportfields);
		
		JButton btnNewButton = new JButton("Test Lecture Table");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateTable(nbColumn,columnNames);
			}
		});
		btnNewButton.setBounds(604, 11, 158, 23);
		contentPane.add(btnNewButton);
		
		TableNameField = new JTextField();
		TableNameField.setBounds(277, 12, 183, 20);
		contentPane.add(TableNameField);
		TableNameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nom de la table");
		lblNewLabel.setBounds(186, 15, 81, 14);
		contentPane.add(lblNewLabel);
		
		ShowTable(nbColumn,columnNames);
		
	}
	
	public void ShowTable(Integer nbColumn, String columnNames) {
		DefaultTableModel tableModel = (DefaultTableModel) TableImportfields.getModel();
		
		String column[] = columnNames.split(";");
		String[]elementsCombo = new String[nbColumn];
		String[]elementsComboType = {"Nombre","Nombre à virgule","Texte","Vrai/Faux"};
        for (int i = 0; i < column.length; i++) {
        	elementsCombo[i] = column[i]; 
        }
		JComboBox combo = new JComboBox(elementsCombo);
		JComboBox comboType = new JComboBox(elementsComboType);
		JCheckBox checkBox = new JCheckBox();
		
		TableImportfields.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(combo));
		TableImportfields.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(comboType));
		TableImportfields.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(checkBox));
		TableImportfields.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(checkBox));
		TableImportfields.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(checkBox));
		
		for(int i = 0 ; i<nbColumn ; i++) {
			tableModel.addRow(new Objects[] {});
		}
	}
	
	public void CreateTable(Integer nbColumn, String columnNames) {
		
		String TableName = TableNameField.getText();
		Integer start = 0;
		Boolean continueCreation = true;
		String finalSQL = "CREATE TABLE [IF NOT EXISTS] ";
		String type;
		if(TableName.equals("")) {
//			JOptionPane.showMessageDialog(Message, "ERREUR!\nVeuillez remplir correctement le nom de la table",
//                    "Erreur", JOptionPane.ERROR_MESSAGE);
			System.out.println("erreur nom table vide");
		}
		else {
			finalSQL += TableName+" (";
			for(int i = 0; i < TableImportfields.getModel().getRowCount() ; i++) {
				for(int j = 1; j<((TableImportfields.getModel().getColumnCount())-1) ; j++) {
					String cellInfo = TableImportfields.getValueAt(i,j).toString();
					type = TableImportfields.getValueAt(i,5).toString();
					switch(type) {//type
					case"Nombre":
						type = "INT ";
						break;
					case"Nombre à virgule":
						type = "numeric ";
						break;
					case"Texte":
						type = "VARCHAR (255) ";
						break;
					case"Vrai/Faux":
						type = "boolean ";
						break;
					default:
						continueCreation = false;
					}
					
					switch(j) {
						case 1:
							finalSQL += cellInfo + " "+ type;
							break;
						case 2:
							if(cellInfo.equals("true")) {
								finalSQL += "serial PRIMARY KEY ";
							}
							break;
						case 3:
							if(cellInfo.equals("true")) {
								finalSQL += "UNIQUE ";
							}
							break;
						case 4:
							if(cellInfo.equals("true")) {
								finalSQL += "NULL,";
							}else if(cellInfo.equals("false")){
								finalSQL += "NOT NULL,";
							}else {
								continueCreation = false;
							}
							break;
						default:
							
					}
				}
			}
			finalSQL += ");";
			if(continueCreation == true) {
				System.out.println(finalSQL);
			}
		}
		
	}
}
