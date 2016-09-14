package tk.luminos.graphics.opengl.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import tk.luminos.graphics.opengl.gameobjects.Entity;
import tk.luminos.graphics.opengl.models.RawModel;
import tk.luminos.graphics.opengl.models.TexturedModel;
import tk.luminos.graphics.opengl.shaders.NormalMapShader;
import tk.luminos.graphics.opengl.textures.ModelTexture;
import tk.luminos.maths.matrix.Matrix4f;
import tk.luminos.tools.Maths;

/**
 * 
 * Allows for rendering of normal mapped entities
 * 
 * @author Nick Clark
 * @version 1.0
 *
 */

public class NormalMapRenderer {

	private NormalMapShader shader;
	 
	/**
	 * Constructor
	 * 
	 * @param nms					Shader to use
	 * @param projectionMatrix		Projection matrix to use
	 */
    public NormalMapRenderer(NormalMapShader nms, Matrix4f projectionMatrix) {
        this.shader = nms;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }
 
    /**
     * Renders entities to current Frame Buffer
     * 
     * @param entities		Map of entities to render
     */
    public void render(Map<TexturedModel, List<Entity>> entities) {
        shader.start();
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
        shader.stop();
    }
     
    /**
     * Cleans up the shader
     */
    public void cleanUp(){
        shader.cleanUp();
    }
 
    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        ModelTexture texture = model.getTexture();
        shader.loadNumberOfRows(texture.getNumberOfRows());
        if (texture.hasTransparency()) {
            MasterRenderer.disableCulling();
        }
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getNormal());
    }
 
    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    }
 
    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadOffset(0, 0);
    }
	
}