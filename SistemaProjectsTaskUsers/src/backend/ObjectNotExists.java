package backend;

import java.io.Serializable;

public class ObjectNotExists extends Exception implements Serializable {
	public ObjectNotExists(String s) {
		super(s);
	}
}
