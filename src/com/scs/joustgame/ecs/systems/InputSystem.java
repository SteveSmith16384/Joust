package com.scs.joustgame.ecs.systems;

import java.awt.event.KeyEvent;

import com.scs.joustgame.JoustMain;
import com.scs.joustgame.models.PlayerData;

public class InputSystem { //extends AbstractSystem {

	private JoustMain game;
	private volatile boolean key[] = new boolean[256];

	public InputSystem(JoustMain _game) {//, BasicECS ecs) {
		//super(ecs);

		game = _game;
	}

	/*
	@Override
	public Class<?> getComponentClass() {
		return PlayersAvatarComponent.class;
	}
	 */

	//@Override
	public void process() {
		if (game.isKeyPressed(KeyEvent.VK_S) && game.gameStage != 0) { // S to start
			key[KeyEvent.VK_S] = false;
			game.startNextStage();
		}

		for (PlayerData player : game.players) {
			player.moveLeft = player.controller.isLeftPressed();
			player.moveRight = player.controller.isRightPressed();
			player.jumpOrFlap = player.controller.isJumpPressed();

			if (player.jumpOrFlap) {
				if (player.isInGame() == false) {
					//game.p("Keyboard player joined");
					player.setInGame(true);
					break;
				}
			}
		}

		if (game.gameStage == -1) {
			/*todo if (game.isKeyPressed(KeyEvent.VK_SPACE)) { // Space for keyboard player to join
				key[KeyEvent.VK_SPACE] = false;
				for (PlayerData player : game.players) {
					if (player.controller instanceof KeyboardInput) {
						if (player.isInGame() == false) {
							//game.p("Keyboard player joined");
							player.setInGame(true);
							break;
						}
					}
				}
			}*/

		} else if (game.gameStage == 0) {
			//super.process();
		}
	}


	public void keyDown(int keycode) {
		/*if (!Settings.RELEASE_MODE) {
			JoustMain.p("key pressed: " + keycode);
		}*/
		key[keycode] = true;
	}


	public void keyUp(int keycode) {
		/*if (!Settings.RELEASE_MODE) {
			//Settings.p("key released: " + keycode);
		}*/
		key[keycode] = false;
	}

	/*
	public boolean buttonDown(Controller controller, int buttonCode) {
		if (buttonCode == 1) {
			if (game.gameStage == -1) {
				PlayerData player = game.players.get(controller);
				player.setInGame(true);
			} else if (game.gameStage == 0) {
				AbstractEntity entity = game.players.get(controller).avatar;
				if (entity != null) {
					PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
					uic.jump = true;
				}
			}
		}
		return false;
	}


	public boolean buttonUp(Controller controller, int buttonCode) {
		if (buttonCode == 1) {
			if (game.gameStage == 0) {
				AbstractEntity entity = game.players.get(controller).avatar;
				if (entity != null) {
					PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
					uic.jump = false;
				}
			}
		}
		return false;
	}


	public boolean axisMoved(Controller controller, int axisCode, float value) {
		if (Settings.RELEASE_MODE == false) {
			float val = controller.getAxis(axisCode);
			if (val < -0.5f || val > .5f) {
				MyGdxGame.p("Axis " + axisCode + ": " + controller.getAxis(axisCode));
			}
		}
		if (axisCode == Settings.AXIS) {
			if (game.gameStage == 0) {
				AbstractEntity entity = game.players.get(controller).avatar;
				if (entity != null) {
					PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
					uic.moveLeft = uic.controller.getAxis(Settings.AXIS) < -0.5f;
					uic.moveRight = uic.controller.getAxis(Settings.AXIS) > 0.5f;
				}
			}
		}
		return false;
	}
	 */
}
