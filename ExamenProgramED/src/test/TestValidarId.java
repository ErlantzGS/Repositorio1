package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import modelo.Libro;

class TestValidarId {
	Metodos mc=new Metodos();
	final static int id=1;
	@Test
	void test() {
		Libro prueba=new Libro();
		assertFalse(prueba.getId()>0);
		Libro []libroP=new Libro[0];
		libroP=mc.cargarDatosBD();
		String parametros=id+"\n";
		InputStream in=new ByteArrayInputStream(parametros.getBytes());
		System.setIn(in);
		Scanner sc =new Scanner(System.in);
		prueba=mc.buscarLibro(sc, libroP);
		assertNotEquals(prueba,null);
		assertTrue(prueba.getId()==id);
	}

}
