package com.hxxc.user.app.Event;

public class InvestEvent {

	public static final int TYPE_PAY = 1;
	public static final int TYPE_FINISH = 2;
	public int type = TYPE_PAY;

	public InvestEvent(int type) {
		super();
		this.type = type;
	}
}
