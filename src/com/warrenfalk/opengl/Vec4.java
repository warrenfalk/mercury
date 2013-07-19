package com.warrenfalk.opengl;

public class Vec4 extends Vec3 {
	public float w;
	
	public Vec4() {
	}
	
	public Vec4(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}
}
