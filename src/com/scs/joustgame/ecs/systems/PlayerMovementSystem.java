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
			if (uic.player.jumpOrFlap) {
				game.playSound("flap.wav");
				mc.offY -= 50; // Flap up
			}
			JumpingComponent jc = (JumpingComponent)player.getComponent(JumpingComponent.class);
			/*if (uic.player.moveLeft && (uic.player.jumpOrFlap || jc.canJump)) {
				mc.offX -= Settings.JOUST_PLAYER_SPEED;
			} else if (uic.player.moveRight && (uic.player.jumpOrFlap || jc.canJump)) {
				mc.offX += Settings.JOUST_PLAYER_SPEED;
			}*/
			if (uic.player.moveLeft) {
				if (uic.player.jumpOrFlap) {
					mc.offX -= Settings.JOUST_PLAYER_SPEED * 4;
				}
				if (jc.canJump) {
					mc.offX -= Settings.JOUST_PLAYER_SPEED;
				}
			}
			if (uic.player.moveRight) {
				if (uic.player.jumpOrFlap) {
					mc.offX += Settings.JOUST_PLAYER_SPEED * 4;
				}
				if (jc.canJump) {
					mc.offX += Settings.JOUST_PLAYER_SPEED;
				}
			}
			
				if (uic.player.jumpOrFlap) {
					uic.player.jumpOrFlap = false;
				}
				mc.offX *= 0.999f; // Drag

			} else {
				if (uic.player.moveLeft) {
					mc.offX = -Settings.NORMAL_PLAYER_SPEED;
				} else if (uic.player.moveRight) {
					mc.offX = Settings.NORMAL_PLAYER_SPEED;
				}

				if (uic.player.jumpOrFlap) {
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