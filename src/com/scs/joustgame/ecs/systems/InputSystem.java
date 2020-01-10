package com.scs.joustgame.ecs.systems;

import java.awt.event.KeyEvent;

import com.scs.joustgame.JoustMain;
import com.scs.joustgame.models.PlayerData;

public class InputSystem {

	private JoustMain game;
	private volatile boolean key[] = new boolean[256];

	public InputSystem(JoustMain _game) {
		game = _game;
	}


	public void process() {
		if (game.isKeyPressed(KeyEvent.VK_S) && game.gameStage != 0) { // S to start
			key[KeyEvent.VK_S] = false;
			game.startNextStage();
		}

		for (PlayerData player : game.players) {
			player.controller.poll();
			player.moveLeft = player.controller.isLeftPressed();
			player.moveRight = player.controller.isRightPressed();
			
			if (player.controller.isJumpPressed()) {
				player.jumpOrFlap = true && player.wasJumpOrFlap == false;
				player.wasJumpOrFlap = true;
			} else {
				player.jumpOrFlap = false;
				player.wasJumpOrFlap = false;
			}
			
			//JoustMain.p(player.jumpOrFlap ? "Flap!" : "noflap");

			if (player.jumpOrFlap) {
				if (player.isInGame() == false) {
					player.setInGame(true);
					game.addLogEntry("Player " + player.imageId + " joined");
					break;
				}
			}
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

}
