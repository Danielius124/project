package gui;

import backend.ToDoList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewFxMain extends Application {
	public static void main(String[] args) throws Exception {
//		todo.registerPerson("admin", "admin123", "admin", "admin");
//		todo.registerPerson("admdfgin", "admin123", "admin", "admin");
//		todo.registerPerson("admasdfgin", "admin123", "admin", "admin");
//		todo.registerCompany("dsfgdfg", "asdegadgf", "dfgadfgasdg");
//		todo.login("admin", "admin123");
//		todo.addProject("Jonas");
//		todo.addProject("petrasr");
//		todo.addProject("Joadfgadfgas");
//		todo.addProject("Jonadfgadfgas");
//		todo.addProject("Jonadfgdafgas");
//		todo.addTaskToProject(1, "paruosti nd");
//		todo.addTaskToProject(1, "paruosdfgti nd");
//		todo.addTaskToProject(1, "paruosadfgti nd");
//		todo.addTaskToProject(1, "paruodsti nd");
//		todo.addTaskToProject(1, "paruoesti nd");
//		todo.gettAllUsersForFileReading();
	launch(args);
	}

	final static ToDoList todo = new ToDoList();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader load = new FXMLLoader(getClass().getResource("Pagrindas.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Laboratorinis nr 3");
		stage.show();

	}
}
