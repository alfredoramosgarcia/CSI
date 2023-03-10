package data.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import data.Especie;

class EspecieTest {

	@Test	
	void testConstructor() {
		
		Especie especie = new Especie("Ninfa");
		
		assertEquals(especie.getId(), null);
		assertEquals(especie.getName(), "Ninfa");
		assertEquals(especie.getDeleteAt(), null);
	}
	
	@Test 	
	void testSets() {
		
		Especie especie = new Especie("Titan");
		especie.setName("Ninfa");
		assertEquals(especie.getName(), "Ninfa");	
	}
	
	@Test 	
	void testGet() throws IOException, SQLException {
		
		for(int i = 1; i<=3; i++)
			assertEquals(Especie.Get(i).getId(), i);
	
		assertEquals(Especie.Get(1).getName(), "Titan");
		assertEquals(Especie.Get(2).getName(), "Minotauro");
		assertNull(Especie.Get(-1));	
	}
	
	@Test 	
	void testSaveDelete() throws Exception { 
		
		Especie especie = new Especie("Kraken");
		
		especie.Save();
		especie = Especie.Get(especie.getId());
		assertEquals(especie.getName(), "Kraken");
		assertEquals(especie.getId(), especie.getId());
		
		especie.setName("Quimera");
		especie.Save();
		assertEquals(especie.getName(), "Quimera");
		
		especie.Delete();
		assertNotNull(especie.getDeleteAt());
		assertNull(Especie.Get(especie.getId()));
	} 
	
	@Test	
	void testSearch() throws SQLException, IOException {	
		
		List<Especie> aEspecie = Especie.Search(null);
		
		assertEquals(3, aEspecie.size());
		assertEquals("Titan", aEspecie.get(0).getName());
		
		aEspecie = Especie.Search("Minotauro");
		assertEquals(1, aEspecie.size());
		assertEquals(2, aEspecie.get(0).getId());
		
		aEspecie = Especie.Search("Min");
		assertEquals(1, aEspecie.size());
		assertEquals(2, aEspecie.get(0).getId());
		
		aEspecie = Especie.Search("M");
		assertEquals(1, aEspecie.size());
		assertEquals(2, aEspecie.get(0).getId());	
	}
}
