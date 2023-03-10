package data.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import data.Especie;
import data.Trabajador;

class TrabajadorTest {
	
	@Test
	void testConstructor() {
	
		Trabajador trabajador = new Trabajador("Manolo", null);
		
		assertEquals(trabajador.getId(), null);
		assertNull(trabajador.getEspecie());
		assertEquals(trabajador.getName(), "Manolo");
		assertEquals(trabajador.getDeleteAt(), null);
	}
	
	@Test 	
	void testSets() {
		
		Especie especie = new Especie("Quimeras");
		Trabajador trabajador = new Trabajador("Manolo", null);
		
		trabajador.setEspecie(especie);
		trabajador.setName("Roberto");
		assertEquals(trabajador.getName(), "Roberto");
		assertNull(trabajador.getEspecie().getId());
		assertEquals(trabajador.getEspecie().getName(), "Quimeras");
	}
	
	@Test 	
	void testGet() throws IOException, SQLException {
		
		for(int i = 1; i<=3; i++)
			assertEquals(Trabajador.Get(i).getId(), i);
	
		assertEquals(Trabajador.Get(1).getName(), "Atlas");
		assertEquals(Trabajador.Get(2).getName(), "Pontos");
		assertEquals(Trabajador.Get(1).getEspecie().getName(), "Titan");
		assertEquals(Trabajador.Get(2).getEspecie().getName(), "Ciclope");
		assertNull(Trabajador.Get(-1));	
	}
	
	@Test 	
	void testSaveDelete() throws Exception { 
		
		Trabajador trabajador = new Trabajador("O'Connell", Especie.Get(1));
		
		trabajador.Save();
		trabajador = Trabajador.Get(trabajador.getId());
		assertEquals(trabajador.getName(), "O'Connell");
		assertEquals(trabajador.getId(), trabajador.getId());
		
		trabajador.setName("Manuel"); 
		trabajador.Save();
		assertEquals(trabajador.getName(), "Manuel");
		
		trabajador.Delete();
		assertNotNull(trabajador.getDeleteAt());
		assertNull(Trabajador.Get(trabajador.getId()));
	} 
	
	@Test	
	void testSearch() throws SQLException, IOException {	
		
		List<Trabajador> aTrabajador = Trabajador.Search(null,null);
		
		assertEquals(3, aTrabajador.size());
		assertEquals("Atlas", aTrabajador.get(0).getName());
		
		aTrabajador = Trabajador.Search("Atlas", null);
		assertEquals(1, aTrabajador.size());
		assertEquals(1, aTrabajador.get(0).getId());
		
		aTrabajador = Trabajador.Search("Atl", "Titan");
		assertEquals(1, aTrabajador.size());
		assertEquals(1, aTrabajador.get(0).getId());
		
		aTrabajador = Trabajador.Search(null, "Ciclope");
		assertEquals(1, aTrabajador.size());
		assertEquals(2, aTrabajador.get(0).getId());
	}
}
