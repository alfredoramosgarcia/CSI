package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame _frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					FrmMain window = new FrmMain();
					window._frame.setVisible(true);
				} 
				catch (Exception e) { e.printStackTrace(); }
			}
		});
	}
	
	public void ShowInternalFrame(JInternalFrame ifr, int iX, int iY, int iWidth, int iHeight) {
		
		ifr.setBounds(iX, iY, iWidth, iHeight);
		_frame.getContentPane().add(ifr);
		ifr.setVisible(true);
	}
	
	public FrmMain getThis() { return this; } 

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		_frame = new JFrame();
		_frame.setTitle("Gestion del trabajo de titanes y semihumanos");
		_frame.setBounds(100, 100, 450, 300);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Nuevo");
		menuBar.add(mnMenu);
		
		JMenuItem mitNewTrabajador = new JMenuItem("Trabajador");
		mitNewTrabajador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ShowInternalFrame(new IfrTrabajador(), 10, 27, 400, 192);
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mnMenu.add(mitNewTrabajador);
		
		JMenuItem mitNewEspecie = new JMenuItem("Especie");
		mitNewEspecie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowInternalFrame(new IfrEspecie(), 10, 27, 400, 192);
			}
		});
		
		mnMenu.add(mitNewEspecie);
		
		JMenu mnSearch = new JMenu("Buscar");
		menuBar.add(mnSearch);
		
		JMenuItem mitSearchTrabajador = new JMenuItem("Trabajadores");
		mitSearchTrabajador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ShowInternalFrame(new IfrTrabajadores(getThis()), 10, 27, 400, 192);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
		
		mnSearch.add(mitSearchTrabajador);
		
		JMenuItem mitSearchEspecie = new JMenuItem("Especies");
		mitSearchEspecie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowInternalFrame(new IfrEspecies(getThis()), 10, 27, 400, 192); 
			}
		});
		
		mnSearch.add(mitSearchEspecie);
		
		_frame.getContentPane().setLayout(null);
	}
}
 