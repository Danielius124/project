package backend;

import java.io.Serializable;

public class Person extends User implements Serializable{
	private String name, surname;

	public Person(String login, String pass, String name, String surname ) {
		super(login, pass);
		this.name = name;
		this.surname = surname;
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
	
	@Override
	public String toString() {
		return "Person [login=" + getLogin() + ", password=" + getPass() + ", name= " + name + ", surname=" + surname + "]" ;
	}

}
