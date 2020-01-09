package com.scs.joustgame;

import java.awt.image.BufferedImage;

import com.scs.basicecs.AbstractEntity;
import com.scs.joustgame.ecs.components.AnimationCycleComponent;
import com.scs.joustgame.ecs.components.WalkingAnimationComponent;
import com.scs.simple2dgameframework.graphics.GraphicsUtils;
import com.scs.simple2dgameframework.graphics.Sprite;

public class AnimationFramesHelper {

	private JoustMain game;
	
	public AnimationFramesHelper(JoustMain _game) {
		game = _game;
	}
	

	public AnimationCycleComponent generateForCoin(float size) {		
		int numFrames = 8;
		AnimationCycleComponent acd = new AnimationCycleComponent(.1f);
		acd.frames = new Sprite[numFrames];

		BufferedImage img = game.graphicsUtils.loadImage(Settings.GFX_FOLDER + "Coin_16x16_Anim.png");
		
		for (int i=0 ; i<numFrames ; i++) {
			BufferedImage bi = GraphicsUtils.extractImage(img, i*16, 0, 16, 16, game.frame);
			acd.frames[i] = game.createSprite(bi);
		}

		return acd;
	}


	public void createPlayersFrames(WalkingAnimationComponent wac, int num, float w, float h) {
		//WalkingAnimationComponent wac = (WalkingAnimationComponent)player.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[3];
		wac.framesRight = new Sprite[3];
		wac.framesLeft[0] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right1.png");
		wac.framesLeft[0].flipLR();
		wac.framesLeft[0].setSize(w, h);
		wac.framesLeft[1] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right2.png");
		wac.framesLeft[1].setSize(w, h);
		wac.framesLeft[1].flipLR();
		wac.framesLeft[2] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right3.png");
		wac.framesLeft[2].setSize(w, h);
		wac.framesLeft[2].flipLR();

		wac.framesRight[0] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right1.png");
		wac.framesRight[0].setSize(w, h);
		wac.framesRight[1] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right2.png");
		wac.framesRight[1].setSize(w, h);
		wac.framesRight[2] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right3.png");
		wac.framesRight[2].setSize(w, h);

		wac.idleFrame = wac.framesRight[0];

	}


	public void createMob1Frames(AbstractEntity mob, float w, float h) {
		int NUM_FRAMES = 6;
		WalkingAnimationComponent wac = (WalkingAnimationComponent)mob.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[NUM_FRAMES];
		wac.framesRight = new Sprite[NUM_FRAMES];

		BufferedImage img = game.graphicsUtils.loadImage(Settings.GFX_FOLDER + "mob1_frames.png");
		for (int i=0 ; i<NUM_FRAMES ; i++) {
			BufferedImage bi = GraphicsUtils.extractImage(img, i*16, 0, 16, 13, game.frame);
			wac.framesLeft[i] = game.createSprite(bi);
			wac.framesLeft[i].setSize(w, h);
			wac.framesRight[i] = game.createSprite(bi);
			wac.framesRight[i].setSize(w, h);
			wac.framesRight[i].flipLR();
		}
		wac.idleFrame = wac.framesLeft[0];
	}


	public void createCannonballFrames(AbstractEntity mob, float w, float h) {
		int NUM_FRAMES = 10;
		WalkingAnimationComponent wac = (WalkingAnimationComponent)mob.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[NUM_FRAMES];
		wac.framesRight = new Sprite[NUM_FRAMES];

		BufferedImage img = game.graphicsUtils.loadImage(Settings.GFX_FOLDER + "cannonbobmouth.png");
		for (int i=0 ; i<NUM_FRAMES ; i++) {
			BufferedImage bi = GraphicsUtils.extractImage(img, i*16, 0, 16, 16, game.frame);
			wac.framesLeft[i] = game.createSprite(bi);
			wac.framesLeft[i].setSize(w, h);
			wac.framesRight[i] = game.createSprite(bi);
			wac.framesRight[i].setSize(w, h);
			wac.framesRight[i].flipLR();
		}
		wac.idleFrame = wac.framesLeft[0];
	}


}
