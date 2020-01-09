package backend;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ToDoList implements Serializable {
	private ArrayList<User> users = new ArrayList();
	private ArrayList<Project> projects = new ArrayList();
	private ArrayList<Task> tasks = new ArrayList();
	private User loggedIn = null;
	private int mainUserId = 0;
	private Connection con = null;
	private String type = "";

	public void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/4lab", "root", "");
			if (!con.isClosed()) {
				System.out.println("Prisijunge");
			}
		} catch (Exception e) {
			System.out.println("Cannont connect to DataBase!!!");
		}
	}

	public void dissconnectFromDB() {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println("Cannot logoff from database");
		}
	}

	public Person registerPerson(String login, String pass, String name, String surname)
			throws ObjectNotExists, SQLException {
		if (login.length() > 3 && pass.length() > 6 && !login.isEmpty() && !pass.isEmpty() && !name.isEmpty()
				&& !surname.isEmpty() && isExists(login) == false) {
			System.out.println("Veikia");
			Person newPerson = new Person(login, pass, name, surname);
			int userID = 0;
			connectToDB();

			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO users (user_login, user_pass, user_active, user_type)" + "VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, login);
			ps.setString(2, pass);
			ps.setBoolean(3, newPerson.isActive());
			ps.setString(4, "Person");
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				userID = rs.getInt(1);
			}
			ps = con.prepareStatement("INSERT INTO persons (name, surname, user_id)" + "VALUES(?, ?, ?)");
			ps.setString(1, name);
			ps.setString(2, surname);
			ps.setInt(3, userID);
			ps.executeUpdate();

			rs.close();
			ps.close();

			dissconnectFromDB();

		} else
			throw new ObjectNotExists("Incorect login data");
		return null;

	}

	public ArrayList<User> gettAllUsersForFileReading() {
		System.out.println(users);
		return users;
	}

	public Company registerCompany(String login, String pass, String title) throws SQLException, ObjectNotExists {

		if (login.length() > 3 && pass.length() > 6 && !login.isEmpty() && !pass.isEmpty() && !title.isEmpty()
				&& isExists(login) == false) {
			System.out.println("Veikia");
			Company newCompany = new Company(login, pass, title);
			int userID = 0;
			connectToDB();

			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO users (user_login, user_pass, user_active, user_type)" + "VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, login);
			ps.setString(2, pass);
			ps.setBoolean(3, newCompany.isActive());
			ps.setString(4, "Company");
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				userID = rs.getInt(1);
			}
			ps = con.prepareStatement("INSERT INTO company (id, title)" + "VALUES(?, ?)");
			ps.setInt(1, userID);
			ps.setString(2, title);
			ps.executeUpdate();

			rs.close();
			ps.close();

			dissconnectFromDB();

		} else
			throw new ObjectNotExists("Incorect login data");
		return null;

	}

	public User login(String login, String pass) throws Exception {
		connectToDB();

		String sqlSentence = "SELECT * FROM users WHERE user_login=? and user_pass=?";
		PreparedStatement ps = con.prepareStatement(sqlSentence);
		ps.setString(1, login);
		ps.setString(2, pass);
		ResultSet result = ps.executeQuery();

		if (!result.isBeforeFirst()) {
			result.close();
			ps.close();
			dissconnectFromDB();
			throw new ObjectNotExists("Incorect login data");

		} else {
			if (result.next()) {
				mainUserId = result.getInt(1);
			}
			result.close();
			ps.close();
			dissconnectFromDB();

		}

		// System.out.println(mainUserId);
		return null;

	}

	public User getLoggedIn() {
		return loggedIn;
	}

	public void logout() {

		loggedIn = null;
	}

	public Project returnProject(int id) {
		for (Project w : projects) {
			if (w.getId() == id) {
				return w;
			}
		}
		return null;
	}

	public ArrayList<Task> returnProjectTask(int id) {

		ArrayList<Task> t = new ArrayList();
		t = returnProject(id).getAllTasks();
		return t;

	}

	public int getProjectIdByTitle(String title) {
		int id = 0;
		for (Project w : projects) {
			if (title == w.getTitle()) {
				id = w.getId();
			}
		}

		return id;
	}
//	public Task getTaskName(int id) {
//		
//	}

	public ArrayList<Project> getAllUserProjects() throws ObjectNotExists {
		if (!loggedIn.getProjects().isEmpty()) {
			return loggedIn.getProjects();
		} else {
			return new ArrayList();
		}
	}

	public void addProjectMember(int projectId, int userId) {
		if (loggedIn != null) {
			Project current = getProjectById(projectId);
			User toAdd = getUserById(userId);
			if (current != null && toAdd != null) {
				current.addMember(toAdd);
				toAdd.addProject(current);
			}
		}
	}

	public void deleteProject(int id) {
		Project current = getProjectById(id);
		if (current != null) {
			projects.remove(current);
			for (User u : current.getMembers()) {
				u.getProjects().remove(current);
			}
		}
	}

	public Project getUserProjectById(int id) {
		if (loggedIn != null) {
			for (Project p : loggedIn.getProjects()) {
				if (p.getId() == id) {
					return p;
				}
			}
		}
		return null;
	}

	public Task addTaskToProject(int projectId, String title) {
		if (loggedIn != null && title.length() > 3) {
			Project my = getUserProjectById(projectId);
			Task newTask = new Task(title, my, loggedIn);
			my.addTask(newTask);
			return newTask;
		}

		return null;
	}

	public Task addTaskToTask(int taskId, String title) {
		if (loggedIn != null) {

			Task my = getUserTaskById(taskId);
			Task newTask = new Task(title, my.getProject(), loggedIn);
			my.addTask(newTask);
			return newTask;
		}

		return null;
	}

	public Task getUserTaskById(int id) {
		for (Project p : loggedIn.getProjects()) {
			ArrayList<Task> allTasks = p.getAllTasks();
			for (Task t : allTasks) {
				if (t.getId() == id) {
					return t;
				}
			}

		}
		return null;
	}

	public Task getUserTaskByTitle(String title) {
		for (Project p : loggedIn.getProjects()) {
			ArrayList<Task> allTasks = p.getAllTasks();
			for (Task t : allTasks) {
				if (t.getTitle().equals(title)) {
					return t;
				}
			}
		}
		return null;
	}

	public Project addProject(String title) throws ObjectNotExists {
		if (loggedIn != null && title.length() > 3) {
			Project newProject = new Project(title, loggedIn);
			projects.add(newProject);
			loggedIn.addProject(newProject);
			return newProject;
		}
		throw new ObjectNotExists("Incorect login data");
	}

	public void editPersonInfo(int id, String name, String surname) {
		User current = getUserById(id);
		if (current != null && current.getClass().equals(Person.class)) {
			Person p = (Person) current;
			p.setName(name);
			p.setSurname(surname);
		}
	}

	public Project getProjectById(int id) {
		return projects.get(0);// TODO
	}

//	public Project getProjectByTitle(String title) {
//		return projects.getTitle();
//	}

	public ArrayList<User> getAllUsers() {
		if (loggedIn != null && loggedIn.isActive()) {
			return users;
		}
		return new ArrayList();
	}

	public ArrayList<User> getAllActiveUsers() {
		if (loggedIn != null && loggedIn.isActive()) {
			ArrayList<User> filtered = new ArrayList();
			for (User u : users) {
				if (u.isActive()) {
					filtered.add(u);
				}
			}
			return filtered;
		}
		return new ArrayList();

	}

	public boolean disableUser(int id) {
		if (loggedIn != null && loggedIn.isActive()) {
			User forDeletion = getUserById(id);
			if (forDeletion != null && forDeletion.isActive()) {
				forDeletion.setActive(false);
				return true;
			}
		}
		return false;

	}

	public User getUserById(int id) {
		if (loggedIn != null && loggedIn.isActive()) {
			for (User u : users) {
				if (u.getId() == id) {
					return u;
				}
			}
		}
		return null;
	}

	public boolean isLoggedInCompany() throws SQLException {
		connectToDB();
		String sql = "SELECT user_type FROM users WHERE id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, mainUserId);
		ResultSet result = ps.executeQuery();

		result.next();
		type = result.getString(5);

		if (type.equals("Person")) {
			return false;
		} else {
			return true;
		}
	}

	public void deleteUser() throws SQLException {
		connectToDB();
		System.out.println(type);
		String deleteUser = "DELETE FROM users WHERE id=?";

		PreparedStatement ch = con.prepareStatement(deleteUser);
		ch.setInt(1, mainUserId);
		ch.executeUpdate();
		deleteCompany();

		deletePerson();

		ch.close();

		dissconnectFromDB();
	}

	public void deleteCompany() throws SQLException {
		connectToDB();
		String deleteCompany = "DELETE FROM persons WHERE user_id=?";
		PreparedStatement ch = con.prepareStatement(deleteCompany);
		ch.setInt(1, mainUserId);
		ch.executeUpdate();

		ch.close();
		dissconnectFromDB();

		// String deleteUser = "DELETE FROM users WHERE id=?";
		// String deletePerson = "DELETE FROM company WHERE user_id=?";
	}

	public void deletePerson() throws SQLException {
		connectToDB();
		String deletePerson = "DELETE FROM company WHERE id=?";
		PreparedStatement ch = con.prepareStatement(deletePerson);
		ch.setInt(1, mainUserId);
		ch.executeUpdate();

		ch.close();
		dissconnectFromDB();
	}

	// Update tablename set columnname = reiksme
	public void changeSurOrTitle(String textas) throws SQLException {
		connectToDB();
		String changeTitle = "UPDATE company SET title=? WHERE id=?";
		String changeSur = "UPDATE persons SET surname=? WHERE user_id=?";

		PreparedStatement ch = con.prepareStatement(changeSur);
		ch.setString(1, textas);
		ch.setInt(2, mainUserId);
		ch.executeUpdate();

		PreparedStatement ps1 = con.prepareStatement(changeTitle);
		ps1.setString(1, textas);
		ps1.setInt(2, mainUserId);
		ps1.executeUpdate();
		ps1.close();

		dissconnectFromDB();

	}

	public int[] getUserCount() {
		int[] num = new int[2];
		for (User u : users) {
			if (u.getClass().equals(Person.class)) {
				num[0] = num[0] + 1;

			} else {
				num[1] = num[1] + 1;
			}
		}
		return num;
	}

	public User getUserByLogin(String login) {
		if (loggedIn != null && loggedIn.isActive()) {
			for (User u : users) {
				if (u.getLogin().equals(login)) {
					return u;
				}
			}
		}
		return null;
	}

	public boolean isExists(String login) throws SQLException {
		connectToDB();

		String sqlSentence = "SELECT user_login FROM users WHERE user_login=?";
		PreparedStatement ps = con.prepareStatement(sqlSentence);
		ps.setString(1, login);
		ResultSet result = ps.executeQuery();

		if (!result.isBeforeFirst()) {

			result.close();
			ps.close();
			dissconnectFromDB();

			return false;
		}

		else {

			result.close();
			ps.close();
			dissconnectFromDB();

			return true;
		}
	}
}