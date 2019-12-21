package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.ecs.components.AnimationCycleComponent;
import com.scs.joustgame.ecs.components.ImageComponent;

/**
 * System for looping an entity through frames, e.g. spinning coin
 *
 */
public class AnimationCycleSystem extends AbstractSystem {

	private JoustMain game;
	
	public AnimationCycleSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return AnimationCycleComponent.class;
	}

	
	@Override
	public void processEntity(AbstractEntity entity) {
		AnimationCycleComponent data = (AnimationCycleComponent)entity.getComponent(AnimationCycleComponent.class);
		if (data != null) {
			data.timeUntilNextFrame -= game.diff;

			if (data.timeUntilNextFrame <= 0) {
				data.currentFrame++;
				if (data.currentFrame >= data.frames.length) {
					data.currentFrame = 0;
				}
				ImageComponent image = (ImageComponent)entity.getComponent(ImageComponent.class);
				image.sprite = data.frames[data.currentFrame];
				
				if (image.sprite == null) {
					throw new RuntimeException("Null sprite");
				}

				data.timeUntilNextFrame = data.interval;
			}
		}
	}

}
