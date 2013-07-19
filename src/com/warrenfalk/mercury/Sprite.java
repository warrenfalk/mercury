package com.warrenfalk.mercury;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Sprite extends PositionedRenderable {
	
	private GL10 gl;
	private int textureId;
	private final int resourceId;
	
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
		// TODO Auto-generated method stub

	}

	@Override
	void scale(GL10 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	void rotate(GL10 gl) {
		// TODO Auto-generated method stub

	}

}
