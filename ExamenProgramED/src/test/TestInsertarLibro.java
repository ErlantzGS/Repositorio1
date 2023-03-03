package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import modelo.Libro;

class TestInsertarLibro {
	Metodos mc=new Metodos();
	@Test
	void test() {
		Libro prueba=new Libro();
		prueba.setAutor("prueba");
		prueba.setEditorial("prueba");
		prueba.setId(0);
		prueba.setFechaPublicacion(mc.stringToDate("2022-06-010"));
		prueba.setTitulo("prueba");
		mc.insertarLibro(prueba);
		Connection conexion;
		int id=0;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:/exameneval2", "root","");
			Statement comando = conexion.createStatement();
			String query = "Select max(id) from t_libros";
			ResultSet max=comando.executeQuery(query);
			while(max.next()) {
				id=max.getInt(1);
			}
			assertTrue(id>0);
			Statement borrado = conexion.createStatement();
			 query = "DELETE FROM t_libros WHERE id='"+id+"'";
			 borrado.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
