package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import modelo.Libro;

class TestMostarDatos {

	Metodos mc=new Metodos();
	@Test
	void test() throws IOException {
		Libro []libroP=new Libro[0];
		libroP=mc.cargarDatosBD();
		mc.cargarDatosTXT();
		mc.grabarDatosTXT(libroP);
		File fich=new File(".\\Archivos\\Libros.txt");
		FileReader lector;
		String linea="";
		String libroLeer="";
		try {
			lector = new FileReader(fich);
			try (BufferedReader lectorLineas = new BufferedReader(lector)) {
				while((linea=lectorLineas.readLine())!=null) {
				libroLeer+=linea+"\n";
				}
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int letras=libroLeer.length();
		assertEquals(libroLeer.length(),letras);
		assertTrue(libroLeer!="");
	}

}
