package com.scs.joustgame;

import java.awt.Rectangle;

import com.scs.basicecs.AbstractEntity;
import com.scs.joustgame.ecs.components.AnimationCycleComponent;
import com.scs.joustgame.ecs.components.CanCollectComponent;
import com.scs.joustgame.ecs.components.CollectableComponent;
import com.scs.joustgame.ecs.components.CollisionComponent;
import com.scs.joustgame.ecs.components.HarmOnContactComponent;
import com.scs.joustgame.ecs.components.ImageComponent;
import com.scs.joustgame.ecs.components.JumpingComponent;
import com.scs.joustgame.ecs.components.KillByJumpingOnComponent;
import com.scs.joustgame.ecs.components.MobComponent;
import com.scs.joustgame.ecs.components.MoveOffScreenComponent;
import com.scs.joustgame.ecs.components.MovementComponent;
import com.scs.joustgame.ecs.components.PlayersAvatarComponent;
import com.scs.joustgame.ecs.components.PositionComponent;
import com.scs.joustgame.ecs.components.ScrollsAroundComponent;
import com.scs.joustgame.ecs.components.WalkingAnimationComponent;
import com.scs.joustgame.input.IPlayerInput;
import com.scs.joustgame.models.PlayerData;
import com.scs.lang.NumberFunctions;
import com.scs.simple2dgameframework.graphics.Ninepatch;
import com.scs.simple2dgameframework.graphics.Sprite;

public class EntityFactory {
	
	private JoustMain game;
	
	public EntityFactory(JoustMain _game) {
		game = _game;
	}
	

	public AbstractEntity createPlayersAvatar(PlayerData player, IPlayerInput controller, float x, float y) {
		AbstractEntity e = new AbstractEntity("Player");

		ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		PlayersAvatarComponent uic = new PlayersAvatarComponent(player);
		e.addComponent(uic);
		JumpingComponent jc = new JumpingComponent();
		e.addComponent(jc);
		KillByJumpingOnComponent kbj = new KillByJumpingOnComponent();
		e.addComponent(kbj);
		CanCollectComponent ccc = new CanCollectComponent();
		e.addComponent(ccc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.2f);
		e.addComponent(wac);
		game.animFrameHelper.createPlayersFrames(wac, player.imageId, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}

/*
	public AbstractEntity createWall(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Wall");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}
*/

	public AbstractEntity createHarmfulArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("HarmfulArea");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		HarmOnContactComponent hoc = new HarmOnContactComponent();
		e.addComponent(hoc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);
		return e;
	}

/*
	public AbstractEntity createTestImage(int x, int y, int w, int h, int zOrder) {
		AbstractEntity e = new AbstractEntity("TestImage");
		
		//Texture tex = new Texture("grey_box.png");
		Ninepatch np = new Ninepatch(game, null, null);
		Sprite sprite = np.getImage(w, h);
		sprite.setSize(w, h);

		ImageComponent imageData = new ImageComponent(sprite, zOrder);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);

		return e;
	}
*/

	public AbstractEntity createImage(String filename, int x, int y, float w, float h, int zOrder) {
		AbstractEntity e = new AbstractEntity("Image_" + filename);

		ImageComponent imageData = new ImageComponent(filename, zOrder, w, h);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, h);
		e.addComponent(pos);

		return e;
	}

/*
	public AbstractEntity createLadderArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Ladder");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, true);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}
*/

	public AbstractEntity createFluidPlatform(int x, int y, float w) {
		AbstractEntity e = new AbstractEntity("FluidPlatform");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 0, w, Settings.PLAYER_SIZE);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createPlatformType1(int x, int y, int w, int h) {
		AbstractEntity e = new AbstractEntity("PlatformImage1");

		Ninepatch ninepatch = new Ninepatch(game, Settings.GFX_FOLDER + "platform1.png", new Rectangle(1, 8, 1, 3));
		
		ImageComponent imageData = new ImageComponent(ninepatch.getImage(w, h), -1);
		e.addComponent(imageData);
		
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createEdge(int x1, int y1, float x2, float y2) {
		AbstractEntity e = new AbstractEntity("Edge");

		PositionComponent pos = PositionComponent.FromEdge(x1, y1, x2, y2);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, false, true, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createMob1(int x, int y) {
		AbstractEntity e = new AbstractEntity("Mob");

		ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, true, false, false); // Needs collideAsPlatform to be killed
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		MobComponent mob = new MobComponent(45, true);
		e.addComponent(mob);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.2f);
		e.addComponent(wac);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);

		game.animFrameHelper.createMob1Frames(e, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createMob_Cannonball(int x, int y) {
		AbstractEntity e = new AbstractEntity("Cannonball");

		ImageComponent imageData = new ImageComponent(Settings.GFX_FOLDER + "grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, true, false, false); // Needs collideAsPlatform to be killed
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		MobComponent mob = new MobComponent(65, false);
		e.addComponent(mob);
		//PreventsEndOfLevelComponent beolc = new PreventsEndOfLevelComponent();
		//e.addComponent(beolc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.1f);
		e.addComponent(wac);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);

		game.animFrameHelper.createCannonballFrames(e, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createFallingMob(AbstractEntity mob) {
		PositionComponent pos = (PositionComponent)mob.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)mob.getComponent(ImageComponent.class);	

		return this.createFallingGraphic(mob.toString(), pos.rect.x, pos.rect.y, img.sprite, pos.rect.width, pos.rect.height);
	}


	public AbstractEntity createFallingGraphic(String name, float x, float y, Sprite image, float w, float h) {
		AbstractEntity e = new AbstractEntity("Falling" + name);

		ImageComponent imageData = new ImageComponent(image, 1);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, h);
		e.addComponent(pos);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(0, 200);
		e.addComponent(moc);
		return e;
	}


	public AbstractEntity createCoin(int x, int y) {
		AbstractEntity e = new AbstractEntity("Coin");

		AnimationCycleComponent acc = game.animFrameHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);
		ImageComponent imageData = new ImageComponent(acc.frames[NumberFunctions.rnd(0, acc.frames.length-1)], 1);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, false, false);
		e.addComponent(cc);
		CollectableComponent col = new CollectableComponent(CollectableComponent.Type.Coin);
		e.addComponent(col);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);
		
		game.collectorSystem.coinsLeft++;
		return e;
	}


	public AbstractEntity createRisingCoin(AbstractEntity coin) {
		PositionComponent pos = (PositionComponent)coin.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)coin.getComponent(ImageComponent.class);
		
		AbstractEntity e = new AbstractEntity("RisingCoin");

		ImageComponent imageData;
		if (img.sprite != null) {
			imageData = new ImageComponent(img.sprite, 1);
		} else {
			imageData = new ImageComponent(img.imageFilename, 1, img.w, img.h);
		}
		e.addComponent(imageData);
		PositionComponent pos2 = PositionComponent.ByTopLeft(pos.rect.x, pos.rect.y, pos.rect.width, pos.rect.height);
		e.addComponent(pos2);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(160 * (NumberFunctions.rndBool() ? -1 : 1), -160);
		e.addComponent(moc);
		AnimationCycleComponent acc = game.animFrameHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);

		return e;
	}	


	public AbstractEntity createDeadPlayer(AbstractEntity player) {
		PositionComponent pos = (PositionComponent)player.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)player.getComponent(ImageComponent.class);
		
		AbstractEntity e = new AbstractEntity("DeadPlayer");

		ImageComponent imageData = new ImageComponent(img.sprite, 1);
		e.addComponent(imageData);
		PositionComponent pos2 = PositionComponent.ByTopLeft(pos.rect.x, pos.rect.y, pos.rect.width, pos.rect.height);
		e.addComponent(pos2);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(0, 200);
		e.addComponent(moc);

		return e;
	}	


}
