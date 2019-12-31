package servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ServidorAgenda implements Interfaz, Remote {
	private Connection conexione;
	private File config;
	private Properties properties;
	private InputStream input;
	private OutputStream output;
	protected HashMap<Integer, Contactos> listadobd;
	private String name;
	private String surname;
	private int telephone;
	private int movil;

	public ServidorAgenda() {
		config = new File("src/configuracion.ini");
		properties = new Properties();
		output = null;
		getConnection();
	}

	public Connection getConnection() {
		String url = getProperty("url");
		String user = getProperty("user");
		String pass = getProperty("pass");
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver);
			conexione = DriverManager.getConnection(url, user, pass);
			System.out.println("CONEXIÓN CON BBDD SQL CORRECTA");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: DRIVER ");
			System.exit(-1);

		} catch (SQLException e) {
			System.out.println("ERROR: FALLO EN CONEXION DE BASE DE DATOS");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("ERROR: GENERAL");
			e.printStackTrace();
			System.exit(-1);
		}
		return conexione;
	}

	public String getProperty(String key) {
		input = null;
		try {
			input = new FileInputStream(config);
			properties.load(input);
		} catch (Exception e) {
			System.out.println("FALLO EN LECTURA DE FICHERO.INI" + e);
		}
		String property = properties.getProperty(key);
		return property;
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
		try {
			output = new FileOutputStream(config);
			properties.store(output, "");

		} catch (IOException e) {
			System.out.println("Algo salió mal");
		}
	}

	@Override
	public boolean registrarCliente(String username, String password, String name, String surname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String iniciarSesion(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, Contactos> leerContactos() throws RemoteException {
		listadobd = new HashMap<Integer, Contactos>();

		Contactos contact;
		int count = 0;
		try {
			String query = "SELECT * FROM contacts";
			PreparedStatement stmt = conexione.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count++;
				name = rs.getString(1);
				surname = rs.getString(2);
				telephone = rs.getInt(3);
				movil = Integer.parseInt(rs.getString(4));
				contact = new Contactos(count, name, surname, telephone, movil);
				listadobd.put(count, contact);
			}

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return listadobd;

	}

	@Override
	public String mostrarContactos(HashMap<Integer, Contactos> lista) {
		String state = null;
		Iterator<Entry<Integer, Contactos>> it = lista.entrySet().iterator();
		System.out.println("\nDatos:");
		System.out.println("____________________________________________\n");
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			state = ""+pair.getKey() + " = " + pair.getValue().toString()+"";
			it.remove(); // avoids a ConcurrentModificationException
		}
		return state;
	}

	@Override
	public void buscarContacto(HashMap<Integer, Contactos> lista, String name) {
		Map<Integer, Contactos> filtermap = lista.entrySet().stream()
				.filter(s -> s.getValue().getName().equalsIgnoreCase(name))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println(filtermap.toString());
	}

	@Override
	public String insertarContacto(String name, String surname, int telephone, int movil) {
		String result = null;
		try {

			HashMap<Integer, Contactos> listaadd;

			try {
				listaadd = leerContactos();

				PreparedStatement stmt;
				stmt = conexione.prepareStatement("SELECT * FROM contacts WHERE movil LIKE '" + movil + "'");
				ResultSet rset = stmt.executeQuery();

				while (rset.next()) {
					for (Entry<Integer, Contactos> entry : listaadd.entrySet()) {
						if (entry.getValue().getMovil() == movil) {
							result = "Numero de movil repetido";

						}
					}
				}
				stmt = conexione
						.prepareStatement("insert into contacs (db_username,db_password,db_description) value ('" + name
								+ "','" + surname + "','" + telephone + "','" + movil + "')");
				stmt.executeUpdate();
				result = "Contacto correctamente introducido";
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean modificarContacto(String name, String surname, int telephone, int movil) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * @Override public boolean modificarContacto(String name, String surname, int
	 * telephone, int movil) { boolean state = false; try { if
	 * (notexistUser(up_username)) { System.out.println("USERNAME NO EXISTE"); state
	 * = false; } System.out.println("El username NUNCA se va a poder Modificar");
	 * 
	 * String query2 = "UPDATE user set db_password =  '" + newPassword +
	 * "', db_description =  '" + newDescription + "' WHERE db_username = '" +
	 * myusername + "'";
	 * 
	 * PreparedStatement stmt = conexione.prepareStatement(query2);
	 * stmt.executeUpdate(query2); System.out.println("Update correcto!"); state =
	 * true;
	 * 
	 * } catch (SQLException e) { System.out.println(e.getMessage()); } return
	 * state; }
	 */

	public boolean notexistMovil(int movil) throws SQLException {
		String query = "SELECT movil FROM contacts WHERE movil LIKE ?";
		PreparedStatement stmt = conexione.prepareStatement(query);
		stmt.setInt(1, movil);
		ResultSet rset = stmt.executeQuery();
		boolean exist = rset.next();
		rset.close();
		stmt.close();
		if (exist) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean borrarContacto(String name, String surname, int movil) {
		boolean state = false;
		try {
			if (notexistMovil(movil)) {
				System.out.println("No existe el usuario escrito");
				return false;
			} else {
				System.out.println("Borrando...");
				String query = "DELETE FROM contacts WHERE movil LIKE ('" + movil + "')";
				PreparedStatement stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Delete correcto!");
				stmt.close();
				state = true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return state;
	}

	@Override
	public boolean borrarTodo() {
		boolean state = false;
		String query = "DELETE FROM contacs";
		PreparedStatement stmt;
		try {
			stmt = conexione.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Delete ALL correcto!");
			state = true;
		} catch (SQLException e) {
			System.err.println("Fallo en ejecutar: delete all");
			e.printStackTrace();
			state = false;
		}

		return state;
	}

	public static void main(String[] args) {
		Registry reg = null;
		try {
			System.out.println("Creando el registro de objetos,escuchando en el puerto 5557");
			reg = LocateRegistry.createRegistry(5557);
		} catch (Exception e) {
			System.out.println("ERROR:No se ha podido crear el registro");
			e.printStackTrace();
		}
		System.out.println("Creando el objeto servidor");
		ServidorAgenda serverObject = new ServidorAgenda();
		try {
			System.out.println("Inscribiendo el objeto servidor en el registro");
			System.out.println("Se le da un nombre único: Agenda");
			reg.rebind("Agenda", (Interfaz) UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {

			System.out.println("ERROR:No se ha podido inscribir el objeto servidor.");
			e.printStackTrace();
		}
	}
}
