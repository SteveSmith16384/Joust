package com.scs.coopplatformer.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.coopplatformer.ecs.components.MovingPlatformComponent;

/*
 * Not implemented yet
 */
public class MovingPlatformSystem extends AbstractSystem {

	public MovingPlatformSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MovingPlatformComponent mpc = (MovingPlatformComponent)entity.getComponent(MovingPlatformComponent.class);
		// todo
	}

}
