package com.scs.joustgame.input;

public interface IPlayerInput {

	void poll();
	
	boolean isLeftPressed();

	boolean isRightPressed();

	boolean isJumpPressed();

}
