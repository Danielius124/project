package gui;

import java.io.IOException;

import backend.ToDoList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateNewProjectController {

	Stage stage;
	ToDoList todo = new ToDoList();
	@FXML
	private Button backButton;
	@FXML
	private Button confirmButton;
	@FXML
	private TextField projectTitle;
	
	public void goBack() throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage = (Stage) backButton.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("3-asis laboratorinis darbas");
		stage.show();
	}
	
	public void createProject() throws IOException {
		
		String newProject = projectTitle.getText();
		try {
		NewFxMain.todo.addProject(newProject);
		FXMLLoader load = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage = (Stage) confirmButton.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("3-asis laboratorinis darbas");
		stage.show();
		}
		catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Create project!");
			alert.setHeaderText("Wrong project name!");
			alert.showAndWait();
		}
	}
	
}
