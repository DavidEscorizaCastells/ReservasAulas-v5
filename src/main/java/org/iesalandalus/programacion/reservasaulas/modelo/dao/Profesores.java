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
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;

public class Profesores {
	private List<Profesor> profesores;
	private static final String NOMBRE_FICHERO_PROFESORES="ficheros"+File.separator+"profesores.dat";

	public Profesores() {
		profesores=new ArrayList<>();
	}

	public Profesores(Profesores otrosProfesores) {
		setProfesores(otrosProfesores);
	}
	
	private void setProfesores(Profesores profesores) {
		if (profesores==null)
			throw new IllegalArgumentException("No se pueden copiar profesores nulos.");
		
		this.profesores=copiaProfundaProfesores(profesores.profesores);	
	}
	
	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> otrosProfesores = new ArrayList<>();
		for (Profesor profesor: profesores) {
			otrosProfesores.add(new Profesor(profesor));
		}
		return otrosProfesores;
	}
	
	public List<Profesor> getProfesores() {
		return copiaProfundaProfesores(profesores);
	}
	
	public int getNumProfesores() {
		return profesores.size();
	}
	
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor==null)
			throw new IllegalArgumentException ("No se puede insertar un profesor nulo.");
		
		if (profesores.contains(profesor)) {
			throw new OperationNotSupportedException("El profesor ya existe.");
		}
		profesores.add(new Profesor(profesor));
	}
	
	public Profesor buscar(Profesor profesor) {
		int indice = profesores.indexOf(profesor);
		if (indice != -1) {
			return new Profesor(profesores.get(indice));
		} else {
			return null;
		}
	}
	
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede borrar un profesor nulo.");
		}
		if (!profesores.remove(profesor)) {
			throw new OperationNotSupportedException("El profesor a borrar no existe.");
		}
	}
	
	
	public List<String> representar() {
		List <String> representacion=new ArrayList<>();
		for (Profesor profesor: profesores) {
			representacion.add(profesor.toString());		
		}
		return representacion;
	}
	
	public void leer() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroProfesores))) {
			Profesor profesor = null;
			do {
				profesor = (Profesor) entrada.readObject();
				insertar(profesor);
			} while (profesor != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de profesores.");
		} catch (EOFException e) {
			System.out.println("Fichero profesores leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void escribir() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroProfesores))){
			for (Profesor profesor : profesores)
				salida.writeObject(profesor);
			System.out.println("Fichero profesores escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de profesores");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
	
}