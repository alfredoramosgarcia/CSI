package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.Timer;

import data.Especie;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class IfrEspecie extends JInternalFrame {
	private JTextField txtName;
	private Especie _especie = null;
	private SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private Timer _timMessage;
	
	public IfrEspecie(Especie especie) {
		 
		this();
		
		if(especie == null) throw new IllegalArgumentException("El p�rametro 'especie' no puede ser null.");
		
		txtName = new JTextField(especie.getName());
		txtName.setBounds(20, 70, 96, 19);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		_especie = especie;
	}
	
	/**
	 * Create the frame.
	 */
	public IfrEspecie() {
		
		setResizable(true);
		setClosable(true);
		setTitle("Especie");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setForeground(SystemColor.textHighlight);
		lblMessage.setBounds(20, 10, 390, 20);
		getContentPane().add(lblMessage);
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setBounds(20, 47, 45, 13);
		getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(20, 70, 96, 19);
		getContentPane().add(txtName);
		txtName.setColumns(10); 
		
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(_especie == null) _especie = new Especie(txtName.getText());
					else _especie.setName(txtName.getText());
					_timMessage.stop();
					_especie.Save(); 
					_timMessage.start();  
				}
				catch (IOException | SQLException | IllegalArgumentException ex) { 
					_timMessage.start();
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
				lblMessage.setText("Se ha introducido correctamente " + _simpleDateFormat.format(new Date()));
			}
		}); 
		butSave.setBounds(139, 69, 96, 21); 
		getContentPane().add(butSave);
		
		_timMessage = new Timer(5000, new ActionListener() {
			
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 
				 lblMessage.setText("");
				 ((Timer)e.getSource()).stop();
			 }
		});
	}
}
