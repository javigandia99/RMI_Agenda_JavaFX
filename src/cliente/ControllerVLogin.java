package cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import servidor.ServidorAgenda;

public class ControllerVLogin extends Controller {
	private ServidorAgenda server;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegistrarUsuario;

	public ControllerVLogin() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
	}

	public void setServer(ServidorAgenda server) {
		this.server = server;
	}

	public void clickLogin(ActionEvent event) {
		server.iniciarSesion(txtUsername.getText(), txtPassword.getText());
		if (server.isLogin()) {
			getusernameLogged(txtUsername.getText());
			mostrarVentana(event, (Node) event.getSource(), "Agenda.fxml", "Agenda de Contactos", false, true, -1);
		} else {
			dialog(AlertType.INFORMATION, "Informacion", "Error", "Usuario o contraseņa incorrectos");
		}
	}

	public void clickRegistro(ActionEvent event) {
		cambiarVentana(event, (Stage) ((Node) event.getSource()).getScene().getWindow(), "Registro.fxml", "Registro");
	}
}
