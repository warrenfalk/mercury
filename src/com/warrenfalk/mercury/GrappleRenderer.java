package com.warrenfalk.mercury;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.warrenfalk.opengl.Utils;
import com.warrenfalk.opengl.Vec2;

import android.content.Context;

public class GrappleRenderer implements ObjectRenderer {
	
	Grapple grapple;
	
	static final ShortBuffer indexBuffer = Utils.createBuffer(new short[] {0, 1, 2, 1, 3, 2});
	static final FloatBuffer vertexBuffer = Utils.createBuffer(new float[] {
	        0.5f, 0.5f, 0f,
	        -0.5f, 0.5f, 0f,
	        0.5f, -0.5f, 0f,
	        -0.5f, -0.5f, 0f,
	});

	@Override
	public void setup(GL10 gl, Context context) {
	}

	@Override
	public void render(GL10 gl) {
		for (Vec2 point : grapple.points) {
			gl.glPushMatrix();
			gl.glTranslatef(point.x, point.y, 5f);
			gl.glScalef(0.2f, 0.2f, 1f);
			gl.glColor4f(0f, 0.2f, 0.0f, 1);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, indexBuffer);
			gl.glPopMatrix();
		}
		
	}

	@Override
	public void destroy() {
	}

}
