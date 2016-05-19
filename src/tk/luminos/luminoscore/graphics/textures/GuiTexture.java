package tk.luminos.luminoscore.graphics.textures;

import org.lwjgl.util.vector.Vector2f;

/**
 * 
 * GUI Texture
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */

public class GuiTexture {
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	
	/**
	 * Constructor
	 * 
	 * @param texture	GPU texture ID
	 * @param position	Position of GUI
	 * @param scale		Scale of texture
	 */
	public GuiTexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	/**
	 * Gets texture ID of GUI Texture
	 * @return  GPU Texture ID
	 */
	public int getTexture() {
		return texture;
	}

	/**
	 * Gets position of GUI Texture
	 * 
	 * @return Position of GUI Texture 
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * Gets scale of GUI Texture
	 * 
	 * @return Vector2f	Scale of GUI Texture 
	 */
	public Vector2f getScale() {
		return scale;
	}

}