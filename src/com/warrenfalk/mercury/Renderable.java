package com.warrenfalk.mercury;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public interface Renderable {
	void surface(GL10 gl, Context context);
	void prerender(GL10 gl);
	void render(GL10 gl);
	void postrender(GL10 gl);
	void destroy();
	boolean remove();
}
