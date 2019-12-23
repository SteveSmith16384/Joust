package com.scs.joustgame;

import java.util.HashMap;

import com.scs.basicecs.BasicECS;
import com.scs.joustgame.ecs.systems.AnimationCycleSystem;
import com.scs.joustgame.ecs.systems.CollectorSystem;
import com.scs.joustgame.ecs.systems.CollisionSystem;
import com.scs.joustgame.ecs.systems.DrawInGameGuiSystem;
import com.scs.joustgame.ecs.systems.DrawPostGameGuiSystem;
import com.scs.joustgame.ecs.systems.DrawPreGameGuiSystem;
import com.scs.joustgame.ecs.systems.DrawingSystem;
import com.scs.joustgame.ecs.systems.InputSystem;
import com.scs.joustgame.ecs.systems.MobAISystem;
import com.scs.joustgame.ecs.systems.MoveToOffScreenSystem;
import com.scs.joustgame.ecs.systems.MovementSystem;
import com.scs.joustgame.ecs.systems.PlayerMovementSystem;
import com.scs.joustgame.ecs.systems.ProcessCollisionSystem;
import com.scs.joustgame.ecs.systems.ProcessPlayersSystem;
import com.scs.joustgame.ecs.systems.WalkingAnimationSystem;
import com.scs.joustgame.input.ControllerWrapper;
import com.scs.joustgame.input.IPlayerInput;
import com.scs.joustgame.input.KeyboardInput;
import com.scs.joustgame.models.GameData;
import com.scs.joustgame.models.PlayerData;
import com.scs.simple2dgamelib.Simple2DGameLib;

import net.java.games.input.Controller;

public final class JoustMain extends Simple2DGameLib {

	public BasicECS ecs;

	public EntityFactory entityFactory;
	public GameData gameData;
	public AnimationFramesHelper animFrameHelper;
	public LevelGenerator levelGenerator;
	public int winnerImageId;
	public int gameStage = -1; // -1, 0, or 1 for before, during and after game
	private boolean nextStage = false;
	private boolean paused = false;

	// Systems
	public InputSystem inputSystem;
	private DrawingSystem drawingSystem;
	public CollisionSystem collisionSystem;
	private MovementSystem movementSystem;
	private AnimationCycleSystem animSystem;
	private MobAISystem mobAiSystem;
	private PlayerMovementSystem playerMovementSystem;
	public ProcessCollisionSystem processCollisionSystem;
	public CollectorSystem collectorSystem;
	private WalkingAnimationSystem walkingAnimationSystem;
	private MoveToOffScreenSystem moveToOffScreenSystem;
	private DrawInGameGuiSystem drawInGameGuiSystem;
	private ProcessPlayersSystem processPlayersSystem;
	//private ScrollPlayAreaSystem scrollPlayAreaSystem;
	private DrawPreGameGuiSystem drawPreGameGuiSystem;
	private DrawPostGameGuiSystem drawPostGameGuiSystem;

	public HashMap<IPlayerInput, PlayerData> players = new HashMap<IPlayerInput, PlayerData>();

	@Override
	public void start() {
		createWindow(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, false);

		this.setBackgroundColour(255, 255, 255);

		ecs = new BasicECS();
		entityFactory = new EntityFactory(this);
		animFrameHelper = new AnimationFramesHelper(this);

		// Systems
		this.inputSystem = new InputSystem(this, ecs);
		drawingSystem = new DrawingSystem(this, ecs);
		collisionSystem = new CollisionSystem(ecs);
		movementSystem = new MovementSystem(this, ecs);
		animSystem = new AnimationCycleSystem(this, ecs);
		mobAiSystem = new MobAISystem(this, ecs);
		this.playerMovementSystem = new PlayerMovementSystem(this, ecs);
		processCollisionSystem = new ProcessCollisionSystem(this, ecs);
		this.collectorSystem = new CollectorSystem(this);
		this.walkingAnimationSystem = new WalkingAnimationSystem(this, ecs);
		this.moveToOffScreenSystem = new MoveToOffScreenSystem(this, ecs);
		this.drawInGameGuiSystem = new DrawInGameGuiSystem(this);
		this.processPlayersSystem = new ProcessPlayersSystem(this);
		//this.scrollPlayAreaSystem = new ScrollPlayAreaSystem(this, ecs);
		this.drawPreGameGuiSystem = new DrawPreGameGuiSystem(this);
		this.drawPostGameGuiSystem = new DrawPostGameGuiSystem(this);

		KeyboardInput kp = new KeyboardInput(this);
		players.put(kp, new PlayerData(kp)); // Create keyboard player by default (they might not actually join though!)
		this.checkForControllers();
		
		this.levelGenerator = new LevelGenerator(this.entityFactory, ecs);

		startPreGame();

		if (Settings.QUICKSTART) {
			this.nextStage = true; // Auto-start game
		}
	}


	@Override
	public void addPlayerForController(Controller controller) {
		if (controller.getName().toLowerCase().indexOf("keyboard") >= 0) {
			return;
		}
		if (this.players.containsKey(controller) == false) {
			PlayerData data = new PlayerData(new ControllerWrapper(controller));
			this.players.put(data.controller, data);
			p("player created for " + controller.getName());
		}
	}


	@Override
	public void removePlayerForController(Controller controller) {
		// todo
		
	}


	public void startNextStage() {
		this.nextStage = true;
	}


	private void startPreGame() {
		this.playMusic("IntroLoop.wav");

		this.removeAllEntities();

		// Reset all player data
		for (PlayerData player : players.values()) {
			player.setInGame(false);
		}
	}


	private void startPostGame() {
		this.removeAllEntities();
		this.playMusic("VictoryMusic.wav");
	}


	public void setWinner(int imageId) {
		this.nextStage = true;
		this.winnerImageId = imageId;
	}


	private void startGame() {
		this.playMusic("8BitMetal.wav");

		/*if (!Settings.RELEASE_MODE) {
			if (this.players.size() > 0) {
				if (this.players.get(0).isInGame() == false) {
					this.players.get(0).setInGame(true); // Auto-add keyboard player
				}
			}
		}*/

		gameData = new GameData();

		this.removeAllEntities();

		this.levelGenerator.createLevel1();
	}


	private int getNumPlayersInGame() {
		int count = 0;
		for (PlayerData player : players.values()) {
			if (player.isInGame()) {
				count++;
			}
		}
		return count;
	}


	@Override
	public void draw() {		
		if (!paused) {
			if (nextStage) {
				nextStage = false;
				if (this.gameStage == -1 && this.getNumPlayersInGame() > 0) {
					this.gameStage = 0;
					this.startGame();
				} else if (this.gameStage == 0) {
					this.gameStage = 1;
					startPostGame();
				} else if (this.gameStage == 1) {
					this.gameStage = -1;
					startPreGame();
				}
			}

			ecs.addAndRemoveEntities();
			ecs.processAllSystems(); // todo - split up

			this.inputSystem.process();

			if (this.gameStage == 0) {
				// loop through systems
				this.processPlayersSystem.process();
				this.moveToOffScreenSystem.process();
				this.playerMovementSystem.process();
				this.mobAiSystem.process();
				//this.scrollPlayAreaSystem.process();
				this.walkingAnimationSystem.process(); // Must be before the movementsystem, as that clears the direction
				this.movementSystem.process();
				this.animSystem.process();			
			}

			this.drawingSystem.process();
			if (this.gameStage == -1) {
				this.drawPreGameGuiSystem.process();
			} else if (this.gameStage == 0) {
				this.drawInGameGuiSystem.process();
			} else if (this.gameStage == 1) {
				this.drawPostGameGuiSystem.process();
				this.drawInGameGuiSystem.process();
			}
		}
	}


	private void removeAllEntities() {
		this.ecs.removeAllEntities();
	}


	//#############################
/*

	@Override
	public boolean keyDown(int keycode) {
		this.inputSystem.keyDown(keycode);
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		this.inputSystem.keyUp(keycode);
		return true;
	}


	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		this.inputSystem.buttonDown(controller, buttonCode);
		return false;
	}


	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		this.inputSystem.buttonUp(controller, buttonCode);
		return false;
	}


	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		this.inputSystem.axisMoved(controller, axisCode, value);
		return false;
	}
*/

	
	// ----------------------------------------------

	public static void main(String[] args) {
		new JoustMain();
	}

}

