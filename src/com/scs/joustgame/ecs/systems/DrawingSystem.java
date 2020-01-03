package com.scs.joustgame.ecs.systems;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.ecs.components.ImageComponent;
import com.scs.joustgame.ecs.components.PositionComponent;

public class DrawingSystem extends AbstractSystem implements Comparator<AbstractEntity> {

	private JoustMain game;

	public DrawingSystem(JoustMain _game, BasicECS ecs) {
		super(ecs);

		game  = _game;

	}


	@Override
	public Class<?> getComponentClass() {
		return ImageComponent.class;
	}


	@Override
	public void process() {
		Collections.sort(this.entities, this);
		Iterator<AbstractEntity> it = this.entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity);
		}
	}


	//@Override
	public void processEntity(AbstractEntity entity) {
		ImageComponent imageData = (ImageComponent)entity.getComponent(ImageComponent.class);
		PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class);
		if (imageData.sprite == null) {
			// Load sprite for given filename
			//JoustMain.p("Creating sprite for " + entity);
			imageData.sprite = game.createSprite(imageData.imageFilename);
			if (imageData.w > 0 && imageData.h > 0) {
				imageData.sprite.setSize(imageData.w, imageData.h);
			}
		}

		// Draw the sprite
		imageData.sprite.setPosition(posData.rect.x, posData.rect.y);
		imageData.sprite.drawSprite();
	}

	/*
	public Texture getTexture(String filename) {
		if (textures.containsKey(filename)) {
			return textures.get(filename);
		}
		MyGdxGame.p("Loading new tex: " + filename);
		Texture t = new Texture(filename);
		this.textures.put(filename, t);
		return t;
	}

	 */
	public void drawDebug() {

	}


	@Override
	public int compare(AbstractEntity arg0, AbstractEntity arg1) {
		ImageComponent im0 = (ImageComponent)arg0.getComponent(ImageComponent.class);
		ImageComponent im1 = (ImageComponent)arg1.getComponent(ImageComponent.class);
		return im0.zOrder - im1.zOrder;
	}

}

