package com.warrenfalk.mercury;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import tv.ouya.console.api.OuyaController;

import com.warrenfalk.opengl.Vec2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

/**
 * This is the abstract game model.
 * 
 * An instance of this represents a single game.
 * @author wfalk
 *
 */
public class Game implements GLSurfaceView.Renderer {
	Context context;
	float widthPx, heightPx; // these will be populated with the actual height of the view
	float widthEm, heightEm; // these will be populated with the height of the view in game units
	
	Vec2 playerCoords = new Vec2(15, 15);
	Grapple[] grapple = new Grapple[] {
		new Grapple(15, 5),
	};
	static float testVal;
	
	PlayerRenderer playerRenderer = new PlayerRenderer();
	GrappleRenderer grappleRenderer = new GrappleRenderer();
	
	public Game(Context context) {
		this.context = context;
	}
	
	public void tick() {
		OuyaController controller = OuyaController.getControllerByPlayer(0);
		if (controller != null) {
			float v = OuyaController.getControllerByPlayer(0).getAxisValue(OuyaController.AXIS_RS_Y);
			if (v != testVal) {
				testVal = v;
				Log.v("Controller", "testVal = " + testVal);
			}
			
	        float leftStickX = controller.getAxisValue(OuyaController.AXIS_LS_X);
	        float playerSpeed = Math.abs(leftStickX * 10f);
	        playerRenderer.speed = playerSpeed;
	        
			float sp = (float)(Math.pow(playerSpeed, 2) * 0.0022);
			//Log.v("Test", "" +  sp);
			playerCoords.x += sp;
			if (playerCoords.x > widthEm)
				playerCoords.x = 0f;
		}
		
		
		playerRenderer.tick();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		
		// TODO: for now, game tick on each frame, but for some games this may not be appropriate
		tick();
		
		gl.glPushMatrix();
		move(gl, playerCoords.x, playerCoords.y, 5, 3, 3, 1, 0, 0, 0, 0);
		playerRenderer.render(gl);
		gl.glPopMatrix();

		for (Grapple g : grapple) {
			grappleRenderer.grapple = g;
			grappleRenderer.render(gl);
		}
		
	}
	
	void move(GL10 gl, float x, float y, float z, float sx, float sy, float sz, float r, float rx, float ry, float rz) {
		gl.glTranslatef(x, y, z);
		gl.glScalef(sx, sy, sz);
		gl.glRotatef(r, rx, ry, rz);
	}
	
	void setupRenderers(GL10 gl) {
		playerRenderer.setup(gl, context);
		grappleRenderer.setup(gl, context);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int viewWidthPx, int viewHeightPx) {
		widthPx = viewWidthPx;
		heightPx = viewHeightPx;
		gl.glViewport(0, 0, (int)widthPx, (int)heightPx);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		float ratio = widthPx / heightPx;
		Vec2 upperLeft = new Vec2(0f, 0f);
		Vec2 lowerRight = new Vec2(widthEm = 30f * ratio, heightEm = 30f);
		gl.glOrthof(upperLeft.x, lowerRight.x, lowerRight.y, upperLeft.y, -10.0f, 10.0f);
		gl.glViewport(0, 0, (int)widthPx, (int)heightPx);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_BLEND);
        gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearColor(215f / 255f, 226f / 255f, 237f / 255f, 1.0f);
		
		setupRenderers(gl);		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

	}
}
