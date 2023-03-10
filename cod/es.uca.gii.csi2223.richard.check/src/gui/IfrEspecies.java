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

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IfrEspecies extends JInternalFrame {
	
	private JTextField txtName;
	private JTable tabResult;

	/**
	 * Create the frame.
	 */
	public IfrEspecies(FrmMain frmMain) {
		
		setClosable(true);
		setResizable(true);
		setTitle("Especies");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panel.add(lblName);
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);
		
		JButton butSearch = new JButton("Search");
		butSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					tabResult.setModel (
						new EspeciesTableModel(Especie.Search(txtName.getText())));
				}
				catch (SQLException | IOException ex) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}); 
		panel.add(butSearch);
		
		tabResult = new JTable(); 
		tabResult.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) { 
					 int iRow = ((JTable)e.getSource()).getSelectedRow();
					 Especie especie = (Especie)((EspeciesTableModel)tabResult.getModel()).getData(iRow);
					 if (especie != null)
						 frmMain.ShowInternalFrame(new IfrEspecie(especie), 10, 27, 400, 100);
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER); 
	}
}
