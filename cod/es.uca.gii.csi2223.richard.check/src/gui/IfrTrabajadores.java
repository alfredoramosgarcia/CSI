package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import data.Especie;
import data.Trabajador;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrTrabajadores extends JInternalFrame {
	
	private JTextField txtName;
	private JTable tabResult;

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public IfrTrabajadores(FrmMain frmMain) throws SQLException, IOException {
		
		setClosable(true);
		setResizable(true);
		setTitle("Trabajadores");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panel.add(lblName); 
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblEspecie = new JLabel("Especie");
		panel.add(lblEspecie);
		
		JComboBox<Especie> cmbEspecie = new JComboBox();
		cmbEspecie.setModel(new EspecieListModel(Especie.Search(null)));
		cmbEspecie.setEditable(true);
		panel.add(cmbEspecie);
		
		JButton butSearch = new JButton("Search");
		panel.add(butSearch);
		butSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					tabResult.setModel (
						new TrabajadoresTableModel(Trabajador.Search(txtName.getText(), cmbEspecie.getSelectedItem().toString())));
				}
				catch (SQLException | IOException ex) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}); 
		 
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) { 
					 int iRow = ((JTable)e.getSource()).getSelectedRow();
					 Trabajador trabajador = (Trabajador)((TrabajadoresTableModel)tabResult.getModel()).getData(iRow);
					 if (trabajador != null) {
						try {
							frmMain.ShowInternalFrame(new IfrTrabajador(trabajador), 10, 27, 400, 100);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					 } 
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER); 
	}
}
