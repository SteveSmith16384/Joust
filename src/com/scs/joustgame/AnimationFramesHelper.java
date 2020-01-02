package com.scs.joustgame;

import com.scs.basicecs.AbstractEntity;
import com.scs.joustgame.ecs.components.WalkingAnimationComponent;
import com.scs.simple2dgameframework.Sprite;

public class AnimationFramesHelper {

	private JoustMain game;
	//private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public AnimationFramesHelper(JoustMain _game) {
		game = _game;
	}
	
/*
	public AnimationCycleComponent generateForCoin(float size) {		
		int numFrames = 8;
		AnimationCycleComponent acd = new AnimationCycleComponent(.1f);
		acd.frames = new Sprite[numFrames];

		/*for(int i=1 ; i<=8 ; i++) {
			Texture tex = getTexture("coin_0" + i + ".png");
			Sprite sprite = new Sprite(tex);
			sprite.setSize(size * (tex.getWidth()/45f), size);
			acd.frames[i-1] = sprite;
		}*/
		/*Texture texture = getTexture("Coin_16x16_Anim.png");
		TextureAtlas atlas = new TextureAtlas();
		for (int i=0 ; i<numFrames ; i++) {
			atlas.addRegion("frame"+i, texture, i*16, 0, 16, 16);
			acd.frames[i] = atlas.createSprite("frame"+i);
			acd.frames[i].setSize(size, size);
		}

		return acd;
	}
*/

	public void createPlayersFrames(AbstractEntity player, int num, float w, float h) {
		WalkingAnimationComponent wac = (WalkingAnimationComponent)player.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[3];
		wac.framesRight = new Sprite[3];
		wac.framesLeft[0] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right1.png");
		wac.framesLeft[0].flip(true,  false);
		wac.framesLeft[0].setSize(w, h);
		wac.framesLeft[1] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right2.png");
		wac.framesLeft[1].setSize(w, h);
		wac.framesLeft[1].flip(true,  false);
		wac.framesLeft[2] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right3.png");
		wac.framesLeft[2].setSize(w, h);
		wac.framesLeft[2].flip(true, false);

		wac.framesRight[0] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right1.png");
		wac.framesRight[0].setSize(w, h);
		wac.framesRight[1] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right2.png");
		wac.framesRight[1].setSize(w, h);
		wac.framesRight[2] = game.createSprite(Settings.GFX_FOLDER + "player" + num + "_right3.png");
		wac.framesRight[2].setSize(w, h);

		wac.idleFrame = wac.framesRight[0];

	}

/*
	public void createMob1Frames(AbstractEntity mob, float w, float h) {
		int NUM_FRAMES = 6;
		WalkingAnimationComponent wac = (WalkingAnimationComponent)mob.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[NUM_FRAMES];
		wac.framesRight = new Sprite[NUM_FRAMES];

		Texture texture = getTexture("mob1_frames.png");
		TextureAtlas atlas = new TextureAtlas();
		for (int i=0 ; i<NUM_FRAMES ; i++) {
			atlas.addRegion("frame"+i, texture, i*16, 0, 16, 13);
			wac.framesLeft[i] = atlas.createSprite("frame"+i);
			wac.framesLeft[i].setSize(w, h);
			wac.framesRight[i] = atlas.createSprite("frame"+i);
			wac.framesRight[i].setSize(w, h);
			wac.framesRight[i].flip(true, false);
		}
		wac.idleFrame = wac.framesLeft[0];
	}


	public void createCannonballFrames(AbstractEntity mob, float w, float h) {
		int NUM_FRAMES = 10;
		WalkingAnimationComponent wac = (WalkingAnimationComponent)mob.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[NUM_FRAMES];
		wac.framesRight = new Sprite[NUM_FRAMES];

		Texture texture = getTexture("cannonbobmouth.png");
		TextureAtlas atlas = new TextureAtlas();
		for (int i=0 ; i<NUM_FRAMES ; i++) {
			atlas.addRegion("frame"+i, texture, i*16, 0, 16, 16);
			wac.framesLeft[i] = atlas.createSprite("frame"+i);
			wac.framesLeft[i].setSize(w, h);
			wac.framesRight[i] = atlas.createSprite("frame"+i);
			wac.framesRight[i].setSize(w, h);
			wac.framesRight[i].flip(true, false);
		}
		wac.idleFrame = wac.framesLeft[0];
	}

*/

}
