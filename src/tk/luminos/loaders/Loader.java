package tk.luminos.loaders;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import tk.luminos.graphics.models.ModelData;
import tk.luminos.graphics.models.RawModel;

/**
 * 
 * Class that loads objects to the graphics card
 * 
 * @author Nick Clark
 * @version 1.1
 *
 */

public class Loader {
	
	protected static List<Integer> vaos = new ArrayList<Integer>();
	protected static List<Integer> vbos = new ArrayList<Integer>();
	protected static List<Integer> textures = new ArrayList<Integer>();
	
	private ModelLoader modelLoader = new ModelLoader();
	private ImageLoader imageLoader = new ImageLoader();
	
	private static Loader instance;
	
	public static Loader create() {
		if (instance != null) {
			System.err.println("ERROR: MINOR - Loader already created.");
			return instance;
		}
		return (instance = new Loader());
	}
	
	public static Loader getInstance() {
		if (instance == null)
			throw new NullPointerException("Loader is not initialized!");
		return instance;
	}
	
	private Loader() {
		
	}

	/**
	 * Loads an array of positions to the graphics card
	 * 
	 * @param positions		Positions to be loaded
	 * @param dimensions	Number of dimensions the positions take
	 * @return				RawModel describing the positions loaded
	 */
	public RawModel loadToVAO(float[] positions, int dimensions) {
		return modelLoader.loadToVAO(positions, dimensions);
	}
	
	public RawModel loadToVAO(float[] positions, int[] indices) {
		return modelLoader.loadToVAO(positions, indices);
	}
	
	/**
	 * Loads an array of positions and texture coordinates to the graphics card
	 * 
	 * @param positions		Positions to be loaded
	 * @param textureCoords	Texture coordinates of the positions
	 * @return				RawModel describing the positions and texture coordinates loaded
	 */
	public int loadToVAO(float[] positions, float[] textureCoords) {
		return modelLoader.loadToVAO(positions, textureCoords);
	}
	
	/**
	 * Loads an array of positions, texture coordinates, and indices to the graphics card
	 * 
	 * @param positions		Positions to be loaded		
	 * @param textureCoords	Texture coordinates of the positions
	 * @param indices		Indices describing the order of the positions
	 * @return				RawModel describing the positions and texture coordinates loaded
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
		return modelLoader.loadToVAO(positions, textureCoords, indices);
	}
	
	/**
	 * Loads an array of positions, texture coordinates, normal coordinates, and inidices to the graphics card
	 * 
	 * @param positions		Positions to be loaded
	 * @param textureCoords	Texture coordinates of the positions
	 * @param normals		Normal coordinates of the positions
	 * @param indices		Indices describing the order of the positions
	 * @return				RawModel describing the positions, texture coordinates, and normal coordinates loaded
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		return modelLoader.loadToVAO(positions, textureCoords, normals, indices);
	}
	
	/**
	 * Loads an array of positions, texture coordinates, normal coordinates, and vertex count to the graphics card
	 * 
	 * @param positions		Positions to be loaded
	 * @param textureCoords	Texture coordinates of the positions
	 * @param normals		Normal coordinates of the positions
	 * @param vertexCount	Integer describing the number of vertices
	 * @return				RawModel describing the positions, texture coordinates, and normal coordinates loaded
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int vertexCount) {
		return modelLoader.loadToVAO(positions, textureCoords, normals, vertexCount);
	}
	
	/**
	 * Loads an array of positions, texture coordinates, normal coordinates, and vertex count to the graphics card
	 * 
	 * @param positions		Positions to be loaded
	 * @param textureCoords	Texture coordinates of the positions
	 * @param normals		Normal coordinates of the positions
	 * @param tangentsArray	Tangential data
	 * @param indicesArray	Index data
	 * @return				RawModel describing the positions, texture coordinates, and normal coordinates loaded
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, float[] tangentsArray, int[] indicesArray) {
		return modelLoader.loadToVAO(positions, textureCoords, normals, tangentsArray, indicesArray);
	}
	
	public RawModel loadToVAO(ModelData data) {
		return loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
	}
	
	/**
	 * Loads array of texture files to a cube map
	 * 
	 * @param textureFiles	Array of strings describing the location of texture files
	 * @return				Integer describing the cube map's index on the GPU
	 * @throws Exception	Exception for if file isn't found or cannot be handled
	 */
	public int loadCubeMap(String[] textureFiles) throws Exception {
		return imageLoader.loadCubeMap(textureFiles);
	}
	
	/**
	 * Loads texture file to a cube map
	 * 
	 * @param fileName		String describing the location of the texture file
	 * @return				Integer describing the texture's index on the GPU
	 * @throws Exception	Exception for if file isn't found or cannot be handled
	 */
	public int loadTexture(String fileName) throws Exception {
		return imageLoader.loadTexture(fileName);
	}
	
	/**
	 * Loads buffered image to the graphics card
	 * 
	 * @param bImage	BufferedImage containing the texture data
	 * @return			Integer describing the texture's index on the GPU
	 */
	public int loadTexture(BufferedImage bImage) {
		return imageLoader.loadTexture(bImage);
	}
	
	/**
	 * Removes all VAOs, VBOs, and Textures from the VRAM
	 */
	public void dispose() {
		for (Integer vao : vaos) GL30.glDeleteVertexArrays(vao);
		for (Integer vbo : vbos) GL15.glDeleteBuffers(vbo);
		for (Integer texture : textures) GL11.glDeleteTextures(texture);
		instance = null;
	}

}
