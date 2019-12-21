package com.scs.joustgame.ecs.components;

import com.scs.simple2dgamelib.Sprite;

public class WalkingAnimationComponent {

	public int currentFrame;
	public Sprite[] framesLeft;
	public Sprite[] framesRight;
	public Sprite idleFrame;
	public float interval;
	public float timeUntilNextFrame;
	public int currentDir = 0;
	
	public WalkingAnimationComponent(float _interval) {
		interval = _interval;
	}

}
