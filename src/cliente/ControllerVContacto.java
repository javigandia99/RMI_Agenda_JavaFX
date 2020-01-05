package cliente;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import servidor.ServidorAgenda;

public class ControllerVContacto extends Controller {
	private ServidorAgenda server;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	@FXML
	private TextField txtTelephone;
	@FXML
	private TextField txtMovil;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;

	public ServidorAgenda getServer() {
		return server;
	}

	public void setServer(ServidorAgenda server) {
		this.server = server;
	}

	public ControllerVContacto() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
	}

	public void registrarContacto() throws SQLException {
		if (server.notexistMovil(Integer.parseInt(txtMovil.getText()))) {
			server.insertarContacto(txtName.getText(), txtSurname.getText(), Integer.parseInt(txtTelephone.getText()),
					Integer.parseInt(txtMovil.getText()), getId());
		} else {
			dialog(AlertType.INFORMATION, "Informacion", "Error",
					"El contacto con el movil: '" + txtMovil.getText() + "' ya existe");
		}
	}

	private void modificarContacto() {
		server.modificarContacto(txtName.getText(), txtSurname.getText(), Integer.parseInt(txtTelephone.getText()),
				Integer.parseInt(txtMovil.getText()), getId());
	}

	public void guardar(ActionEvent event) {
		try {
			if (selecionado != -1) {
				modificarContacto();
				dialog(AlertType.INFORMATION, "Informacion", " MODIFICACION COMPLETADA", "Modificado contacto: "
						+ txtName.getText() + " " + txtSurname.getText() + " con numero: " + txtMovil.getText());
			} else if (selecionado == -1) {
				registrarContacto();
				dialog(AlertType.INFORMATION, "Informacion", "REGISTRO COMPLETADO", "Nuevo contacto: "
						+ txtName.getText() + " " + txtSurname.getText() + " con numero: " + txtMovil.getText());
			}

		} catch (Exception e) {
			dialog(AlertType.INFORMATION, "Informacion", "Error", "Ha habido un error al guardar el contacto");
		}

		Stage stage = (Stage) btnGuardar.getScene().getWindow();
		stage.close();

	}

	public void clickCancelar(ActionEvent event) {
		cancelar(event);
	}
}
