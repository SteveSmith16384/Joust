package com.scs.joustgame;

public class Settings {

	public static final boolean RELEASE_MODE = false;
	public static final boolean JOUST_MOVEMENT = true; // If false, it's a platformer
	public static final boolean QUICKSTART = !RELEASE_MODE && true;
	public static final boolean SHOW_GREY_BOXES = !RELEASE_MODE && false;
 
	public static final int MAX_MOVEMENT = 3;
	public static final float JOUST_PLAYER_SPEED = 5f;
	public static final float NORMAL_PLAYER_SPEED = 40f;
	public static final float JUMP_FORCE = 80f;	
	public static final float GRAVITY = 120f;

	public static final int PLAYER_SIZE = 20;
	public static final int COLLECTABLE_SIZE = 20;
	public static final int AVATAR_RESPAWN_TIME_SECS = 5;
	public static final int LOGICAL_WIDTH_PIXELS = 800; // May break if too small
	public static final int LOGICAL_HEIGHT_PIXELS = (int)(LOGICAL_WIDTH_PIXELS * 0.68);
	public static final int PHYSICAL_WIDTH_PIXELS = 800;
	public static final int PHYSICAL_HEIGHT_PIXELS = (int)(PHYSICAL_WIDTH_PIXELS * 0.68);
	public static final boolean TURN_OFF_GRAVITY = false;

	public static final int PLATFORM_SPACING = (int)(Settings.LOGICAL_HEIGHT_PIXELS * 0.20f);
	public static final int MAX_PLATFORM_HEIGHT = (int)(Settings.LOGICAL_HEIGHT_PIXELS * 0.9f); // Needs to be slightly lower so the coins don't disappear straight away!

	public static final String GFX_FOLDER = "gfx/";
	public static final String SFX_FOLDER = "sfx/";

	private Settings() {
	}

}
