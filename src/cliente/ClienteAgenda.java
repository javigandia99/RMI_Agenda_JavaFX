package cliente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import servidor.Interfaz;

public class ClienteAgenda {
	public static void main(String[] args) {
		Interfaz agenda = null;
		try {
			System.out.println("Localizando el registro de objetos remotos");
			Registry registry = LocateRegistry.getRegistry("localhost", 5557);
			System.out.println("Obteniendo el stub del objeto remoto");
			agenda = (Interfaz) registry.lookup("Agenda");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (agenda != null) {
			System.out.println("Realizando operaciones con el objeto remoto");	
				try {
					System.out.println(agenda.mostrarContactos(agenda.leerContactos()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
	}
}
