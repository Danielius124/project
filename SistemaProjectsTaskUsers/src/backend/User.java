package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private int id;
	private String login, pass, eMail;
	private static int idCounter = 1;
	private boolean active = true;
	private ArrayList<Project> projects = new ArrayList();

	public User(String login, String pass) {
		
		this.login = login;
		this.pass = pass;
		this.id = idCounter;
		idCounter++;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void addProject(Project p) {
		projects.add(p);
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}
	

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", pass=" + pass + ", active=" + active + ", projects="
				+ projects +  "]";
	}

}
