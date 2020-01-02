package cliente;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import servidor.ServidorAgenda;

public class ControllerVAgenda extends Controller {
	private ServidorAgenda server;
	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnRegistrarContacto;
	@FXML
	private Button btnModificarContacto;
	@FXML
	private Button btnBorrarContacto;
	@FXML
	private Button btnBorrarTODOS;
	@FXML
	private Button btnRefrescar;
	@FXML
	private Button btnPerfil;
	@FXML
	private Button btnCerrarSesion;
	@FXML
	private TextField txtBuscarContacto;
	@FXML
	private TableView tableView;

	private ObservableList<ObservableList> data;

	@FXML
	private void initialize() {
		server.setTablaContactos(tableView);
		refresh(null);
	}

	public ServidorAgenda getServer() {
		return server;
	}

	public void setServer(ServidorAgenda server) {
		this.server = server;
	}

	public ControllerVAgenda() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
		data = FXCollections.observableArrayList();
		tableView = new TableView();
	}

	public TableView getTableView() {
		return tableView;
	}

	public void clickNuevoContacto(ActionEvent event) {
		selecionado = -1;
		mostrarVentana(event, (Node) event.getSource(), "Contacto.fxml", "Nuevo Contacto", false, false);
	}

	public void clickModificarContacto(ActionEvent event) {
		selecionado = tableView.getSelectionModel().getSelectedIndex();
		if (selecionado != -1) {
			mostrarVentana(event, (Node) event.getSource(), "Contacto.fxml", "Modificar Contacto", false, false);
			refresh(event);
		} else {
			dialog(AlertType.WARNING, "Alerta", "No hay ninguna fila seleccionada",
					"Seleccione una fila para continuar");
		}
	}

	public void clickBorrarContacto(ActionEvent event) {
		selecionado = tableView.getSelectionModel().getSelectedIndex();
		if (selecionado != -1) {

			TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();
			// TableColumn col = pos.getTableColumn();
			TableColumn col = (TableColumn) tableView.getColumns().get(4);
			String data = (String) col.getCellObservableValue(row).getValue();

			// alert to confirm
			Optional<ButtonType> option = dialog(AlertType.CONFIRMATION, "Borrar contacto", "",
					"¿Estas seguro de eliminar el numero: " + data);

			if (option.get() == ButtonType.OK) {
				server.borrarContacto(Integer.parseInt(data));
				System.out.println("contacto con numero: " + data + " borrado");
			}
			refresh(null);
		} else {
			dialog(AlertType.WARNING, "Cuidado", "No hay ninguna fila seleccionada",
					"Seleccione una fila para continuar");
		}
	}

	public void clickBorrarTODOSContactos(ActionEvent event) {
		// alert to confirm
		Optional<ButtonType> option = dialog(AlertType.CONFIRMATION, "Borrar contacto", "",
				"¿Estas seguro de querer eliminar todos los contactos?, No habra vuelta atras");
		if (option.get() == ButtonType.OK) {
			server.borrarTodo();
		}
		refresh(null);
	}

	public void buscarContacto(ActionEvent event) {
		search("SELECT * FROM contacts WHERE name LIKE '%" + txtBuscarContacto.getText() + "%'", tableView, data);
	}

	public void clickRefrescar(ActionEvent event) {
		refresh(null);
	}

	public void refresh(ActionEvent event) {
		search("SELECT * FROM contacts", tableView, data);
	}

	public void clickPerfil(ActionEvent event) {
		cerrarSesion(event);
	}

	public void clickCerrarSesion(ActionEvent event) {
		cerrarSesion(event);
	}
}
