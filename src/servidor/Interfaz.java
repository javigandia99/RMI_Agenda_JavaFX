package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Interfaz extends Remote {

	// Register usuario
	public boolean registrarUsuario(String username, String password, String name, String surname) throws RemoteException;

	// Delete usuario
	public boolean borrarUsuario(String username) throws RemoteException;

	// Login
	public String iniciarSesion(String username, String password) throws RemoteException;

	// Lee de todos los contactos almacenados en la agenda.
	public HashMap<Integer, Contactos> leerContactos() throws RemoteException;

	// El metodo de mostrar contactos, lo pase al cliente a la vista
	// public void mostrarContactos(HashMap<Integer, Contactos> lista) throws
	// RemoteException;

	// El metodo de buscarContacto, lo pase al cliente a la vista
	// Buscar: Permite localizar un contacto por nombre. Si existen varios con dicho
	// nombre, se mostrarán todos.
	// public void buscarContacto(HashMap<Integer, Contactos> lista, String name)
	// throws RemoteException;

	// insert
	public String insertarContacto(String name, String surname, int telephone, int movil) throws RemoteException;

	// update
	public boolean modificarContacto(String name, String surname, int telephone, int movil) throws RemoteException;

	// Delete
	public boolean borrarContacto(int movil) throws RemoteException;

	// Delete all
	public boolean borrarTodo() throws RemoteException;

}
