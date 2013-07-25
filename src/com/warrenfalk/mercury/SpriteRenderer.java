package com.warrenfalk.mercury;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.warrenfalk.opengl.Utils;

public class SpriteRenderer implements ObjectRenderer {
	
	private GL10 gl;
	private int textureId;
	private final int resourceId;
	
	public SpriteRenderer(int imgResourceId) {
		this.resourceId = imgResourceId;
	}
	
	static final ShortBuffer indexBuffer = Utils.createBuffer(new short[] {0, 1, 2, 1, 3, 2});
	static final FloatBuffer vertexBuffer = Utils.createBuffer(new float[] {
	        1f, 1f, 0f,
	        0f, 1f, 0f,
	        1f, 0f, 0f,
	        0f, 0f, 0f,
	});
	static final FloatBuffer textureBuffer = Utils.createBuffer(new float[] {
            1.0f, 1.0f,
            0.0f, 1.0f,	
    		1.0f, 0.0f,
            0.0f, 0.0f,
	});

	@Override
	public void setup(GL10 gl, Context context) {
		if (this.gl != gl) {
			this.gl = gl;
			this.textureId = Utils.loadGLTextureFromResource(resourceId, context, gl);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void render(GL10 gl) {
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glColor4f(1, 1, 1, 1);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glEnable(GL10.GL_ALPHA_BITS);
		gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glDisable(GL10.GL_ALPHA_BITS);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}

}
