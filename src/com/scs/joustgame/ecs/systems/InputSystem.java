package com.scs.joustgame.ecs.systems;

import java.awt.event.KeyEvent;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.ecs.components.PlayersAvatarComponent;
import com.scs.joustgame.input.KeyboardInput;
import com.scs.joustgame.models.PlayerData;

public class InputSystem extends AbstractSystem {//implements ControllerListener {

	private JoustMain game;
	private volatile boolean key[] = new boolean[256];

	public InputSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return PlayersAvatarComponent.class;
	}


	@Override
	public void process() {
		if (game.isKeyPressed(KeyEvent.VK_S) && game.gameStage != 0) { // S to start
			key[KeyEvent.VK_S] = false;
			game.startNextStage();
		}

		if (game.gameStage == -1) {
			if (game.isKeyPressed(KeyEvent.VK_SPACE)) { // Space for keyboard player to join
				key[KeyEvent.VK_SPACE] = false;
				for (PlayerData player : game.players.values()) {
					if (player.controller instanceof KeyboardInput) {
						if (player.isInGame() == false) {
							game.p("Keyboard player joined");
							player.setInGame(true);
							break;
						}
					}
				}
			}

			// See if players want to join
			/*if (Settings.CONTROLLER_MODE_1) {
				for (PlayerData player : game.players.values()) {
					if (player.isInGame() == false) {
						if (player.controller != null) {
							if (player.controller.getButton(1)) {
								MyGdxGame.p("Controller player joined!");
								player.setInGame(true);
							}
						}
					}
				}
			}*/
		} else if (game.gameStage == 0) {
			super.process();
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		/*PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
		if (uic != null) {
			if (uic.controller != null) {
				if (!Settings.RELEASE_MODE) {
					// 1 = right stick l/r
					// 2 = left stick u/d
					// 3 = left stick l.r
					//MyGdxGame.p("Axis:" + uic.controller.getAxis(3));
					// 0 =square
					if (uic.controller.getButton(1)) {
						MyGdxGame.p("button!");
					}
				}
				if (Settings.CONTROLLER_MODE_1) {
					uic.moveLeft = uic.controller.getAxis(Settings.AXIS) < -0.5f;
					uic.moveRight = uic.controller.getAxis(Settings.AXIS) > 0.5f;
					uic.jump = uic.controller.getButton(1);
				}
			} else {
				uic.moveLeft = key[29];
				uic.moveRight = key[32];
				uic.jump = key[51] || key[62];  // W or space
			}
		}*/
	}


	public void keyDown(int keycode) {
		if (!Settings.RELEASE_MODE) {
			JoustMain.p("key pressed: " + keycode);
		}
		key[keycode] = true;
	}


	public void keyUp(int keycode) {
		if (!Settings.RELEASE_MODE) {
			//Settings.p("key released: " + keycode);
		}

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
