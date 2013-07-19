package com.warrenfalk.opengl;

public class Vec3 extends Vec2 {
	public float z;
	
	public Vec3() {
	}
	
	public Vec3(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
}
