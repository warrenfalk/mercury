package com.warrenfalk.mercury;

import javax.microedition.khronos.opengles.GL10;

public abstract class RenderableBase implements Renderable {
	@Override
	public void prerender(GL10 gl) {
        gl.glPushMatrix();
	}
	
	@Override
	public void render(GL10 gl) {
        //gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        //gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, indexBuffer);
	}
	
	@Override
	public void postrender(GL10 gl) {
        gl.glPopMatrix();
	}
}
