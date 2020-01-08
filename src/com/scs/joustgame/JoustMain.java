package com.scs.joustgame;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.scs.basicecs.BasicECS;
import com.scs.joustgame.ecs.systems.AnimationCycleSystem;
import com.scs.joustgame.ecs.systems.CollectorSystem;
import com.scs.joustgame.ecs.systems.CollisionCheckSystem;
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
import com.scs.joustgame.ecs.systems.ScrollPlayAreaSystem;
import com.scs.joustgame.ecs.systems.WalkingAnimationSystem;
import com.scs.joustgame.input.ControllerInput;
import com.scs.joustgame.input.KeyboardCursorInput;
import com.scs.joustgame.input.KeyboardOPSpaceInput;
import com.scs.joustgame.input.KeyboardWASDInput;
import com.scs.joustgame.models.GameData;
import com.scs.joustgame.models.PlayerData;
import com.scs.simple2dgameframework.Simple2DGameFramework;

import net.java.games.input.Controller;

public final class JoustMain extends Simple2DGameFramework {

	public BasicECS ecs;

	public ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	public EntityFactory entityFactory;
	public GameData gameData;
	public AnimationFramesHelper animFrameHelper;
	public LevelGenerator levelGenerator;
	public int winnerImageId;
	public int gameStage = -1; // -1, 0, or 1 for before, during and after game
	private boolean nextStage = false;
	private ArrayList<String> log = new ArrayList<String>(); 

	// Systems
	public InputSystem inputSystem;
	private DrawingSystem drawingSystem;
	public CollisionCheckSystem collisionSystem;
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
	private ScrollPlayAreaSystem scrollPlayAreaSystem;
	private DrawPreGameGuiSystem drawPreGameGuiSystem;
	private DrawPostGameGuiSystem drawPostGameGuiSystem;

	public JoustMain() {
		super(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS);

		this.setScreenSize(Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS);
		super.setFont(new Font("Helvetica", Font.BOLD, 24));
	}


	@Override
	public void init() {
		this.setBackgroundColour(255, 255, 255);

		ecs = new BasicECS();
		entityFactory = new EntityFactory(this);
		animFrameHelper = new AnimationFramesHelper(this);

		// Systems
		this.inputSystem = new InputSystem(this);
		drawingSystem = new DrawingSystem(this, ecs);
		collisionSystem = new CollisionCheckSystem(ecs);
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
		this.scrollPlayAreaSystem = new ScrollPlayAreaSystem(this, ecs);
		this.drawPreGameGuiSystem = new DrawPreGameGuiSystem(this);
		this.drawPostGameGuiSystem = new DrawPostGameGuiSystem(this);

		KeyboardWASDInput keyboardInput = new KeyboardWASDInput(this);
		PlayerData keyboardPlayer1 = new PlayerData(keyboardInput); 
		players.add(keyboardPlayer1); // Create keyboard player by default (they might not actually join though!)
		
		KeyboardOPSpaceInput keyboardInput2 = new KeyboardOPSpaceInput(this);
		PlayerData keyboardPlayer2 = new PlayerData(keyboardInput2); 
		players.add(keyboardPlayer2); // Create keyboard player by default (they might not actually join though!)

		KeyboardCursorInput keyboardInput3 = new KeyboardCursorInput(this);
		PlayerData keyboardPlayer3 = new PlayerData(keyboardInput3); 
		players.add(keyboardPlayer3); // Create keyboard player by default (they might not actually join though!)

		this.levelGenerator = new LevelGenerator(this.entityFactory, ecs);

		startPreGame();

		if (Settings.QUICKSTART) {
			this.nextStage = true; // Auto-start game
			keyboardPlayer1.setInGame(true);
		}
	}


	public void startNextStage() {
		this.nextStage = true;
	}


	private void startPreGame() {
		this.playMusic("IntroLoop.wav");

		this.ecs.removeAllEntities();

		// Reset all player data
		for (PlayerData player : players) {
			player.setInGame(false);
		}
	}


	private void startPostGame() {
		this.ecs.removeAllEntities();
		this.playMusic("VictoryMusic.wav");
	}


	public void setWinner(int imageId) {
		this.nextStage = true;
		this.winnerImageId = imageId;
	}


	private void startGame() {
		this.playMusic("8BitMetal.wav");

		gameData = new GameData();

		this.ecs.removeAllEntities();

		this.levelGenerator.createLevel1();
	}


	private int getNumPlayersInGame() {
		int count = 0;
		for (PlayerData player : players) {
			if (player.isInGame()) {
				count++;
			}
		}
		return count;
	}


	@Override
	public void draw() {
		Controller c = this.getNextNewController();
		if (c != null) {
			this.addPlayerForController(c);
		}
		c = this.getRemovedController();
		if (c != null) {
			this.removePlayerForController(c);
		}

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

		this.inputSystem.process();

		if (this.gameStage == 0) {
			// Loop through systems
			this.processPlayersSystem.process();
			this.moveToOffScreenSystem.process();
			this.playerMovementSystem.process();
			this.mobAiSystem.process();
			if (Settings.JOUST_MOVEMENT == false) {
				this.scrollPlayAreaSystem.process();
			}
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
		
		for (int i=0 ; i<this.log.size() ; i++) {
			super.drawFont(this.log.get(i), 20, 200-(i*20));
		}

	}


	private void addPlayerForController(Controller controller) {
		PlayerData data = new PlayerData(new ControllerInput(controller));
		this.players.add(data);
		this.addLogEntry("Player created for controller " + controller.getName());
	}


	private void removePlayerForController(Controller controller) {
		for (PlayerData p : this.players) {
			if (p.controller instanceof ControllerInput) {
				ControllerInput ci = (ControllerInput)p.controller;
				if (ci.controller == controller) {
					this.players.remove(p);
					this.addLogEntry("Player removed for controller " + controller.getName());
					break;
				}
			}
		}
	}


	public void addLogEntry(String s) {
		this.log.add(s);
		while (this.log.size() > 5) {
			this.log.remove(0);
		}
	}

	
	// ----------------------------------------------

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JoustMain();
            }
        });
    }
    

}
