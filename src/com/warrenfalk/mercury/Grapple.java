package com.warrenfalk.mercury;

import com.warrenfalk.opengl.Vec2;

public class Grapple {
	public Vec2[] points;

	public Grapple(float x, float y) {
		int pointCount = 25;
		points = new Vec2[pointCount];
		
		for (int i = 0; i < pointCount; i++) {
			points[i] = new Vec2(x, y);
			x += 0.4f;
		}
	}
}
