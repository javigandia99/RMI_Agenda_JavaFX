package cliente;

import java.rmi.RemoteException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import servidor.ServidorAgenda;

public class ControllerVUsuario extends Controller {
	private ServidorAgenda server;
	@FXML
	private TextField txtAntigua;
	@FXML
	private TextField txtNueva;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	@FXML
	private Button btnBorrar;
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

	public ControllerVUsuario() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
	}

	private void modificarUsuario() {
		try {
			server.modificarUsuario(txtNueva.getText(), txtName.getText(), txtSurname.getText());

		} catch (RemoteException e) {
			dialog(AlertType.WARNING, "Ha ocurrido algo", "Fallo modificando los datos",
					"Pruebe a introducir de nuevo los datos, si el error persiste, introduce unos nuevos");
			e.printStackTrace();
		}
	}

	public void guardar(ActionEvent event) {
		try {
			if (txtAntigua.getText() != txtNueva.getText()) {
				modificarUsuario();
				dialog(AlertType.INFORMATION, "EXITO", "Cambios realizados", "");
			} else {
				dialog(AlertType.WARNING, "Datos introduccidos no aceptados", "La contraseña es igual a la anterior",
						"Pruebe a introducir otra contraseña diferente");
			}

		} catch (Exception e) {
			dialog(AlertType.ERROR, "Error", "Error", "Ha habido un error al guardar los cambios");
		}
		Stage stage = (Stage) btnGuardar.getScene().getWindow();
		stage.close();
		// server.refreshContactos();
	}

	public void cancelar(ActionEvent event) {
		cancelar(event);
	}

	public void clickBorrar(ActionEvent event) {
		/*
		 * try { server.borrarUsuario(getUserLogged()); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */
	}
}
