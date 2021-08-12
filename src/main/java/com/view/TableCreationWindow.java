package com.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
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
		setBounds(100, 100, 646, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton BtnReturn = new JButton("Retour");
		BtnReturn.setBounds(10, 11, 89, 23);
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
		scrollPane.setBounds(50, 45, 420, 235);
		contentPane.add(scrollPane);
		
		TableImportfields = new JTable();
		TableImportfields.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"COL CSV", "COL TABLE"
			}
		));
		scrollPane.setViewportView(TableImportfields);
		
		JButton BtnTestCombo = new JButton("New button");
		BtnTestCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) TableImportfields.getModel();
				
				String column[] = columnNames.split(";");
				String[]elementsCombo = new String[nbColumn];
				
		        for (int i = 0; i < column.length; i++) {
		        	elementsCombo[i] = column[i]; 
		        }
				JComboBox combo = new JComboBox(elementsCombo);
				TableImportfields.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(combo));
				
				for(int i = 0 ; i<nbColumn ; i++) {
					tableModel.addRow(new Objects[] {});
				}
			}
		});
		BtnTestCombo.setBounds(381, 11, 89, 23);
		contentPane.add(BtnTestCombo);
	}
}
