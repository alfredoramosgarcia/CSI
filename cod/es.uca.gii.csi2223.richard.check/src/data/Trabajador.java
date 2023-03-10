package data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Database;

public class Trabajador {
	
	private Integer _iId;
	private String _sName;
	private Especie _especie;
	private Date _dtDeleteAt = null;
	
	public Integer getId() { return _iId; }
	public String getName() { return _sName; }
	public Especie getEspecie() { return _especie; }
	public Date getDeleteAt() { return _dtDeleteAt; }
	
	//Preconditions: The variable sName couldn't be NULL or empty string 
	public void setName(String sName) {
		
		if(sName == null || sName.isEmpty()) throw new IllegalArgumentException("Nombre nulo o vacio.");  
		_sName = sName;
	}  
	
	public  void setEspecie(Especie especie) { _especie = especie; } 
	
	public Trabajador(String sName, Especie especie){ this(null, especie, sName); }
	Trabajador(Integer iId, Especie especie, String sName) { setName(sName); setEspecie(especie); _iId = iId; }
	
	public String toString() { return super.toString() + ":" + getId() + ":" + getEspecie() + ":" + getName(); }
	
	public static Trabajador Get(int iId) throws IOException, SQLException {
		
		Connection con = null; 
		ResultSet rs = null;
		  	
		try {
			con = Database.Connection();
			rs = con.createStatement().executeQuery("SELECT Nombre, especie_id FROM trabajador WHERE Id = " + iId + ";");
			
			if(rs.next()) return new Trabajador(iId,  Especie.Get(rs.getInt("especie_id")), rs.getString("Nombre"));
			else return null;
		} 
		catch (SQLException e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	public void Save() throws IOException, SQLException {
		 
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Database.Connection(); 
			int iInstancias = con.createStatement().executeUpdate("UPDATE trabajador SET especie_id = " + _especie.getId() + ", Nombre = " + Database.String2Sql(_sName, true, false) + " WHERE id = " + _iId + ";");
			
			if(iInstancias == 0) {
				con.createStatement().executeUpdate("INSERT INTO trabajador (Especie_id, Nombre) VALUES (" + _especie.getId() + ", " + Database.String2Sql(_sName, true, false) + ");");
				_iId = Database.LastId(con); 
			}		
		}
		catch (SQLException e) { throw e; }
		finally {
			if (rs != null) rs.close(); 
			if (con != null) con.close();
		}
	}
	
	public void Delete() throws Exception {
		
		if(_dtDeleteAt != null || _iId == null) throw new IllegalArgumentException ("Elemento ya eliminado o no insertado previamente.");
		
		Connection con = null;

		try {
			con = Database.Connection();
			con.createStatement().executeUpdate("DELETE FROM trabajador WHERE Id = " + _iId + ";");
			_dtDeleteAt = new Date();
		}
		catch (SQLException e) { throw e; }
		finally { if(con != null) con.close(); }	 
	} 
	 
	@SuppressWarnings("null")
	public static List<Trabajador> Search(String sName, String sEspecie) throws SQLException, IOException {
		
		Connection con = null;
		ResultSet rs = null; 
		ArrayList<Trabajador> aTrabajador = new ArrayList<Trabajador>();
		  
		try { 
			con = Database.Connection();
			rs = con.createStatement().executeQuery("SELECT trabajador.Id, trabajador.especie_id, trabajador.Nombre, especie.Nombre "
					+ "FROM trabajador INNER JOIN especie ON trabajador.especie_id = especie.Id " + Where(sName, sEspecie) + ";");
			
			while(rs.next()) aTrabajador.add(new Trabajador(rs.getInt("Id"), Especie.Get(rs.getInt("especie_id")), rs.getString("Nombre")));
			
			return aTrabajador;
		}
		catch (SQLException e) { throw e; }
		finally {
			if (con != null) con.close();
			if (rs != null) rs.close();   
		}		
	} 
	
	private static String Where(String sName, String sEspecie) {
		
		String sResult = "";
		
		if(sName != null) sResult = "trabajador.Nombre LIKE " + Database.String2Sql(sName, true, true) + " AND ";
		if(sEspecie != null) sResult = "especie.Nombre LIKE " + Database.String2Sql(sEspecie, true, true) + " AND ";
		if(sResult.length() != 0) {
			sResult = "where " + sResult;
			sResult = sResult.substring(0, sResult.length()-5);
		}
		return sResult;
	}
}