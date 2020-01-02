package com.scs.joustgame;

public class Settings {

	public static final boolean RELEASE_MODE = false;
	public static final boolean QUICKSTART = true;
 
	public static final int MAX_MOVEMENT = 20;
	public static final float PLAYER_SPEED = 40f;
	public static final float JUMP_FORCE = 80f;	
	public static final float GRAVITY = 120f;

	public static final int PLAYER_SIZE = 20;
	public static final int COLLECTABLE_SIZE = 20;
	public static final int AVATAR_RESPAWN_TIME_SECS = 5;
	public static final int LOGICAL_WIDTH_PIXELS = 400;
	public static final int LOGICAL_HEIGHT_PIXELS = (int)(LOGICAL_WIDTH_PIXELS * .68);
	public static final int PHYSICAL_WIDTH_PIXELS = 800;
	public static final int PHYSICAL_HEIGHT_PIXELS = (int)(PHYSICAL_WIDTH_PIXELS * .68);
	public static final boolean TURN_OFF_GRAVITY = false;

	public static final int PLATFORM_SPACING = (int)(Settings.LOGICAL_HEIGHT_PIXELS * 0.20f);
	public static final int MAX_PLATFORM_HEIGHT = (int)(Settings.LOGICAL_HEIGHT_PIXELS * 0.9f); // Needs to be slightly lower so the coins don't disappear straight away!

	public static final String GFX_FOLDER = "gfx/";

	private Settings() {
	}

}
