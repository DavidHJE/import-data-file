package com.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.opencsv.CSVReader;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainWindow extends JFrame {
	private JPanel contentPane;
	private JTextField FilePath;
	private JCheckBox CheckBoxFirstRow;
	
	String absolutepath;
	static Boolean firstRowEqualsColumn = false;
	static Boolean createTable = false;
	static Integer nbColumn;
	static String columnNames = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 689, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		FilePath = new JTextField();
		FilePath.setBounds(10, 11, 393, 20);
		contentPane.add(FilePath);
		FilePath.setColumns(10);
		
		JButton BtnImport = new JButton("Rechercher");
		BtnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fileChooser
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier CSV", "csv");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    absolutepath = selectedFile.getAbsolutePath();
				    String FileName = selectedFile.getName();
				    FilePath.setText(FileName);
				}
				
			}
		});
		BtnImport.setBounds(413, 10, 89, 23);
		contentPane.add(BtnImport);
		
		CheckBoxFirstRow = new JCheckBox("Fichier poss\u00E9dant ent\u00EAte");
		CheckBoxFirstRow.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if(e.getStateChange() == ItemEvent.SELECTED) {
					firstRowEqualsColumn = true;
				}else {
					firstRowEqualsColumn = false;
				}
			}
		});
		CheckBoxFirstRow.setBounds(10, 38, 377, 23);
		contentPane.add(CheckBoxFirstRow);
		
		JCheckBox CheckBoxCreateTable = new JCheckBox("Cr\u00E9er une table depuis le fichier");
		CheckBoxCreateTable.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					createTable = true;
				}else {
					createTable = false;
				}
			}
		});
		CheckBoxCreateTable.setBounds(10, 64, 377, 23);
		contentPane.add(CheckBoxCreateTable);
		
		JButton BtnNext = new JButton("Suivant");
		BtnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Import(absolutepath);
				if(createTable = true) {
					TableCreationWindow TableCreationWindow = new TableCreationWindow(nbColumn,columnNames);
					TableCreationWindow.setVisible(true);
				}else {
					
				}
				dispose();
			}
		});
		BtnNext.setBounds(214, 113, 89, 23);
		contentPane.add(BtnNext);
	}
	
	
	public static void Import(String filePath) {
			try {
		
		        FileReader fileReader = new FileReader(filePath);
		        CSVReader openCSVReader = new CSVReader(fileReader);	
		        List<String[]> allData = openCSVReader.readAll();        
		        
		        //lire tout
//		        for (String[] row : allData) {
//		            for (String col : row) {
//		                System.out.print(col + "\t");
//		            }
//		            System.out.println();
//		        }
		        //lire 1 ligne
//		        String[] columnName = allData.get(0);
//		        for (int j = 0; j < columnName.length; j++) {
//		        	System.out.print(columnName[j] + ";");
//		        }
		        
		        String[] columnName = allData.get(0);
				nbColumn = columnName.length;
				if(firstRowEqualsColumn) {
			        for (int j = 0; j < columnName.length; j++) {
			        	columnNames += columnName[j] + ";";
			        }
		        }else {
		        	for (int k = 0; k < columnName.length; k++) {
		        		columnNames += "colonne" + (k+1) +";";
			        }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}
}
