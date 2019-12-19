package com.scs.coopplatformer.ecs.components;

public class MovementComponent {

	public float offX, offY;
	public boolean canFall;
	
	public MovementComponent(boolean _canFall) {
		canFall = _canFall;
	}

}
