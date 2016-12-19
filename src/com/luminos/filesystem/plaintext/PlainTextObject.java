package com.luminos.filesystem.plaintext;

/**
 * Creates plain text object which
 * wraps the type, value, and name
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */
public class PlainTextObject {
	
	/**
	 * Entry variables
	 */
	public DataStruct type;
	public Object value;
	public String name;
	
	/**
	 * Creates new plain text object
	 * @param type		Type of variable
	 * @param name		Name of variable
	 * @param value		Value of variable
	 */
	public PlainTextObject(DataStruct type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

}
