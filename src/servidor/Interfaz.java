package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Interfaz extends Remote {

	// register
	public boolean registrarCliente(String username, String password, String name, String surname) throws RemoteException;

	// login
	public String iniciarSesion(String username, String password) throws RemoteException;

	// Lee de todos los contactos almacenados en la agenda.
	public HashMap<Integer, Contactos> leerContactos() throws RemoteException;

	// para luego mostrarlos.
	public String mostrarContactos(HashMap<Integer, Contactos> lista) throws RemoteException;

	// Buscar: Permite localizar un contacto por nombre. Si existen varios con dicho
	// nombre, se mostrarán todos.
	public void buscarContacto(HashMap<Integer, Contactos> lista, String name) throws RemoteException;

	// insert
	public String insertarContacto(String name, String surname, int telephone, int movil) throws RemoteException;

	// update
	public boolean modificarContacto(String name, String surname, int telephone, int movil) throws RemoteException;

	// Delete
	public boolean borrarContacto(String name, String surname, int movil) throws RemoteException;

	// Delete all
	public boolean borrarTodo() throws RemoteException;

}
