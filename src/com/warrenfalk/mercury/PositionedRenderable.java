package com.warrenfalk.mercury;

import javax.microedition.khronos.opengles.GL10;

public abstract class PositionedRenderable extends RenderableBase {

	@Override
	public void prerender(GL10 gl) {
		super.prerender(gl);
		translate(gl);
		scale(gl);
		rotate(gl);
	}
	
	abstract void translate(GL10 gl);
	
	abstract void scale(GL10 gl);
	
	abstract void rotate(GL10 gl);

}
