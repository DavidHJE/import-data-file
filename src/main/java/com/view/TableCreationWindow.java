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

public class TableCreationWindow extends JFrame {

	private JPanel contentPane;
	private JTable TableImportfields;
	private JScrollPane scrollPane;
	
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
				CreateTable();
			}
		});
		btnNewButton.setBounds(604, 11, 158, 23);
		contentPane.add(btnNewButton);
		
		ShowTable(nbColumn,columnNames);
		
	}
	
	public void ShowTable(final Integer nbColumn, final String columnNames) {
		DefaultTableModel tableModel = (DefaultTableModel) TableImportfields.getModel();
		
		String column[] = columnNames.split(";");
		String[]elementsCombo = new String[nbColumn];
		String[]elementsComboType = {"Nombre","Texte","Vrai/Faux"};
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
	
	public void CreateTable() {
		//lecture de la table
		for(int i = 0; i<TableImportfields.getModel().getRowCount() ; i++) {
			for(int j = 0; j<((TableImportfields.getModel().getColumnCount())-1) ; j++) {
				String cellInfo = TableImportfields.getValueAt(i,j).toString();
				System.out.println(cellInfo);
			}
			System.out.println("======================================");
		}
	}
}
