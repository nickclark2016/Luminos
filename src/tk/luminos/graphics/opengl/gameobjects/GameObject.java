package tk.luminos.graphics.opengl.gameobjects;

import tk.luminos.filesystem.serialization.LDatabase;
import tk.luminos.filesystem.serialization.LObject;
import tk.luminos.graphics.opengl.models.RawModel;
import tk.luminos.maths.vector.Vector;
import tk.luminos.maths.vector.Vector3f;

/**
 * 
 * Interface for game objects
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */

public interface GameObject {
	
	/**
	 * Gets the position of the GameObject
	 * 
	 * @return	position of the object
	 */
	public Vector3f getPosition();
	
	/**
	 * Increases the position of the GameObject by delta
	 * 
	 * @param delta		amount to increase
	 */
	public void increasePosition(Vector delta);
	
	/**
	 * Gets the rotation of the GameObject
	 * 
	 * @return 	rotation of the object
	 */
	public Vector3f getRotation();
	
	//Serialization Interface
	/**
	 * Gets the bytes of the GameObject
	 * 
	 * @return	bytes of the object
	 */
	public byte[] getBytes();
	
	/**
	 * Gets the Luminos Object of the object
	 * 
	 * @return	Luminos Object representing the object
	 */
	public LObject getLuminosObject();
	
	/**
	 * Attaches the GameObject to a Luminos Database
	 * 
	 * @param database		Database to attach to
	 */
	public void attachToLuminosDatabase(LDatabase database);
	
	/**
	 * Gets Game Object's Raw Model
	 * 
	 * @return		Raw Model of Game Object
	 */
	public RawModel[] getRawModels();
	
}