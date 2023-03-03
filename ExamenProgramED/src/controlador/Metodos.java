package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import modelo.Libro;


public class Metodos {
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
	final static String SEPARADOR="=";
	String valor;
	public Libro[] cargarDatosBD() {
		Connection conexion;
		Libro [] arrayL=new Libro[0];
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:/exameneval2", "root","");
				Statement comando = conexion.createStatement();
				String query = "SELECT * FROM t_libros";
				ResultSet result = comando.executeQuery(query);
				while(result.next()) {
					Libro libro=new Libro();
					libro.setId(result.getInt(1));
					libro.setTitulo(result.getString(2));
					libro.setAutor(result.getString(3));
					libro.setEditorial(result.getString(4));
					libro.setFechaPublicacion(result.getDate(5));
					Libro [] arrayLAux=new Libro[arrayL.length+1];
					for(int i=0;i<arrayL.length;i++) {
						arrayLAux[i]=arrayL[i];
					}
					arrayLAux[arrayL.length]=libro;
					arrayL=arrayLAux;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return arrayL;
	}
	public Libro buscarLibro(Scanner sc, Libro[] arrayL) {
		int id=-1;
		Libro libroB=null;
		System.out.println("1.Escriba el ID del libro que desea buscar:");
		String respuesta=sc.nextLine();
		try {
			id=Integer.parseInt(respuesta);
			for(int i=0;i<arrayL.length;i++) {
			if(id==arrayL[i].getId()){
				libroB=arrayL[i];
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return libroB;
	}
	public Libro [] buscarLibrosPorAutor(String autor, Libro[] arrayL) {
		Libro[] arrayLNuevo=new Libro[0];
		
			for(int i=0;i<arrayL.length;i++) {
			if(autor.equals(arrayL[i].getAutor())){
				Libro [] arrayLAux=new Libro[arrayLNuevo.length+1];
				for(int x=0;x<arrayLNuevo.length;x++) {
					arrayLAux[x]=arrayLNuevo[x];
				}
				arrayLAux[arrayLNuevo.length]=arrayL[i];
				arrayLNuevo=arrayLAux;
			}
		}
		return arrayLNuevo;
	}
	public Libro [] buscarLibrosAntesDeFecha(Date comprobarFecha, Libro[] arrayL) {
		Libro[] arrayLNuevo=new Libro[0];
			for(int i=0;i<arrayL.length;i++) {
			if(comprobarFecha.before(arrayL[i].getFechaPublicacion())){
				Libro [] arrayLAux=new Libro[arrayLNuevo.length+1];
				for(int x=0;x<arrayLNuevo.length;x++) {
					arrayLAux[x]=arrayLNuevo[x];
				}
				arrayLAux[arrayLNuevo.length]=arrayL[i];
				arrayLNuevo=arrayLAux;
			}
		}
		return arrayLNuevo;
	}
	public void insertarLibro(Libro libroInsert) {
		// TODO Auto-generated method stub
		Connection conexion;
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:/exameneval2", "root","");
				Statement comando = conexion.createStatement();
				String query = "INSERT INTO t_libros VALUES "
				+ "('"+libroInsert.getId()+"','"+libroInsert.getTitulo()+"',"
				+ "'"+libroInsert.getAutor()+"','"+libroInsert.getEditorial()+"',"
				+ "'"+formatter.format(libroInsert.getFechaPublicacion())+"')";
				 comando.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void grabarDatosTXT(Libro [] arrayL) throws IOException {
		File fich=new File(".\\Archivos\\Libros.txt");
		FileWriter escritor=new FileWriter(fich);
		int cont=0;
		try (BufferedWriter escritorLineas = new BufferedWriter(escritor)) {
			while(escritorLineas!=null && cont<arrayL.length) {
				escritorLineas.write(arrayL[cont].toString());
				cont++;
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Libro [] cargarDatosTXT() {
		Libro [] arrayLibro=new Libro[0];
		Libro libro=new Libro();
		File fich=new File(".\\Archivos\\Libros.txt");
		String linea="",titulo="",autor="",editorial="";
		Date fechaP=null;
		int id=0;
		try {
			FileReader lector=new FileReader(fich);
			try (BufferedReader lectorLineas = new BufferedReader(lector)) {
				while((linea=lectorLineas.readLine())!=null) {
					valor = "";
					if(linea.split(SEPARADOR).length>1)
						valor=linea.split(SEPARADOR)[1];
						else {
							libro=new Libro();
							System.out.println(id+titulo+autor+editorial+fechaP);
							libro.setId(id);
							libro.setTitulo(titulo);
							libro.setAutor(autor);
							libro.setEditorial(editorial);
							libro.setFechaPublicacion(fechaP);
							Libro [] arrayMAux=new Libro[arrayLibro.length+1];
							for(int i=0;i<arrayLibro.length;i++) {
								arrayMAux[i]=arrayLibro[i];
							}
							arrayMAux[arrayLibro.length]=libro;
							arrayLibro=arrayMAux;
						}
					if(linea.contains("ID=")) {
						System.out.println(linea);
						id=Integer.parseInt(linea.split(SEPARADOR)[1]);
					}
					if(linea.contains("Titulo=")) {
						System.out.println(linea);
						titulo=linea.split(SEPARADOR)[1];
					}
					if(linea.contains("Autor=")) {
						System.out.println(linea);
						autor=linea.split(SEPARADOR)[1];
					}
					if(linea.contains("Editorial=")) {
						System.out.println(linea);
						editorial=linea.split(SEPARADOR)[1];
					}
					if(linea.contains(" Fecha de publicacion=")) {
						System.out.println(linea);
						fechaP=stringToDate(linea.split(SEPARADOR)[1]);
					}
				}
			} catch (FileNotFoundException e) {
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<arrayLibro.length;i++){
			arrayLibro[i].toString();
		}
		return arrayLibro;
	}
	public Date stringToDate(String fecha) {
		Date ret = null;
		try {
			String pattern = "yyyy/MM/dd";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
			ret = formatter.parse(fecha);
		} catch (Exception e) {
			// Algo ha ido mal, devolvemos la fecha del sistema
			ret = new Date();
		}
		return ret;
	}
	public void borrarDatosBD() {
		Connection conexion;
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:/exameneval2", "root","");
				Statement comando = conexion.createStatement();
				String query = "DELETE FROM t_libros";
				comando.executeUpdate(query);
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
