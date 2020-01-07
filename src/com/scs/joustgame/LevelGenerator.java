package com.scs.joustgame;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.lang.NumberFunctions;

public class LevelGenerator {

	private EntityFactory entityFactory;
	private BasicECS ecs;

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		AbstractEntity background = this.entityFactory.createImage(Settings.GFX_FOLDER + "background.jpg", 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, -99);
		ecs.addEntity(background);

		// Ceiling
		this.createPlatform(0, 0, Settings.LOGICAL_WIDTH_PIXELS, false);

		//int row = Settings.LOGICAL_HEIGHT_PIXELS/2;
		for (int yPos=Settings.PLATFORM_SPACING ; yPos<Settings.MAX_PLATFORM_HEIGHT ; yPos += Settings.PLATFORM_SPACING) {
			this.generateRow(yPos, false);
		}

		// Floor
		this.createPlatform(0, Settings.LOGICAL_HEIGHT_PIXELS-5, Settings.LOGICAL_WIDTH_PIXELS, false);

	}


	public void generateRow(int rowYPos, boolean createMobs) {
		//generateRow_OneLongRow(row);

		int numPlatforms = NumberFunctions.rnd(1, 6);
		generateRow_MultiplePlatforms(rowYPos, numPlatforms, createMobs);
	}

	/*
	private void generateRow_OneLongRow(int row) {
		int inset = 100;
		int width = Settings.LOGICAL_WIDTH_PIXELS-(inset*2);

		AbstractEntity platform = this.entityFactory.createFluidPlatform(inset, row, width);
		ecs.addEntity(platform);
		AbstractEntity platformImage = this.entityFactory.createPlatformType1(inset, row, width, 35);
		ecs.addEntity(platformImage);

		AbstractEntity mob = this.entityFactory.createMob1(NumberFunctions.rnd(inset+20, inset+width-20), row);
		ecs.addEntity(mob);

		for (int col=inset ; col<inset+width ; col+=150) {
			AbstractEntity coin = this.entityFactory.createCoin(inset, row+5);
			ecs.addEntity(coin);
		}
	}
	 */

	private void generateRow_MultiplePlatforms(int yPos, int numPlatforms, boolean createMobs) {
		int numCreated = 0;
		for (int p=0 ; p<numPlatforms ; p++) {
			if (numPlatforms > 1) {
				if (numCreated > numPlatforms/2) {
					if (NumberFunctions.rnd(1, 3) == 1) {
						continue; // Miss some out
					}
				}
			}
			int width = Settings.LOGICAL_WIDTH_PIXELS / (numPlatforms*2);
			int startX = (width/2)+(p*width*2);
			this.createPlatform(startX, yPos, width, createMobs && numPlatforms <= 5);
			numCreated++;
		}
	}


	private void createPlatform(int startX, int yPos, int width, boolean createMob) {
		//AbstractEntity platform = this.entityFactory.createFluidPlatform(startX, row, width);
		//ecs.addEntity(platform);
		AbstractEntity platformImage = this.entityFactory.createPlatformType1(startX, yPos, width, Settings.LOGICAL_HEIGHT_PIXELS/20);
		ecs.addEntity(platformImage);

		if (createMob) {
			if (NumberFunctions.rnd(1, 3) == 1) {
				AbstractEntity mob = this.entityFactory.createMob_Cannonball(NumberFunctions.rnd(startX+20, startX+width-20), yPos);
				ecs.addEntity(mob);
			} else {
				AbstractEntity mob = this.entityFactory.createMob1(NumberFunctions.rnd(startX+20, startX+width-60), yPos);
				ecs.addEntity(mob);
			}
		}

		// Add coins
		for (int col=startX+15 ; col<startX+width-15 ; col+=50) {
			AbstractEntity coin = this.entityFactory.createCoin(col, yPos+5);
			ecs.addEntity(coin);
		}

	}

}
