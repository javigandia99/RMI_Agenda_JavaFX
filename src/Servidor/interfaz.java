package Servidor;

import java.util.HashMap;

public interface interfaz {

	// register
	public boolean registrarCliente(String username, String password, String name, String surname);

	// login
	public String iniciarSesion(String username, String password);

	// Lee de todos los contactos almacenados en la agenda.
	public HashMap<Integer, Contactos> leerContactos();

	// para luego mostrarlos.
	public void mostrarContactos(HashMap<Integer, Contactos> lista);

	// Buscar: Permite localizar un contacto por nombre. Si existen varios con dicho
	// nombre, se mostrarán todos.
	public HashMap<Integer, Contactos> buscarContacto(HashMap<Integer, Contactos> lista, String name);

	// insert
	public String insertarContacto(String name, String surname, int telephone, int movil);

	// update
	public boolean modificarContacto(String name, String surname, int telephone, int movil);

	// Delete
	public boolean borrarContacto(String name, String surname, int movil);

	// Delete all
	public boolean borrarTodo();

}
