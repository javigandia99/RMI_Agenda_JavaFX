package cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import servidor.ServidorAgenda;

public class ControllerVLogin extends Controller {
	private ServidorAgenda server;
	@FXML
	private TextField txtUser;
	@FXML
	private TextField txtPassword;
	@FXML
	private Label lblResult;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegistrarUserLog;

	public ControllerVLogin() {
		ServidorAgenda server = new ServidorAgenda();
		this.server = server;
	}

	public void setModel(ServidorAgenda server) {
		this.server = server;
	}

	public void clickLogin(ActionEvent event) {
		server.iniciarSesion(txtUser.getText(), txtPassword.getText());
		if (server.isLogin()) {
			goAgenda(event);
		} else {
			lblResult.setText("Usuario o contraseña incorrecto");
		}
	}
}
