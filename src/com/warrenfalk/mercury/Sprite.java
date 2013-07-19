package com.warrenfalk.mercury;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.warrenfalk.opengl.Utils;
import com.warrenfalk.opengl.Vec2;
import com.warrenfalk.opengl.Vec3;

import android.content.Context;

public class Sprite extends PositionedRenderable {
	
	private GL10 gl;
	private int textureId;
	private final int resourceId;
	private Vec2 size = new Vec2();
	private Vec3 position = new Vec3();
	private Vec2 center = new Vec2(0.5f, 0.5f);
	private float rotation;
	
	public Sprite(int imgResourceId) {
		this.resourceId = imgResourceId;
	}
	
	static final ShortBuffer vertexBuffer = Utils.createBuffer(new short[] {0, 1, 2, 1, 3, 2});
	static final FloatBuffer indexBuffer = Utils.createBuffer(new float[] {
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
	public void surface(GL10 gl, Context context) {
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
	public boolean remove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	void translate(GL10 gl) {
		gl.glTranslatef(position.x - center.x, position.y - center.y, 5.0f);
	}

	@Override
	void scale(GL10 gl) {
		gl.glScalef(size.x, size.y, 1f);
	}

	@Override
	void rotate(GL10 gl) {
		gl.glRotatef(rotation, 0.0f, 0.0f, 1.0f);
	}
	
	@Override
	public void render(GL10 gl) {
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}

}
