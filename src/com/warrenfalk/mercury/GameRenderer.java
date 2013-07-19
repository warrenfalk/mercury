/*
 * Copyright (C) 2012 OUYA, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.warrenfalk.mercury;

import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.warrenfalk.opengl.Vec2;

import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer {

	LinkedList<Renderable> renderables = new LinkedList<Renderable>();
	LinkedList<Renderable> toAdd = new LinkedList<Renderable>();

	float width, height;

	public GameRenderer() {
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		width = w;
		height = h;
		gl.glViewport(0, 0, w, h);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		// orthographic
		float ratio = width / height;
		Vec2 upperLeft = new Vec2(0f, 0f);
		Vec2 lowerRight = new Vec2(width, height);
		gl.glOrthof(upperLeft.x, lowerRight.x, lowerRight.y, upperLeft.y, -10.0f, 10.0f);
		// gl.glFrustumf(-10.0f, 10.0f, 10.0f, -10.0f, 0.1f, 100.0f);
		gl.glViewport(0, 0, (int) width, (int) height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glClearColor(0f, 0f, 0f, 1.0f);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();

		LinkedList<Renderable> add = null;
		if (toAdd != null) {
			synchronized (renderables) {
				add = toAdd;
				toAdd = null;
			}
		}
		if (add != null)
			for (Renderable renderable : add)
				renderables.push(renderable);
		for (Renderable renderable : renderables) {
			renderable.prerender(gl);
			renderable.render(gl);
			renderable.postrender(gl);
		}
	}
}
