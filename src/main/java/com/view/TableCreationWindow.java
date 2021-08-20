package com.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.controller.ConnectionController;
import com.model.Database;
import com.opencsv.CSVReader;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
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
	
	static String absolutepath = "";
	static Integer nbColumn;
	static String columnNames = "";
	static Boolean firstRowEqualsColumn = false;
	Database db;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableCreationWindow frame = new TableCreationWindow(nbColumn,columnNames,firstRowEqualsColumn,absolutepath);
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
	
	
	public TableCreationWindow (final Integer nbColumn, final String columnNames, final Boolean firstRowEqualsColumn, final String absolutepath) {
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
		
		JButton btnNewButton = new JButton("Cr\u00E9er la table");
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
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportAfterCreate(absolutepath,firstRowEqualsColumn);
			}
		});
		btnNewButton_1.setBounds(196, 443, 89, 23);
		contentPane.add(btnNewButton_1);
		
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
		Boolean continueCreation = true;
		String finalSQL = "CREATE TABLE IF NOT EXISTS ";
		String type;
		String primaryColumn = "";
		
		if(TableName.equals("")) {
			JOptionPane.showMessageDialog(this, "ERREUR!\nVeuillez remplir correctement le nom de la table",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		else {
			finalSQL += TableName+" (";
			for(int i = 0; i < TableImportfields.getModel().getRowCount() ; i++) {
				for(int j = 1; j<((TableImportfields.getModel().getColumnCount())-1) ; j++) {
					String cellInfo = TableImportfields.getValueAt(i,j).toString();
					type = TableImportfields.getValueAt(i,5).toString();
					if(cellInfo.equals("")) {
						continueCreation = false;
					}
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
								finalSQL += "PRIMARY KEY ";
								primaryColumn = TableImportfields.getValueAt(i,1).toString();
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
			finalSQL = finalSQL.substring(0, finalSQL.length()-1);
			finalSQL += ");";
			if(continueCreation == true) {
				db = db.getInstance();
	            Connection connexion = db.getConnexion();
	            Statement st;
	            
	            try {
					st = connexion.createStatement();
					st.executeQuery(finalSQL);
					
				} catch (SQLException e) {
					
				}
	            JOptionPane.showMessageDialog(this, "Succès!\n la création de la table à réussi",
	            		"Erreur", JOptionPane.INFORMATION_MESSAGE);
	            
	            finalSQL = "ALTER TABLE public."+TableName+" ALTER COLUMN "+primaryColumn+" ADD GENERATED ALWAYS AS IDENTITY (SEQUENCE NAME public."+TableName+"_"+primaryColumn+"_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1);";
	            
	            try {
	            	st = connexion.createStatement();
					st.executeQuery(finalSQL);
				} catch (SQLException e) {
					
				}

	            int dialogButton = JOptionPane.YES_NO_OPTION;
	            int dialogResult = JOptionPane.showConfirmDialog (null, "Voulez-vous importer les données du csv dans la nouvel table ?","",dialogButton);
	            if(dialogResult == JOptionPane.YES_OPTION){
	            	//on lance l'import de donées:
	            	
	            }	
			}
		}
		
	}
	
	public static void ImportAfterCreate(String absolutepath,Boolean firstRowEqualsColumn) {
		
//		try {
//	        FileReader fileReader = new FileReader(absolutepath);
//	        CSVReader openCSVReader = new CSVReader(fileReader);	
//	        List<String[]> allData = openCSVReader.readAll();        
//	        
//	        //lire tout
////	        for (String[] row : allData) {
////	            for (String col : row) {
////	                System.out.print(col + "\t");
////	            }
////	            System.out.println();
////	        }
//	        //lire 1 ligne
////	        String[] columnName = allData.get(0);
////	        for (int j = 0; j < columnName.length; j++) {
////	        	System.out.print(columnName[j] + ";");
////	        }
//	        
//	        String[] columnName = allData.get(0);
//			nbColumn = columnName.length;
//			if(firstRowEqualsColumn) {
//		        for (int j = 0; j < columnName.length; j++) {
//		        	columnNames += columnName[j] + ";";
//		        }
//	        }else {
//	        	for (int k = 0; k < columnName.length; k++) {
//	        		columnNames += "colonne" + (k+1) +";";
//		        }
//	        }
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
	}
}
