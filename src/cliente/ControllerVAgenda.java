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
	private ServidorAgenda model;
	@FXML
	private Button btnMenuAdmin;
	@FXML
	private Button btnMenuListadoPaciente;
	@FXML
	private Button btnBuscador;
	@FXML
	private Button btnRegistrarPaciente;
	@FXML
	private Button btnModificarPaciente;
	@FXML
	private Button btnBorrarPaciente;
	@FXML
	private Button btnRefrescar;
	@FXML
	private TextField txtBuscarPaciente;
	@FXML
	private TableView tableView;
	private ObservableList<ObservableList> data;

	@FXML
	private void initialize() {
		model.setTablaContactos(tableView);
		refresh(null);
	}

	public ServidorAgenda getModel() {
		return model;
	}

	public void setModel(ServidorAgenda model) {
		this.model = model;
	}

	public ControllerVAgenda() {
		ServidorAgenda model = new ServidorAgenda();
		this.model = model;
		data = FXCollections.observableArrayList();
		tableView = new TableView();
	}

	public void clickRefrescar(ActionEvent event) {
		refresh(null);
	}

	public void BuscarPaciente(ActionEvent event) {
		consultar("SELECT * FROM contacs WHERE name LIKE '%" + txtBuscarPaciente.getText() + "%'", tableView, data);
	}

	public TableView getTableView() {
		return tableView;
	}

	public void borrarPaciente(ActionEvent event) {
		selecionado = tableView.getSelectionModel().getSelectedIndex();
		if (selecionado != -1) {

			TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();
			// TableColumn col = pos.getTableColumn();
			TableColumn col = (TableColumn) tableView.getColumns().get(3);
			String data = (String) col.getCellObservableValue(row).getValue();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Borrar paciente");
			alert.setHeaderText("");
			alert.setContentText("¿Estas seguro de eliminar el paciente " + data);
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == ButtonType.OK) {
				System.out.println("contacto borrado");
				//borrarContacto del servidor
				//model.borrarContacto("0tt", "0tt", data);
			}
			refresh(null);
		} else {
			dialog("Cuidado", "No hay ninguna fila seleccionada", "Seleccione una fila para continuar");
		}
	}

	public void clickNewPaciente(ActionEvent event) {
		selecionado = -1;
		mostrarVentana(event, (Node) event.getSource(), "../vistas/RegistroPaciente.fxml", "Nuevo Paciente", false,
				false);
	}

	public void clickModificarPaciente(ActionEvent event) {
		selecionado = tableView.getSelectionModel().getSelectedIndex();
		if (selecionado != -1) {
			mostrarVentana(event, (Node) event.getSource(), "../vistas/RegistroPaciente.fxml", "Nuevo Paciente", false,
					false);
			refresh(event);
		} else {
			dialog("Alerta", "No hay ninguna fila seleccionada", "Seleccione una fila para continuar");
		}
	}

	public void refresh(ActionEvent event) {
		consultar("SELECT * FROM contacts", tableView, data);
	}
}
