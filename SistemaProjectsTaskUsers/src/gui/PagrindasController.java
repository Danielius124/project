package gui;

import java.io.IOException;

import com.sun.glass.ui.MenuBar;

import backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PagrindasController {
	Stage stage;

	@FXML
	private TextField loginName;
	@FXML
	private TextField loginPassword;
	@FXML
	private MenuBar menuBar;
	@FXML
	private ImageView imageviev;
	@FXML
	private Button personButton;
	@FXML
	private Button companyButton;


	public void login(ActionEvent ae) throws Exception {
		String login = loginName.getText();
		String password = loginPassword.getText();
		try {
			User u = NewFxMain.todo.login(login, password);
			FXMLLoader load = new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root = load.load();		
			Scene scene = new Scene(root);
			MainController t = load.getController();
			t.showUserStat();
			stage = (Stage) loginName.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("3-asis laboratorinis darbas");
			stage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Login");
			alert.setHeaderText("Incorrect username/password");
			// alert.setContentText("Llease try again");

			alert.showAndWait();
		}
	}

	public void registerPerson() throws IOException {
		Scene scena = personButton.getScene();
		FXMLLoader load = new FXMLLoader(getClass().getResource("RegisterPerson.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage = (Stage) personButton.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("3-asis laboratorinis darbas");
		stage.show();
	}

	public void registerCompany() throws IOException {
		Scene scena = companyButton.getScene();
		FXMLLoader load = new FXMLLoader(getClass().getResource("RegisterCompany.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage = (Stage) companyButton.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("3-asis laboratorinis darbas");
		stage.show();
	}

}
