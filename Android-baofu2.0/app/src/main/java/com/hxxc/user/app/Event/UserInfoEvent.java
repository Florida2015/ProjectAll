package com.hxxc.user.app.Event;

public class UserInfoEvent {

	public String name;
	public String identitycard;
	public UserInfoEvent(String name, String card) {
		super();
		this.name = name;
		this.identitycard = card;
	}
	public UserInfoEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
