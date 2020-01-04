package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public interface Interfaz extends Remote {

	// Register user
	public boolean registrarUsuario(String username, String password, String name, String surname)
			throws RemoteException;

	// Modificar user
	public boolean modificarUsuario(String password, String name, String surname) throws RemoteException;

	// Delete user
	public boolean borrarUsuario(String username) throws RemoteException;

	// Login user
	public String iniciarSesion(String username, String password) throws RemoteException;

	// Lee de todos los contactos almacenados en la agenda.
	// Ademas si en la query le pasas el nombre que quiere filtrar, tambien busca.
	// public void leerContactos(String query,TableView tableView,
	// ObservableList<ObservableList> data) throws RemoteException;

	public HashMap<Integer, Contactos> leerHashContactos() throws RemoteException;

	// insert contact
	public String insertarContacto(String name, String surname, int telephone, int movil, String ref_user)
			throws RemoteException;

	// update contact
	public boolean modificarContacto(String name, String surname, int telephone, int movil, String ref_user)
			throws RemoteException;

	// Delete contact
	public boolean borrarContacto(int movil) throws RemoteException;

	// Delete all
	public boolean borrarTodo(String query) throws RemoteException;

}
