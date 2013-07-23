package com.warrenfalk.mercury;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.warrenfalk.opengl.Utils;

import android.content.Context;

public class TestObjectRenderer implements ObjectRenderer {

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
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void render(GL10 gl) {
		gl.glColor4f(0, 0, 0, 1f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, indexBuffer);
	}


}
