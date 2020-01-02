package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.ecs.components.JumpingComponent;
import com.scs.joustgame.ecs.components.MovementComponent;
import com.scs.joustgame.ecs.components.PositionComponent;
import com.scs.joustgame.models.CollisionResults;

public class MovementSystem extends AbstractSystem {

	private JoustMain game;

	public MovementSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return MovementComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity movingEntity) {
		MovementComponent md = (MovementComponent)movingEntity.getComponent(MovementComponent.class);
			PositionComponent pos = (PositionComponent)movingEntity.getComponent(PositionComponent.class);
			//MyGdxGame.p("Mob pos :" + pos.rect);
			if (md.offX != 0) {
				pos.prevPos.setFrame(pos.rect);
				float totalDist = md.offX * game.diff_secs;
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
					//MyGdxGame.p("Max movement hit!");					
				}
				pos.rect.add(totalDist, 0);
				CollisionResults results = game.collisionSystem.collided(movingEntity, md.offX, 0);
				md.offX = 0;
				if (results != null) {
					if (results.moveBack) {
						pos.rect.setFrame(pos.prevPos); // Move back
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
				} else {
					if (pos.rect.getCenterX() < 0) {
						pos.rect.add(Settings.LOGICAL_WIDTH_PIXELS, 0);
					} else if (pos.rect.getCenterX() > Settings.LOGICAL_WIDTH_PIXELS) {
						pos.rect.add(-Settings.LOGICAL_WIDTH_PIXELS, 0);
					}
				}
			}
			if (md.offY != 0) {
				pos.prevPos.setFrame(pos.rect);
				float totalDist = md.offY * game.diff_secs;
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					//MyGdxGame.p("Max movement hit!");					
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
				}
				pos.rect.add(0, totalDist);
				CollisionResults results = game.collisionSystem.collided(movingEntity, 0, md.offY);
				if (results != null) {
					if (results.moveBack) {
						pos.rect.setFrame(pos.prevPos); // Move back
						if (md.offY > 0) {
							JumpingComponent jc = (JumpingComponent)movingEntity.getComponent(JumpingComponent.class);
							if (jc != null) {
								jc.canJump = true;
							}
						}
						if (md.canFall) {
							md.offY = 0;
						}
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
				} else {
					if (md.offY > 0) {
						JumpingComponent jc = (JumpingComponent)movingEntity.getComponent(JumpingComponent.class);
						if (jc != null) {
							jc.canJump = false;
						}
					}

					if (pos.rect.getMaxY() < 0) { // Fallen off bottom of screen
						pos.rect.add(0, Settings.LOGICAL_HEIGHT_PIXELS); // Re-appear at the top
						/*PlayersAvatarComponent dbm = (PlayersAvatarComponent)movingEntity.getComponent(PlayersAvatarComponent.class);
						if (dbm != null) {
							game.processCollisionSystem.playerKilled(movingEntity, -1);
						}*/
					}
				}
			}
			if (md.canFall) {
				// Gravity
				if (!Settings.TURN_OFF_GRAVITY) {
					md.offY += Settings.GRAVITY * game.diff_secs;
				}
			} else {
				md.offY = 0;
			}
	}

}
