package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;

public class Aulas {
	private List<Aula>aulas;
	private static final String NOMBRE_FICHERO_AULAS = "ficheros"+File.separator+"aulas.dat";
	
	public Aulas() {
		aulas=new ArrayList<>();
	}
	
	public Aulas(Aulas otrasAulas) {
		setAulas(otrasAulas);
	}
	
	private void setAulas(Aulas aulas) {
		if (aulas==null)
			throw new IllegalArgumentException("No se pueden copiar aulas nulas.");
		
		this.aulas=copiaProfundaAulas(aulas.aulas);			
		
	}
	
	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> otrasAulas = new ArrayList<>();
		for (Aula aula: aulas) {
			otrasAulas.add(new Aula(aula));
		}
		return otrasAulas;
	}
	


	public List<Aula> getAulas() {
		return copiaProfundaAulas(aulas);
	}

	public int getNumAulas() {
		return aulas.size();
	}
	
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula==null)
			throw new IllegalArgumentException ("No se puede insertar un aula nula.");
		if (aulas.contains(aula)) {
			throw new OperationNotSupportedException("El aula ya existe.");
		}
		aulas.add(new Aula(aula));
	}
	
	public Aula buscar(Aula aula) { 
		int indice = aulas.indexOf(aula);
		if (indice != -1) {
			return new Aula(aulas.get(indice));
		} else {
			return null;
		}
	}
	
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		}
		if (!aulas.remove(aula)) {
			throw new OperationNotSupportedException("El aula a borrar no existe.");
		}
	}
	
	public List<String> representar() {
		List <String> representacion=new ArrayList<>();
		for (Aula aula : aulas) {
			representacion.add(aula.toString());
		}
		return representacion;
	}
	
	public void leer() {
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAulas))) {
			Aula aula = null;
			do {
				aula = (Aula) entrada.readObject();
				insertar(aula);
			} while (aula != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de aulas.");
		} catch (EOFException e) {
			System.out.println("Fichero aulas leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void escribir() {
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))){
			for (Aula aula : aulas)
				salida.writeObject(aula);
			System.out.println("Fichero aulas escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de aulas");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
	
}
