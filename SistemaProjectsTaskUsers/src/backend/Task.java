package backend;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Task implements Serializable {
	private int id;
	private static int idCounter = 1;
	private String title;
	private Date createdOn, completedOn;
	private User createdBy, complytedBy;
	private boolean done = false;
	private ArrayList<Task> subTasks = new ArrayList();
	private Project project;

	public Task(String title, Project p, User createdBy) {
		id = idCounter++;
		this.title = title;
		this.createdBy = createdBy;
		// createdOn = new Date(id);
		project = p;

	}

	public ArrayList<Task> getAllTasks() {
		ArrayList<Task> all = new ArrayList();
		for (Task t : subTasks) {
			all.addAll(t.getAllTasks());
		}
		return all;
	}

	public void addTask(Task t) {
		subTasks.add(t);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getCompletedOn() {
		return completedOn;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getComplytedBy() {
		return complytedBy;
	}

	public boolean isDone() {
		return done;
	}

	public ArrayList<Task> getSubTask() {
		return subTasks;
	}

	public Project getProject() {
		return project;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + "]";
	}



}
