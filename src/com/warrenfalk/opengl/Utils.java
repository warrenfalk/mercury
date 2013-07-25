package com.warrenfalk.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Utils {
	
	static final ShortBuffer rectIndexBuffer = createBuffer(new short[] {0, 1, 2, 3});
	static final FloatBuffer rectVertexBuffer = createBuffer(new float[] {
	        1f, 1f, 0f,
	        0f, 1f, 0f,
	        0f, 0f, 0f,
	        1f, 0f, 0f,
	});

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
	
	public static void rect(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectVertexBuffer);
		gl.glDrawElements(GL10.GL_LINE_LOOP, rectIndexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, rectIndexBuffer);
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
		
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE); 
		//GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		myTexImage2D(gl, bitmap);
		
		((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, new int[] { 0, bitmap.getHeight(), bitmap.getWidth(), - bitmap.getHeight() }, 0); 
		return textureIds[0];
	}

	private static void myTexImage2D(GL10 gl, Bitmap bitmap) {
		// Don't loading using GLUtils, load using gl-method directly
		// GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		int[] pixels = extractPixels(bitmap);
		byte[] pixelComponents = new byte[pixels.length * 4];
		int byteIndex = 0;
		for (int i = 0; i < pixels.length; i++) {
			int p = pixels[i];
			// Convert to byte representation RGBA required by gl.glTexImage2D.
			// We don't use intbuffer, because then we
			// would be relying on the intbuffer wrapping to write the ints in
			// big-endian format, which means it would work for the wrong
			// reasons, and it might brake on some hardware.
			pixelComponents[byteIndex++] = (byte) ((p >> 16) & 0xFF); // red
			pixelComponents[byteIndex++] = (byte) ((p >> 8) & 0xFF); // green
			pixelComponents[byteIndex++] = (byte) ((p) & 0xFF); // blue
			pixelComponents[byteIndex++] = (byte) (p >> 24); // alpha
		}
		ByteBuffer pixelBuffer = ByteBuffer.wrap(pixelComponents);

		gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, bitmap.getWidth(),
				bitmap.getHeight(), 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE,
				pixelBuffer);
	}

    public static int[] extractPixels(Bitmap src) { 
            int x = 0; 
            int y = 0; 
            int w = src.getWidth(); 
            int h = src.getHeight(); 
            int[] colors = new int[w * h]; 
            src.getPixels(colors, 0, w, x, y, w, h); 
            return colors; 
    } 


}
