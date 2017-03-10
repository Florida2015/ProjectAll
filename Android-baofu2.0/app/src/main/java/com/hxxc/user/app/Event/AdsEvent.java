package com.hxxc.user.app.Event;

public class AdsEvent {
	public static final int TODO_SHARE = 0;
	public static final int TODO_Reflush= 5;
	public static final int TODO_JSCallback = 10;

	public int todo = TODO_SHARE;

	public AdsEvent(int todo) {
		super();
		this.todo = todo;
	}
	
}
