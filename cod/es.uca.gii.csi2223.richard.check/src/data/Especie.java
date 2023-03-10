package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Database;

public class Especie {
	
	private Integer _iId;
	private String _sName;
	private Date _dtDeleteAt = null;
	
	public Integer getId() { return _iId; }
	public String getName() { return _sName; }
	public Date getDeleteAt() { return _dtDeleteAt; }
	
	public void setName(String sName) {
		
		if(sName == null || sName.isEmpty()) throw new IllegalArgumentException("Elemento no seleccionado.");  
		_sName = sName;
	} 
	
	public Especie(String sName){ this(null, sName); } 
	Especie(Integer iId, String sName) { setName(sName); _iId = iId; }
	
	public String toString() { return getName(); } 
	
	public static Especie Get(int iId) throws IOException, SQLException {
		
		Connection con = null; 
		ResultSet rs = null; 
		  	
		try {
			con = Database.Connection();
			rs = con.createStatement().executeQuery("SELECT Nombre FROM especie WHERE id = " + iId + ";");
			
			if(rs.next()) return new Especie(iId, rs.getString("Nombre"));
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
			int iInstancias = con.createStatement().executeUpdate("UPDATE especie SET Nombre = " + Database.String2Sql(_sName, true, false) + " WHERE id = " + _iId + ";");
			
			if(iInstancias == 0) {
				con.createStatement().executeUpdate("INSERT INTO especie (Nombre) VALUES (" + Database.String2Sql(_sName, true, false) + ");");
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
			con.createStatement().executeUpdate("DELETE FROM especie WHERE Id = " + _iId +";");
			_dtDeleteAt = new Date();
		}
		catch (SQLException e) { throw e; }
		finally { if(con != null) con.close(); }	 
	}
	
	@SuppressWarnings("null")
	public static List<Especie> Search(String sName) throws SQLException, IOException {
		
		Connection con = null;
		ResultSet rs = null; 
		ArrayList<Especie> aEspecie = new ArrayList<Especie>();
		  
		try { 
			con = Database.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Nombre FROM especie " + Where(sName));			
			
			while(rs.next()) aEspecie.add(new Especie(rs.getInt("Id"), rs.getString("Nombre")));
			
			return aEspecie;
		}
		catch (SQLException e) { throw e; }
		finally {
			if (con != null) con.close();
			if (rs != null) rs.close();   
		}		
	}
	
	private static String Where(String sName) {
		
		if(sName == null)
			return "";
		else
			return "WHERE Nombre LIKE " + Database.String2Sql(sName, true, true);
	}
}

