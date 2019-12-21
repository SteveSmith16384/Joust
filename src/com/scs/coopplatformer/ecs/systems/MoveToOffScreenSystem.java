package com.scs.coopplatformer.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.coopplatformer.MyGdxGame;
import com.scs.coopplatformer.Settings;
import com.scs.coopplatformer.ecs.components.MoveOffScreenComponent;
import com.scs.coopplatformer.ecs.components.PositionComponent;

public class MoveToOffScreenSystem extends AbstractSystem {

	private MyGdxGame game;
	
	public MoveToOffScreenSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return MoveOffScreenComponent.class;
	}

	
	@Override
	public void processEntity(AbstractEntity entity) {
		MoveOffScreenComponent gic = (MoveOffScreenComponent)entity.getComponent(MoveOffScreenComponent.class);
		if (gic != null) {
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			if (pos.rect.right < 0 || pos.rect.top < 0 || pos.rect.left > Settings.LOGICAL_WIDTH_PIXELS || pos.rect.bottom > Settings.LOGICAL_HEIGHT_PIXELS) {
				entity.remove();
				return;
			}
			//MovementComponent mc = (MovementComponent)entity.getComponent(MovementComponent.class);
			//mc.offX = gic.offX;
			//mc.offY = gic.offY;
			pos.rect.move(gic.offX * game.diff, gic.offY * game.diff);

		}
	}

}
