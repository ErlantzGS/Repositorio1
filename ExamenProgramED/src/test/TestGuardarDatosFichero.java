package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controlador.Metodos;
import modelo.Libro;

class TestGuardarDatosFichero {
	Metodos mc=new Metodos();
	@Test
	void test() throws IOException {
		Libro []libroP=new Libro[1];
		Libro prueba=new Libro();
		prueba.setAutor("prueba");
		prueba.setEditorial("prueba");
		prueba.setId(0);
		prueba.setFechaPublicacion(mc.stringToDate("2022-06-010"));
		prueba.setTitulo("prueba");
		libroP[0]=prueba;
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
		assertEquals(libroLeer.length(),104);
		
	}

}
