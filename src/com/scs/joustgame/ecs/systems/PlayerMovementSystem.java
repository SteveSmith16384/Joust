package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.ecs.components.JumpingComponent;
import com.scs.joustgame.ecs.components.MovementComponent;
import com.scs.joustgame.ecs.components.PlayersAvatarComponent;

public class PlayerMovementSystem extends AbstractSystem {

	private JoustMain game;

	public PlayerMovementSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return PlayersAvatarComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity player) {
		PlayersAvatarComponent uic = (PlayersAvatarComponent)player.getComponent(PlayersAvatarComponent.class);
		if (uic != null) {
			MovementComponent mc = (MovementComponent)player.getComponent(MovementComponent.class);

			if (uic.moveLeft) {
				mc.offX = -Settings.PLAYER_SPEED;
			} else if (uic.moveRight) {
				mc.offX = Settings.PLAYER_SPEED;
			}

			if (uic.jump) {
				JumpingComponent jc = (JumpingComponent)player.getComponent(JumpingComponent.class);
				if (jc.canJump) {
					//game.sfx.play("BonusCube.ogg");
					mc.offY = -Settings.JUMP_FORCE;
					jc.canJump = false;
				} else {
					//JoustMain.p("Cannot jump!");
				}
			}
		}
	}


}