package cliente;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import servidor.ServidorAgenda;

public class ControllerVRegistro extends Controller {
	private ServidorAgenda server;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField txtConfirmPassword;

	public ServidorAgenda getServer() {
		return server;
	}

	public void setServer(ServidorAgenda server) {
		this.server = server;
	}

	public ControllerVRegistro() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
	}

	public void clickRegistrarUsuario(ActionEvent event) {
		try {
			if (txtUsername.getText() != "" && txtPassword.getText() != "" && txtName.getText() != ""
					&& txtSurname.getText() != "" && txtUsername.getText() != null && txtPassword.getText() != null
					&& txtName.getText() != null && txtSurname.getText() != null) {

				if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
					registrarUsuario();
					dialog(AlertType.INFORMATION, "Informacion", txtUsername.getText(), "Usuario registrado con exito");
					goLogin(event);

				} else {
					dialog(AlertType.INFORMATION, "Informacion", "Error", "Las contraseñas no coinciden");
				}
			}
		} catch (Exception e) {
			dialog(AlertType.INFORMATION, "Informacion", "Error", "Ha habido un error en el registro del usuario");
		}
	}

	public void registrarUsuario() throws SQLException {
		if (!server.existeUsuario(txtUsername.getText())) {

			server.registrarUsuario(txtUsername.getText(), txtPassword.getText(), txtName.getText(),
					txtSurname.getText());
		} else {
			dialog(AlertType.INFORMATION, "Informacion", "Error",
					"El usuario '" + txtUsername.getText() + "' no esta disponible");
		}
	}

	public void goLogin(ActionEvent event) {
		cambiarVentana(event, (Stage) ((Node) event.getSource()).getScene().getWindow(), "Login.fxml",
				"Iniciar sesion");
	}
}
