package tk.luminos.luminoscore.graphics.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import tk.luminos.luminoscore.graphics.gameobjects.Entity;
import tk.luminos.luminoscore.graphics.models.RawModel;
import tk.luminos.luminoscore.graphics.models.TexturedModel;
import tk.luminos.luminoscore.graphics.shaders.EntityShader;
import tk.luminos.luminoscore.graphics.textures.ModelTexture;
import tk.luminos.luminoscore.tools.Maths;

/**
 * 
 * Allows for rendering of entities
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */

public class EntityRenderer {

	private EntityShader shader;
	private float gradient = 5.0f;
	private float density = 0.0035f;
	
	/**
	 * Constructor of EntityRenderer
	 * 
	 * @param shader			{@link EntityShader} that is used for rendering entities
	 * @param projectionMatrix	Projection Matrix that is used to draw the screen
	 */
	public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**
	 * @param entities	Defines the map of entities to render
	 * 
	 * Renders entities to screen
	 */
	public void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	/**
	 * Gets the gradient of the fog
	 * 
	 * @return	gradient of the fog
	 */
	public float getGradient() {
		return gradient;
	}

	/**
	 * Sets the gradient of the fog
	 * 
	 * @param gradient		fog gradient value
	 */
	public void setGradient(float gradient) {
		this.gradient = gradient;
	}

	/**
	 * Gets the density of the fog
	 * 
	 * @return	density of the fog
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * Sets the density of the fog
	 * 
	 * @param density	fog density value
	 */
	public void setDensity(float density) {
		this.density = density;
	}
	
	/**
	 * Cleans up shader program
	 */
	public void cleanUp() {
		shader.cleanUp();
	}
	
//***********************************Private Methods*********************************//	

	/**
	 * @param model		Defines textured model to be prepared
	 * 
	 * Prepares a textured model for rendering as entity
	 */
	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if(texture.hasTransparency()){
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.usesFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		shader.loadDensity(density);
		shader.loadGradient(gradient);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}

	/**
	 * Unbinds the prepared textured model
	 */
	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	/**
	 * @param entity	Entity to be rendered
	 * 
	 * Prepares instance of entity for rendering
	 */
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix((Vector3f) entity.getPosition(),
				entity.getRotation(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffset(0, 0);
	}

}