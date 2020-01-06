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
		MovementComponent mc = (MovementComponent)player.getComponent(MovementComponent.class);

		if (Settings.JOUST_MOVEMENT) {
			if (uic.jumpOrFlap) {
				mc.offY -= 5; // Flap up
			}
			JumpingComponent jc = (JumpingComponent)player.getComponent(JumpingComponent.class);
			if (uic.moveLeft && (uic.jumpOrFlap || jc.canJump)) {
				mc.offX -= Settings.JOUST_PLAYER_SPEED;
			} else if (uic.moveRight && (uic.jumpOrFlap || jc.canJump)) {
				mc.offX += Settings.JOUST_PLAYER_SPEED;
			}
			if (uic.jumpOrFlap) {
				uic.jumpOrFlap = false;
			}
			mc.offX *= 0.99f;

		} else {
			if (uic.moveLeft) {
				mc.offX = -Settings.NORMAL_PLAYER_SPEED;
			} else if (uic.moveRight) {
				mc.offX = Settings.NORMAL_PLAYER_SPEED;
			}

			if (uic.jumpOrFlap) {
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