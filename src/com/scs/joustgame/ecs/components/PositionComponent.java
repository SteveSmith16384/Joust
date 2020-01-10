package com.scs.joustgame.ecs.components;

import java.awt.geom.Rectangle2D;

import com.scs.awt.Edge;

public class PositionComponent {

	public Rectangle2D.Float rect;
	public Rectangle2D.Float prevPos;
	public Edge edge;
	
	public static PositionComponent ByBottomLeft(float x, float y, float w, float h) {
		PositionComponent pos = new PositionComponent();
		pos.rect = new Rectangle2D.Float(x, y-h, w, h);
		return pos;
	}


	public static PositionComponent ByTopLeft(float x, float y, float w, float h) {
		PositionComponent pos = new PositionComponent();
		pos.rect = new Rectangle2D.Float(x, y, w, h);
		return pos;
	}

	
	public static PositionComponent FromEdge(float x1, float y1, float x2, float y2) {
		PositionComponent pos = new PositionComponent();
		pos.edge = new Edge(x1, y1, x2, y2);
		return pos;
	}

	
	private PositionComponent() {
		prevPos = new Rectangle2D.Float();
	}

}
