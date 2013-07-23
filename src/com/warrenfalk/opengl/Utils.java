package com.warrenfalk.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Utils {

	public static ShortBuffer createBuffer(short[] shorts) {
        ByteBuffer bb = ByteBuffer.allocateDirect(shorts.length * 2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer sb = bb.asShortBuffer();
        sb.put(shorts);
        sb.position(0);
        return sb;
    }

	public static FloatBuffer createBuffer(float[] floats) {
        ByteBuffer bb = ByteBuffer.allocateDirect(floats.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(floats);
        fb.position(0);
        return fb;
	}
	
	public static int loadGLTextureFromResource(int resourceID, Context context, GL10 gl) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, opts);
		int textureId = loadGLTextureFromBitmap(bitmap, gl);
		bitmap.recycle();
		return textureId;
	}
	
	public static int loadGLTextureFromBitmap(Bitmap bitmap, GL10 gl) {

		// Generate one texture pointer
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);

		// bind this texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);

		// Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

		// Use the Android GLUtils to specify a two-dimensional texture image
		// from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		return textureIds[0];
	}

}
