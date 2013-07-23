package com.warrenfalk.mercury;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public interface ObjectRenderer {
	void setup(GL10 gl, Context context);
	void render(GL10 gl);
	void destroy();
}
