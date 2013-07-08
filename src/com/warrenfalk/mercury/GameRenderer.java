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

import android.graphics.PointF;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameRenderer implements GLSurfaceView.Renderer {
	
	float width = 1920f;
	float height = 1080f;

    public GameRenderer() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        // orthographic
        float ratio = width / height;
        PointF upperLeft = new PointF(0f, 0f);
        PointF lowerRight = new PointF(width, height);
        gl.glOrthof(upperLeft.x, lowerRight.x, lowerRight.y, upperLeft.y, -10.0f, 10.0f);
        //gl.glFrustumf(-10.0f, 10.0f, 10.0f, -10.0f, 0.1f, 100.0f);
        gl.glViewport(0, 0, (int) width, (int) height);
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glClearColor(0f, 0f, 0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        width = w;
        height = h;
        gl.glViewport(0, 0, w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
    }
}
