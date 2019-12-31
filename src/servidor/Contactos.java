package servidor;

public class Contactos {
	int id;
	String name;
	String surname;
	int telephone;
	//movil == primary key
	int movil;

	public Contactos(int id, String name, String surname, int telephone, int movil) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.telephone = telephone;
		this.movil = movil;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public int getMovil() {
		return movil;
	}

	public void setMovil(int movil) {
		this.movil = movil;
	}

	public String toString() {
		return "Name: " + name + "\n" 
				+ "    Surname: " + surname + "\n" 
				+ "    Description: " + telephone+ "\n"
				+ "Movil: " + movil + "\n" ;
	}
}
