package cliente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import servidor.ServidorAgenda;

public class Controller {
	private ServidorAgenda server;
	private static String userlogged;
	private ObservableList<ObservableList> data;
	static protected int selecionado;

	public ServidorAgenda getServer() {
		return server;
	}

	public void setModel(ServidorAgenda model) {
		this.server = model;
	}

	public Controller() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
		new FXMLLoader();
		data = FXCollections.observableArrayList();

	}

	// Permite volver a la pantalla de inicio y cerrar la sesion
	public void cerrarSesion(ActionEvent event) {
		server.logout();
		mostrarVentana(event, (Node) event.getSource(), "Login.fxml", "Inicar sesion", true, true, -1);
		cancelar(event);
	}

	public void cancelar(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

	}

	/**
	 * Metodo que permite cambiar una nueva vista, abriendo un nuevo stage
	 * 
	 * @param event     ActionEvent que JavaFX pasa al método desde el que se llama
	 *                  a mostrarVentana()
	 * @param node      Node obtenido a partir del evento que pasa JavaFX
	 * @param fxml      Archivo FXML que corresponde con la vista que quiere abrirse
	 * @param title     Título que se le quiere dar a la nueva vista
	 * @param escalable Define si el nuevo stage puede cambiar de tamaño
	 * @param hide      Si se define como true, oculta el stage que se le pasa
	 */
	public void mostrarVentana(ActionEvent event, Node node, String fxml, String title, boolean escalable, boolean hide,
			int selecionado) {
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

	// cambia de stage, manteniendo la ventana
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

	public Optional<ButtonType> dialog(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:Resources/images/logo.png"));
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initStyle(StageStyle.UTILITY);
		Optional<ButtonType> option = alert.showAndWait();
		return option;
	}

	// Permite borra el contenido de una tabla concreta
	public void clearTable(TableView tableView) {
		data.clear();
		tableView.getColumns().clear();
		tableView.getItems().clear();
	}

	public String getusernameLogged(String myusername) {
		userlogged = myusername;
		return userlogged;
	}

	public String getId() {
		return userlogged;
	}
}
