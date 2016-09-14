package tk.luminos.graphics.opengl.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import tk.luminos.graphics.opengl.gameobjects.Entity;
import tk.luminos.graphics.opengl.models.RawModel;
import tk.luminos.graphics.opengl.models.TexturedModel;
import tk.luminos.graphics.opengl.shaders.ShadowShader;
import tk.luminos.maths.matrix.Matrix4f;
import tk.luminos.maths.vector.Vector3f;
import tk.luminos.tools.Maths;

/**
 * 
 * Renders shadow maps for entities
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */

public class ShadowMapEntityRenderer {

	private Matrix4f projectionViewMatrix;
	private ShadowShader shader;

	/**
	 * Constructor
	 * 
	 * @param shader				Defines shader to use
	 * @param projectionViewMatrix	Defines projectionView matrix for rendering
	 */
	protected ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}

	/**
	 * Renders shadow map to buffer
	 * 
	 * @param entities	Defines entities to render to shadowo map
	 */
	protected void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			RawModel rawModel = model.getRawModel();
			bindModel(rawModel);
			for (Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

//*************************************Private Methods********************************//
	
	/**
	 * Binds raw model to GPU
	 * 
	 * @param rawModel		RawModel to be bound
	 */
	private void bindModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}

	/**
	 * Prepares entity to be rendered
	 * 
	 * @param entity		Entity to be prepared
	 */
	private void prepareInstance(Entity entity) {
		Matrix4f modelMatrix = Maths.createTransformationMatrix((Vector3f) entity.getPosition(),
				entity.getRotation(), entity.getScale());
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix, null);
		shader.loadMvpMatrix(mvpMatrix);
	}

}