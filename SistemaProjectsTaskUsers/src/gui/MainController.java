package gui;

import java.io.IOException;
import java.sql.SQLException;

import backend.ObjectNotExists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	Stage stage;

	@FXML
	private MenuBar meniu;
	@FXML
	private ListView<String> listView;
	@FXML
	private Button openButton;
	@FXML
	private Button loadProjectsButton;
	@FXML
	private Button loadTasksButton;
	@FXML
	private ListView<String> taskList;
	@FXML
	private Button createTaskButton;
	@FXML
	private TextField addTask;
	@FXML
	private PieChart userS;
	@FXML
	private Button change;
	@FXML
	private TextField changeName;
	

	public void close() {
		System.exit(0);
	}
	
	public void changeName() throws SQLException {
		String task = changeName.getText();
		NewFxMain.todo.changeSurOrTitle(task);
	}

	public void showUserStat() {
			int[] count = NewFxMain.todo.getUserCount();
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
					new PieChart.Data("Persons - " + count[0], count[0]), new PieChart.Data("Company - " + count[1], count[1]));
			userS.setTitle("User statistics");
			userS.setData(pieChartData);
	}

	public void signOut() throws IOException {

		NewFxMain.todo.logout();
		FXMLLoader load = new FXMLLoader(getClass().getResource("Pagrindas.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage = (Stage) meniu.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("3-asis laboratorinis darbas");
		stage.show();

	}

	public void createNewProject() throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("CreateNewProject.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);
		stage = (Stage) meniu.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("3-asis laboratorinis darbas");
		stage.show();
	}

	public void getProjectList() throws IOException, ObjectNotExists {
		listView.getItems().clear();
		int numberOfProjects = NewFxMain.todo.getAllUserProjects().size();
		for (int i = 0; i < numberOfProjects; i++) {
			String pavadinimas = NewFxMain.todo.getAllUserProjects().get(i).getTitle();
			listView.getItems().addAll(pavadinimas);
		}
	}

	int projectid;

	public void getProjectTaskList() {
		taskList.getItems().clear();
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		String title = listView.getSelectionModel().getSelectedItem();
		projectid = NewFxMain.todo.getProjectIdByTitle(title);
		int numberOfTasks = NewFxMain.todo.returnProjectTask(projectid).size();
		for (int i = 0; i < numberOfTasks; i++) {
			String pavadinimas = NewFxMain.todo.returnProjectTask(projectid).get(i).getTitle();
			taskList.getItems().addAll(pavadinimas);
		}

	}

	public void addNewTask(ActionEvent e) throws IOException {
		String task = addTask.getText();
		NewFxMain.todo.addTaskToProject(projectid, task);
	}
	
	public void delete() throws SQLException, IOException {
		NewFxMain.todo.deleteUser();
	}

}
//listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
// FXMLLoader load = new
// FXMLLoader(getClass().getResource("ProjectOptions.fxml"));
// ProjectOptionsController p = load.getController();
// System.out.println(selectedItem);
// p.setLabel(listView.getSelectionModel().getSelectedItem());