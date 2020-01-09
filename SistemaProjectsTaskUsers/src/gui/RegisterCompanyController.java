package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterCompanyController {
	Stage stage;

	@FXML
	private Button backButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField registerLogin;
	@FXML
	private TextField registerPassword;
	@FXML
	private TextField registerTitle;

	

	public void back(ActionEvent ae) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("Pagrindas.fxml"));
        Parent root = load.load();
        Scene scene = new Scene(root);
        stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("3-asis laboratorinis darbas");
        stage.show();
	}
	
	public void registerCompany(ActionEvent ae) throws  IOException {
		String login = registerLogin.getText();
		String password = registerPassword.getText();
		String title = registerTitle.getText();
		try {
			NewFxMain.todo.registerCompany(login, password, title);
			Scene scena = registerPassword.getScene();
			FXMLLoader load = new FXMLLoader(getClass().getResource("Pagrindas.fxml"));
			Parent root = load.load();
			Scene scene = new Scene(root);
			stage = (Stage) registerPassword.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("3-asis laboratorinis darbas");
			stage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Register");
			alert.setHeaderText("Something went wrong!!!!!");
			// alert.setContentText("Llease try again");
			alert.showAndWait();
		
		}
	}
}
