package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.ecs.components.PositionComponent;
import com.scs.joustgame.ecs.components.ScrollsAroundComponent;

public class ScrollPlayAreaSystem extends AbstractSystem {

	private JoustMain game;
	private float dist;
	private int highestPos, lowestPos;
	private int dir = 1;
	
	public ScrollPlayAreaSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);

		game = _game;

	}


	@Override
	public Class<?> getComponentClass() {
		return ScrollsAroundComponent.class;
	}


	@Override
	public void process() {
		/*timeUntilChange -= Gdx.graphics.getDeltaTime();  NO!  When scrolling up, the player falls through the platform.
		if (timeUntilChange < 0) {
			dir = NumberFunctions.rnd(0, 1) == 1 ? 1 : -1;
			timeUntilChange = NumberFunctions.rndFloat(2, 10);
		}*/
		
		dist = 20 * game.diff_secs * dir;
		highestPos = 0;
		lowestPos = Settings.LOGICAL_HEIGHT_PIXELS;

		super.process();

		if (highestPos < Settings.MAX_PLATFORM_HEIGHT - Settings.PLATFORM_SPACING) {
			game.levelGenerator.generateRow(highestPos + Settings.PLATFORM_SPACING, true);
		}
		/*if (lowestPos > Settings.PLATFORM_SPACING) {
			game.lvl.generateRow(lowestPos - Settings.PLATFORM_SPACING, true);
		}*/

	}


	@Override
	public void processEntity(AbstractEntity entity) {
		ScrollsAroundComponent gic = (ScrollsAroundComponent)entity.getComponent(ScrollsAroundComponent.class);
		if (gic != null) {
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			if (gic.removeWhenNearEdge) { // Only use these types to detemine high
				if (pos.rect.y < 0 || pos.rect.y > Settings.MAX_PLATFORM_HEIGHT) {
					entity.remove();
					return;
				}
				if (pos.rect.y < lowestPos) {
					lowestPos = (int)pos.rect.y; 
				}
				if (pos.rect.y > highestPos) {
					highestPos = (int)pos.rect.y; 
				}			
			} else {
				if (pos.rect.y < 0 || pos.rect.getMaxY() > Settings.LOGICAL_HEIGHT_PIXELS) {
					entity.remove();
					return;
				}
			}
			pos.rect.y += dist;
		}
	}

}
