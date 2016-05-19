package tk.luminos.luminoscore.graphics.models;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * Contains the mesh data of the model
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */

public class Mesh {
	
	private List<Vector3f> vertices;
	private List<Vector3f> normals;

	/**
	 * Constructor
	 * 
	 * @param vertices	Vertex positions
	 * @param normals	Normal vectors
	 */
	public Mesh(List<Vector3f> vertices, List<Vector3f> normals) {
		this.vertices = vertices;
		this.normals = normals;
	}

	/**
	 * Gets the vertices of the model
	 * 
	 * @return Positions of vertices of model
	 */
	public List<Vector3f> getVertices() {
		return vertices;
	}
	
	/**
	 * Gets the normals of the model\
	 * 
	 * @return Normals of the vertices of model
	 */
	public List<Vector3f> getNormals() {
		return normals;
	}

}