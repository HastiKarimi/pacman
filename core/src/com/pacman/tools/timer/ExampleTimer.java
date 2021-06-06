package com.pacman.tools.timer;

import com.pacman.model.Entity;

public class ExampleTimer extends Timer{
	public Entity entity;
	
	public ExampleTimer(Entity entity, long interval, long duration) {
		super(interval, duration);
		this.entity = entity;
	}
	
//	public ExampleTimer(long interval, long duration){
//		super(interval, duration);
//	}

	@Override
	protected void onTick() {
		System.out.println("bye");
	}

	@Override
	protected void onFinish() {
		entity.timerEnd();
		System.out.println("hello");
	}
	
}
