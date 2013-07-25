package com.warrenfalk.mercury;

import javax.microedition.khronos.opengles.GL10;

import tv.ouya.console.api.OuyaController;

import com.warrenfalk.opengl.Utils;
import com.warrenfalk.opengl.Vec2;

import android.content.Context;
import android.util.Log;

public class PlayerRenderer implements ObjectRenderer {
	
	public float speed = 0f;
	public long strideTick = 0L;
	float testVal = 0f;
	
	final static float FRAMES_PER_SECOND = 60f;
	final static long STRIDE_RESOLUTION = 1000L;
	
	final static int TORSO = 0;
	final static int HEAD = 1;
	final static int UP_ARM = 2;
	final static int LOW_ARM = 3;
	final static int UP_LEG = 4;
	final static int LOW_LEG = 5;
	final static int UP_ARM2 = 6;
	final static int LOW_ARM2 = 7;
	final static int UP_LEG2 = 8;
	final static int LOW_LEG2 = 9;
	
	final static float PI = (float)Math.PI;
	
	static final class Component {
		public final Vec2 center;
		public final Vec2 position;
		public final float scale;
		public final int rotationSlot;
		public final ObjectRenderer renderer;
		public final Component[] subbelow;
		public final Component[] subabove;
		
		public Component(float cx, float cy, float x, float y, float scale, int rotationSlot, ObjectRenderer renderer, Component[] subbelow, Component[] subabove) {
			this.center = new Vec2(cx, cy);
			this.position = new Vec2(x, y);
			this.scale = scale;
			this.rotationSlot = rotationSlot;
			this.renderer = renderer;
			this.subbelow = subbelow;
			this.subabove = subabove;
		}
	}
	
	Component torso = new Component(
			0.5f, 0.5f,
			0.5f, 0.388f,
			0.35f,
			TORSO,
			new SpriteRenderer(R.drawable.player_torso),
			new Component[] {
				new Component(
						0.43f, 0.21f,
						-0.05f, -0.19f,
						0.78f,
						UP_ARM2,
						new SpriteRenderer(R.drawable.player_uparm),
						new Component[] {
								new Component(
										0.445f, 0.242f,
										-0.041f, 0.61f,
										0.78f,
										LOW_ARM2,
										new SpriteRenderer(R.drawable.player_lowarm),
										new Component[0],
										new Component[0]
										),
							},
						new Component[0]
						),
				new Component(
						0.54f, 0.33f,
						0.024f, 0.57f,
						0.89f,
						UP_LEG2,
						new SpriteRenderer(R.drawable.player_upleg),
						new Component[] {
							new Component(
									0.547f, 0.086f,
									0.125f, 0.53f,
									0.87f,
									LOW_LEG2,
									new SpriteRenderer(R.drawable.player_lowleg),
									new Component[0],
									new Component[0]
									)
						},
						new Component[0]
						),
				new Component(
						0.54f, 0.33f,
						0.024f, 0.57f,
						0.89f,
						UP_LEG,
						new SpriteRenderer(R.drawable.player_upleg),
						new Component[] {
							new Component(
									0.547f, 0.086f,
									0.125f, 0.53f,
									0.87f,
									LOW_LEG,
									new SpriteRenderer(R.drawable.player_lowleg),
									new Component[0],
									new Component[0]
									)
						},
						new Component[0]
						),
			},
			new Component[] {
				new Component(
						0.4f, 0.9f,
						-0.04f, -0.49f,
						0.676f,
						HEAD,
						new SpriteRenderer(R.drawable.player_head),
						new Component[0],
						new Component[0]
						),
				new Component(
						0.50f, 0.25f,
						0.02f, -0.15f,
						0.78f,
						UP_ARM,
						new SpriteRenderer(R.drawable.player_uparm),
						new Component[] {
							new Component(
									0.445f, 0.242f,
									-0.041f, 0.61f,
									0.78f,
									LOW_ARM,
									new SpriteRenderer(R.drawable.player_lowarm),
									new Component[0],
									new Component[0]
									),
						},
						new Component[0]
						),
			}
			);
	
	void tick() {
		float s = speed;
		if (s < 0.1f) {
			s = 0f;
			strideTick = 0L;
		}
		else {
			strideTick += (long)(STRIDE_RESOLUTION * speed);
		}
	}
	
	@Override
	public void setup(GL10 gl, Context context) {
		setup(torso, gl, context);
	}
	
	void setup(Component component, GL10 gl, Context context) {
		component.renderer.setup(gl, context);
		for (Component sub : component.subbelow)
			setup(sub, gl, context);
		for (Component sub : component.subabove)
			setup(sub, gl, context);
	}
	
	float stridesPerSec() {
		return speed / 5f;
	}
	
	float sinFunc(float lowAngle, float highAngle, float cycleFactor, float cycleOffset) {
		float q = STRIDE_RESOLUTION * 300f / cycleFactor;
		float stridePos = (float)(strideTick % q) / (float)q;
		float angle = lowAngle + (highAngle - lowAngle) * 0.5f * (1 + (float)Math.sin((stridePos + cycleOffset) * 2.0 * PI));
		return angle;
	}
	
	float getRotation(int slot) {
		switch (slot) {
		case TORSO:
			return 3f + sinFunc(speed * 1f, speed * 1.4f, 2f, -0.33f);
		case HEAD:
			return -3f + sinFunc(speed * -1.3f, speed * -0.7f, 2f, -0.8f);
		case UP_ARM:
			return sinFunc(speed * -6.5f, speed * 6.5f, 1f, 0.5f);
		case UP_ARM2:
			return sinFunc(speed * -6.5f, speed * 6.5f, 1f, 0f);
		case LOW_ARM:
			return -3f + sinFunc(speed * -3.0f, speed * -9.0f, 1f, 0f);
		case LOW_ARM2:
			return -3f + sinFunc(speed * -3.0f, speed * -9.0f, 1f, 0.5f);
		case UP_LEG:
			return sinFunc(speed * -6.5f, speed * 6.5f, 1f, 0f);
		case LOW_LEG:
			return sinFunc(speed * -1f, speed * 9f, 1f, 0.223f + 0.5f);
		case UP_LEG2:
			return sinFunc(speed * -6.5f, speed * 6.5f, 1f, 0.5f);
		case LOW_LEG2:
			return sinFunc(speed * -1f, speed * 9f, 1f, 0.223f);
		default:
			return 0f;
		}
	}

	@Override
	public void render(GL10 gl) {
		gl.glColor4f(0, 0, 0, 1);
		//Utils.rect(gl); 
		
		render(torso, gl);

	}

	void render(Component comp, GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(comp.position.x, comp.position.y, 0f);
		gl.glScalef(comp.scale, comp.scale, 1f);
		gl.glRotatef(getRotation(comp.rotationSlot), 0f, 0f, 1f);
		for (Component sub : comp.subbelow)
			render(sub, gl);
		gl.glTranslatef(-comp.center.x, -comp.center.y, 0f);
		comp.renderer.render(gl);
		gl.glTranslatef(comp.center.x, comp.center.y, 0f);
		for (Component sub : comp.subabove)
			render(sub, gl);
		gl.glPopMatrix();
	}

	@Override
	public void destroy() {
		destroy(torso);
	}
	
	void destroy(Component component) {
		component.renderer.destroy();
		for (Component sub : component.subbelow)
			destroy(sub);
		for (Component sub : component.subabove)
			destroy(sub);
	}

}
