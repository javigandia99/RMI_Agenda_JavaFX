package cliente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import servidor.ServidorAgenda;

public class Controller {
	private FXMLLoader loader;
	private ServidorAgenda model;
	private ObservableList<ObservableList> data;
	static protected int selecionado;
	private Stage primaryStage;
	private TableView tablaPacientes;
	private ObservableList<ObservableList> dataPacientes;

	public ServidorAgenda getModel() {
		return model;
	}

	public void setModel(ServidorAgenda model) {
		this.model = model;
	}

	public Controller() {
		ServidorAgenda model = new ServidorAgenda();
		this.model = model;
		loader = new FXMLLoader();
		data = FXCollections.observableArrayList();
	}

	public void goAgenda(ActionEvent event) {
		cambiarVentana(event, (Stage) ((Node) event.getSource()).getScene().getWindow(),
				"Agenda.fxml", "Agenda de contactos");
	}

	// Permite volver a la pantalla de inicio y cerrar la sesión
	public void cerrarSesion(ActionEvent event) {
		model.logout();
		mostrarVentana(event, (Node) event.getSource(), "Login.fxml", "Log in", true, true);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	public void clickCancelar(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	/**
	 * Método que permite cambiar una nueva vista, abriendo un nuevo stage
	 * 
	 * @param event     ActionEvent que JavaFX pasa al método desde el que se llama
	 *                  a mostrarVentana()
	 * @param node      Node obtenido a partir del evento que pasa JavaFX
	 * @param fxml      Archivo FXML que corresponde con la vista que quiere abrirse
	 * @param title     Título que se le quiere dar a la nueva vista
	 * @param escalable Define si el nuevo stage puede cambiar de tamaño
	 * @param hide      Si se define como true, oculta el stage que se le pasa
	 */
	public void mostrarVentana(ActionEvent event, Node node, String fxml, String title, boolean escalable,
			boolean hide) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("file:Resources/logo.png"));
			if (escalable) {
				stage.setResizable(true);

			} else {
				stage.setResizable(false);
			}
			if (hide) {
				((Node) (event.getSource())).getScene().getWindow().hide();

			} else {
				((Node) (event.getSource())).getScene().getWindow();
			}
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mostrarVentanaedicion(ActionEvent event, Node node, String fxml, String title, boolean escalable,
			boolean hide, int selecionado) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("file:Resources/logo.png"));
			if (escalable) {
				stage.setResizable(true);

			} else {
				stage.setResizable(false);
			}
			if (hide) {
				((Node) (event.getSource())).getScene().getWindow().hide();

			} else {
				((Node) (event.getSource())).getScene().getWindow();
			}
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que permite cambiar a una nueva vista sin cambiar de stage
	 * 
	 * @param event ActionEvent que JavaFX pasa al método desde el que se llama a
	 *              cambiarVentana()
	 * @param stage Stage obtenido a partir del evento que pasa JavaFX
	 * @param fxml  Archivo FXML que corresponde con la vista que quiere abrirse
	 * @param title Título que se le quiere dar a la nueva vista
	 */
	public void cambiarVentana(ActionEvent event, Stage stage, String fxml, String title) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.setHeight(stage.getHeight());
			stage.setWidth(stage.getWidth());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permite mostrar un diálogo que espera hasta que lo cierra el usuario
	 * 
	 * @param title   Define el título del diálogo
	 * @param header  Establece el texto de la cabecera del diálogo, puede estar
	 *                vacío si se trata de un mensage más simple
	 * @param content Define el contenido del diálogo
	 */
	public void dialog(String title, String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:Resources/images/logo.png"));
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();

	}

	/**
	 * Permite llenar una tabla con consultas que no requieran de una comprobación,
	 * como obtener todos los datos de una tabla, o hacer una búsqueda
	 * 
	 * @param sql       La consulta con la que se llenará la tabla
	 * @param tableView La tabla que va a ser completada con los datos de la
	 *                  consulta
	 * @param data      El ObservableList que utiliza la tabla para obtener los
	 *                  datos
	 */
	public void consultar(String sql, TableView tableView, ObservableList<ObservableList> data) {
		clearTable(tableView);

		try {
			Connection connection = model.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);

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

				while (rs.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					for (int z = 1; z <= rs.getMetaData().getColumnCount(); z++) {
						row.add(rs.getString(z));
					}
					data.add(row);
				}
				tableView.setItems(data);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Permite borra el contenido de una tabla concreta
	public void clearTable(TableView tableView) {
		data.clear();
		tableView.getColumns().clear();
		tableView.getItems().clear();
	}
}
