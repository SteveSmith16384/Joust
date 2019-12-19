package com.scs.coopplatformer.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.coopplatformer.ecs.components.ImageComponent;
import com.scs.coopplatformer.ecs.components.MovementComponent;
import com.scs.coopplatformer.ecs.components.WalkingAnimationComponent;

public class WalkingAnimationSystem extends AbstractSystem {

	public WalkingAnimationSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return WalkingAnimationComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		try {
			WalkingAnimationComponent wac = (WalkingAnimationComponent)entity.getComponent(WalkingAnimationComponent.class);
			if (wac != null) {
				MovementComponent mc = (MovementComponent)entity.getComponent(MovementComponent.class);
				int dir = (int)Math.signum(mc.offX);

				if (wac.currentDir != dir) {
					wac.currentFrame = 0;
					wac.currentDir = dir;
					wac.timeUntilNextFrame = wac.interval;
				} else {
					wac.timeUntilNextFrame -= game.diff;
					if (wac.timeUntilNextFrame <= 0) {
						wac.currentFrame++;
						if (wac.currentFrame >= wac.framesLeft.length) {
							wac.currentFrame = 0;
						}
						wac.timeUntilNextFrame = wac.interval;
					}
				}

				ImageComponent image = (ImageComponent)entity.getComponent(ImageComponent.class);
				if (dir == -1) {
					image.sprite = wac.framesLeft[wac.currentFrame];
				} else if (dir == 1) {
					image.sprite = wac.framesRight[wac.currentFrame];
				} else {
					image.sprite = wac.idleFrame;
				}
				
				if (image.sprite == null) {
					throw new RuntimeException("null sprite for " + entity + "!");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
