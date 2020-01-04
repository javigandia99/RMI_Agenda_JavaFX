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
import java.util.Properties;
import java.util.Map.Entry;
import cliente.ControllerVAgenda;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ServidorAgenda implements Interfaz, Remote {
	private Connection conexione;
	private File config;
	private Properties properties;
	private InputStream input;
	private OutputStream output;
	private boolean login;
	private String correctUsername;
	protected HashMap<Integer, Contactos> listadobd;
	private TableView tablaContactos;
	private String name;
	private String surname;
	private int telephone;
	private int movil;
	protected ControllerVAgenda agenda;

	public ServidorAgenda() {
		config = new File("src/configuracion.ini");
		properties = new Properties();
		output = null;
		login = false;
		getConnection();
	}

	// Conexion base de datos sql desde configuacion.ini
	public Connection getConnection() {
		String url = getProperty("url");
		String user = getProperty("user");
		String pass = getProperty("pass");
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver);
			conexione = DriverManager.getConnection(url, user, pass);
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

	// LOGIN-REGISTRO
	@Override
	public boolean registrarUsuario(String username, String password, String name, String surname) {
		String query = "INSERT INTO users (username, password, name, surname) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conexione.prepareStatement(query);

			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setString(4, surname);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Comprueba si existe un usuario
	public boolean existeUsuario(String username) throws SQLException {
		String query = "SELECT username FROM users WHERE username LIKE ?";
		PreparedStatement stmt = conexione.prepareStatement(query);
		stmt.setString(1, username);
		ResultSet rset = stmt.executeQuery();
		boolean exist = rset.next();
		rset.close();
		stmt.close();
		if (exist) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean modificarUsuario(String password, String name, String surname) throws RemoteException {
		boolean state;

		String query = "UPDATE users SET password = ?, name = ?, surname = ?  WHERE username LIKE '"
				+ correctUsername.toString() + "'";
		try {
			PreparedStatement stmt = conexione.prepareStatement(query);

			stmt.setString(1, surname);
			stmt.setString(2, name);
			stmt.setString(3, surname);
			stmt.executeUpdate();
			stmt.close();
			state = true;
		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		}
		return state;
	}

	@Override
	public boolean borrarUsuario(String username) throws RemoteException {
		try {
			String query = "DELETE FROM users WHERE username LIKE ?";
			PreparedStatement stmt = conexione.prepareStatement(query);
			stmt.setString(1, username);
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	@Override
	public String iniciarSesion(String username, String password) {
		correctUsername = " ";
		String correctPassword = " ";

		try {
			String query = "SELECT username, password FROM users WHERE username = ?  AND password = ?";
			PreparedStatement stmt = conexione.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rset = stmt.executeQuery();
			rset.next();
			correctUsername = rset.getString(1);
			correctPassword = rset.getString(2);
			rset.close();
			stmt.close();
		} catch (SQLException s) {
			login = false;
		}

		login = correctUsername.equals(username) && correctPassword.equals(password);
		if (login) {
			return correctUsername.toString();
		} else {
			return "Username o password incorrecto";
		}

	}

	public boolean isLogin() {
		return login;
	}

	public void logout() {
		conexione = null;
		login = false;
		correctUsername = "";
	}

	// AGENDA-CONTACTOS

	/**
	 * Permite llenar una tabla con consultas que no requieran de una comprobacion,
	 * como obtener todos los datos de una tabla, o hacer una busqueda
	 * 
	 * @param sql       La consulta con la que se llena la tabla
	 * @param tableView La tabla que va a ser completada con los datos de la
	 *                  consulta
	 * @param data      El ObservableList que utiliza la tabla para obtener los
	 *                  datos
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void leerContactos(String query, TableView tableView, ObservableList<ObservableList> data)
			throws RemoteException {
		// clear table
		data.clear();
		tableView.getColumns().clear();
		tableView.getItems().clear();

		try {
			ResultSet rs = getConnection().createStatement().executeQuery(query);
			// table column added dynamically
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				tableView.getColumns().addAll(col);

				// data added to ObservableList
				while (rs.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					for (int z = 1; z <= rs.getMetaData().getColumnCount(); z++) {
						row.add(rs.getString(z));
					}
					data.add(row);
				}
				// added to tableview
				tableView.setItems(data);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void setTablaContactos(TableView<?> tableView) {
		this.tablaContactos = tableView;
	}

	@Override
	public HashMap<Integer, Contactos> leerHashContactos() throws RemoteException {
		listadobd = new HashMap<Integer, Contactos>();
		Contactos contact;
		int count = 0;
		try {
			String query = "SELECT * FROM contacts";
			PreparedStatement stmt = conexione.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count++;
				name = rs.getString(2);
				surname = rs.getString(3);
				telephone = rs.getInt(4);
				movil = rs.getInt(5);
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
	public String insertarContacto(String name, String surname, int telephone, int movil, String ref_user) {
		String result = null;
		try {

			HashMap<Integer, Contactos> listaadd;

			try {
				listaadd = leerHashContactos();

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
						.prepareStatement("INSERT INTO contacts (name,surname,telephone,movil,ref_user) value ('" + name
								+ "','" + surname + "','" + telephone + "','" + movil + "','" + ref_user + "')");
				stmt.executeUpdate();
				result = "Contacto correctamente introducido con el numero: " + movil;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean modificarContacto(String name, String surname, int telephone, int movil, String ref_user) {
		boolean state;
		String query = "UPDATE contacts SET name = ?, surname = ?, telephone = ?, movil = ?  WHERE movil LIKE ? AND ref_user LIKE '"
				+ ref_user + "'";
		try {
			PreparedStatement stmt = conexione.prepareStatement(query);

			stmt.setString(1, name);
			stmt.setString(2, surname);
			stmt.setInt(3, telephone);
			stmt.setInt(4, movil);
			stmt.executeUpdate();
			stmt.close();
			state = true;
		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		}
		return state;
	}

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
	public boolean borrarContacto(int movil) {
		boolean state = false;
		try {
			if (notexistMovil(movil)) {
				System.out.println("No existe el usuario escrito");
			} else {
				String query = "DELETE FROM contacts WHERE movil LIKE ('" + movil + "')";
				PreparedStatement stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
				stmt.close();
				state = true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return state;
	}

	@Override
	public boolean borrarTodo(String query) {
		boolean state = false;
		PreparedStatement stmt;
		try {
			stmt = conexione.prepareStatement(query);
			stmt.executeUpdate();
			state = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return state;
	}

	// MAIN
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
			System.out.println("");
			System.out.println("Se le da un nombre único: Agenda");
			reg.rebind("Agenda", (Interfaz) UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {

			System.out.println("ERROR:No se ha podido inscribir el objeto servidor.");
			e.printStackTrace();
		}
	}
}
