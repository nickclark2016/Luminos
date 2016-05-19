package tk.luminos.luminoscore.graphics.shaders;

import org.lwjgl.util.vector.Matrix4f;

import tk.luminos.luminoscore.GlobalLock;
import tk.luminos.luminoscore.graphics.gameobjects.Camera;
import tk.luminos.luminoscore.graphics.gameobjects.Light;
import tk.luminos.luminoscore.tools.Maths;

/**
 * 
 * Water Shader for Water Renderer
 * 
 * @author Nick Clark
 * @version 1.1
 *
 */
public class WaterShader extends ShaderProgram {

	public static String VERT = "water.vert";
	public static String FRAG = "water.frag";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_normalMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_depthMap;

	/**
	 * Constructor
	 */
	public WaterShader() {
		super(VERT, FRAG);
	}

	/*
	 * (non-Javadoc)
	 * @see luminoscore.graphics.shaders.ShaderProgram#bindAttributes()
	 */
	protected void bindAttributes() {
		bindAttribute(GlobalLock.POSITION, "position");
	}

	/*
	 * (non-Javadoc)
	 * @see luminoscore.graphics.shaders.ShaderProgram#getAllUniformLocations()
	 */
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_dudvMap = getUniformLocation("dudvMap");
		location_normalMap = getUniformLocation("normalMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_cameraPosition = getUniformLocation("cameraPosition");
		location_lightPosition = getUniformLocation("lightPosition");
		location_lightColor = getUniformLocation("lightColor");
		location_depthMap = getUniformLocation("depthMap");
	}

	/**
	 * Connect texture units
	 */
	public void connectTextureUnits() {
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
		super.loadInt(location_dudvMap, 2);
		super.loadInt(location_normalMap, 3);
		super.loadInt(location_depthMap, 4);
	}
	
	/**
	 * Loads {@link Light} to shader
	 * 
	 * @param sun	Focal light of scene 
	 */
	public void loadLight(Light sun) {
		super.loadVector(location_lightColor, sun.getColor());
		super.loadVector(location_lightPosition, sun.getPosition());
	}

	/**
	 * Loads projection matrix to shader
	 * 
	 * @param projection	Projection matrix
	 */
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	/**
	 * Loads movement factor of water
	 * 
	 * @param factor	Movement factor of water
	 */
	public void loadMoveFactor(float factor) {
		super.loadFloat(location_moveFactor, factor);
	}

	/**
	 * Load view matrix to shader
	 * 
	 * @param camera	Camera to calculate view matrix of
	 */
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}

	/**
	 * Loads model matrix to shader
	 * 
	 * @param modelMatrix	Model matrix
	 */
	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}