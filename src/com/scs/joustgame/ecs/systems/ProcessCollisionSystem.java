package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.ecs.components.CanBeHarmedComponent;
import com.scs.joustgame.ecs.components.CanCollectComponent;
import com.scs.joustgame.ecs.components.CollectableComponent;
import com.scs.joustgame.ecs.components.HarmOnContactComponent;
import com.scs.joustgame.ecs.components.KillByJumpingOnComponent;
import com.scs.joustgame.ecs.components.MobComponent;
import com.scs.joustgame.ecs.components.PlayersAvatarComponent;
import com.scs.joustgame.ecs.components.PositionComponent;
import com.scs.joustgame.models.CollisionResults;
import com.scs.joustgame.models.PlayerData;

public class ProcessCollisionSystem extends AbstractSystem {

	private JoustMain game;

	public ProcessCollisionSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	public void processCollision(AbstractEntity mover, CollisionResults results) {
		if (Settings.JOUST_MOVEMENT) {
			/*{
				// Cancel momentum if applicable
				if (results.moveBack) {
					PlayersAvatarComponent movingPlayer = (PlayersAvatarComponent)mover.getComponent(PlayersAvatarComponent.class);
					if (movingPlayer != null) {
						movingPlayer.momentum.x = 0;
						movingPlayer.momentum.y = 0;
					}
				}
			}*/
			{
				// Player moving into player
				PlayersAvatarComponent movingPlayer = (PlayersAvatarComponent)mover.getComponent(PlayersAvatarComponent.class);
				if (movingPlayer != null) {
					PlayersAvatarComponent hitPlayer = (PlayersAvatarComponent)results.collidedWith.getComponent(PlayersAvatarComponent.class);
					if (hitPlayer != null) {
						PositionComponent movingPos = (PositionComponent)mover.getComponent(PositionComponent.class);
						PositionComponent hitPlayerPos = (PositionComponent)results.collidedWith.getComponent(PositionComponent.class);
						if (movingPos.rect.y < hitPlayerPos.rect.y) {
							this.playerKilled(results.collidedWith, hitPlayer.timeStarted);
						} else if (movingPos.rect.y > hitPlayerPos.rect.y) {
							this.playerKilled(mover, movingPlayer.timeStarted);
						}
					}
				}
			}
		}

		{
			PlayersAvatarComponent dbm = (PlayersAvatarComponent)mover.getComponent(PlayersAvatarComponent.class);
			if (dbm != null) {
				// Player moving into mob
				MobComponent mob = (MobComponent)results.collidedWith.getComponent(MobComponent.class);
				if (mob != null) {
					// Player jumping on mob
					if (results.fromAbove) {
						KillByJumpingOnComponent kbj = (KillByJumpingOnComponent)mover.getComponent(KillByJumpingOnComponent.class);
						if (kbj != null) {
							results.collidedWith.remove();
							//game.sfx.play("Laser.ogg");
							AbstractEntity fall = game.entityFactory.createFallingMob(results.collidedWith);
							game.ecs.addEntity(fall);

							// Give player points
							//PlayersAvatarComponent uic = (PlayersAvatarComponent)mover.getComponent(PlayersAvatarComponent.class);
							//if (uic != null) {
							dbm.player.score += 200;
							//}

							return;
						}
					}
					this.playerKilled(mover, dbm.timeStarted);
					return;

				}
			}
		}

		// Mob moving into player
		{
			MobComponent mob = (MobComponent)mover.getComponent(MobComponent.class);
			if (mob != null) {
				PlayersAvatarComponent dbm = (PlayersAvatarComponent)results.collidedWith.getComponent(PlayersAvatarComponent.class);
				if (dbm != null) {
					this.playerKilled(results.collidedWith, dbm.timeStarted);
					return;
				}
			}
		}

		// Generic harm
		{
			CanBeHarmedComponent cbh = (CanBeHarmedComponent)mover.getComponent(CanBeHarmedComponent.class);
			if (cbh != null) {
				HarmOnContactComponent hoc = (HarmOnContactComponent)results.collidedWith.getComponent(HarmOnContactComponent.class);
				if (hoc != null) {
					mover.remove();
					return;
				}
			}
		}

		// Collecting - player moves into collectable
		{
			CanCollectComponent ccc = (CanCollectComponent)mover.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)results.collidedWith.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(mover, results.collidedWith);
					return;
				}
			}
		}

		// Collecting - collectable moves into player
		{
			CanCollectComponent ccc = (CanCollectComponent)results.collidedWith.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)mover.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(results.collidedWith, mover);
					return;
				}
			}
		}
	}


	public void playerKilled(AbstractEntity avatar, long timeStarted) {
		long diff = System.currentTimeMillis() - timeStarted;
		if (diff < 4000) { // Invincible for 4 seconds
			if (!Settings.RELEASE_MODE) {
				JoustMain.p("Player invincible!");
			}
			return;
		}

		//game.sfx.play("Falling.mp3");

		avatar.remove();
		game.ecs.addEntity(game.entityFactory.createDeadPlayer(avatar));

		PlayersAvatarComponent uic = (PlayersAvatarComponent)avatar.getComponent(PlayersAvatarComponent.class);
		PlayerData player = uic.player;
		player.avatar = null;
		player.timeUntilAvatar = Settings.AVATAR_RESPAWN_TIME_SECS;
		player.lives--;

	}

}
