package gui;

import java.awt.EventQueue;


import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.Timer;

import data.Especie;
import data.Trabajador;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import gui.EspecieListModel;

public class IfrTrabajador extends JInternalFrame {
	private JTextField txtName;
	JComboBox<Especie> cmbEspecie = new JComboBox<Especie>();
	private Trabajador _trabajador = null;
	private SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private Timer _timMessage;
	
	
	public IfrTrabajador(Trabajador trabajador) throws SQLException, IOException {
		
		this();
		
		if(trabajador == null) throw new IllegalArgumentException("El p�rametro 'Trabajador' no puede ser null.");
	
		txtName.setText(trabajador.getName());
		cmbEspecie.getModel().setSelectedItem(trabajador.getEspecie());
		_trabajador = trabajador;
	}
	 
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException  
	 */
	public IfrTrabajador() throws SQLException, IOException {
		
		setResizable(true);
		setClosable(true);
		setTitle("Trabajador");
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
		
		cmbEspecie.setModel(new EspecieListModel(Especie.Search(null)));
		cmbEspecie.setToolTipText("");
		cmbEspecie.setBounds(126, 69, 132, 21);
		getContentPane().add(cmbEspecie);
		
		JLabel lblEspecie = new JLabel("Especie");
		lblEspecie.setBounds(130, 47, 45, 13);
		getContentPane().add(lblEspecie);
		
		_timMessage = new Timer(5000, new ActionListener() {
			
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 
				 lblMessage.setText("");
				 ((Timer)e.getSource()).stop();
			 }
		});
		
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
					if(_trabajador == null) {
						if(cmbEspecie.getModel().getSelectedItem() == null) throw new IllegalArgumentException("Especie no seleccionada.");
						_trabajador = new Trabajador(txtName.getText(), (Especie) cmbEspecie.getModel().getSelectedItem());
					}
					else {
						_trabajador.setName(txtName.getText()); 
						_trabajador.setEspecie((Especie) cmbEspecie.getModel().getSelectedItem()); 
					}		
					_timMessage.stop();
					_trabajador.Save(); 
					_timMessage.start();  
					lblMessage.setText("Se ha introducido correctamente " + _simpleDateFormat.format(new Date())); 
					
				}
				catch (IOException | SQLException | IllegalArgumentException ex) { 
					_timMessage.start();
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				} 
			}
		}); 
		 
		butSave.setBounds(314, 108, 96, 21);
		getContentPane().add(butSave);
	}
}
