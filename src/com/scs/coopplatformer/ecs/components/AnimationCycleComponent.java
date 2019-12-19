package com.scs.coopplatformer.ecs.components;

import com.scs.simple2dgamelib.Sprite;

public class AnimationCycleComponent {

	public int currentFrame;
	public Sprite[] frames;
	public float interval;
	public float timeUntilNextFrame;
	
	public AnimationCycleComponent(float _interval) {
		interval = _interval;
		timeUntilNextFrame = 100;//Gdx.graphics.getDeltaTime() % interval; // So they all animate at the same time 
	}
	
}
