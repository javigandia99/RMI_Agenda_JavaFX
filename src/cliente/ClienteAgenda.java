package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import servidor.Interfaz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;

public class ClienteAgenda extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Iniciar Sesion");
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClienteAgenda.class.getResource("Login.fxml"));
			rootLayout = (AnchorPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("file:Resources/logo.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Interfaz agenda = null;
		try {
			System.out.println("Localizando el registro de objetos remotos");
			Registry registry = LocateRegistry.getRegistry("localhost", 5557);
			System.out.println("Obteniendo el stub del objeto remoto");
			agenda = (Interfaz) registry.lookup("Agenda");
		} catch (Exception e) {
			System.out.println("---------------------------------------");
			System.out.println("No se ha podido conectar con el servidor");
			System.exit(-1);
		}
		if (agenda != null) {
			System.out.println("ABRIENDO AGENDA DE CONTACTOS");
			// lanzamos aplicacion visual
			launch(args);
			System.out.println("Agenda cerrada");

		}
	}
}
