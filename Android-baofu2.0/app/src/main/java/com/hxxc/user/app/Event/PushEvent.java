package com.hxxc.user.app.Event;

public class PushEvent {

	public String page;
	public String data;
	public PushEvent(String page, String data) {
		super();
		this.page = page;
		this.data = data;
	}

	@Override
	public String toString() {
		return "PushEvent{" +
				"page='" + page + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
