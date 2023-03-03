package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import modelo.Libro;

class TestBuscarTodosLosLibros {

Metodos mc=new Metodos();
	@Test
	void test() {
		Libro []libroP=new Libro[0];
		assertEquals(libroP.length,0);
		libroP=mc.cargarDatosBD();
		assertTrue(libroP[0]!=null);
		assertTrue(libroP.length>0);
	}

}
