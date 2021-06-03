package com.pacman.tools.timer;

import com.pacman.model.Entity;

public class ExampleTimer extends Timer{
	public Entity entity;
	
	public ExampleTimer(Entity entity, long duration, long interval) {
		super(interval, duration);
		this.entity = entity;
	}
	
	public ExampleTimer(long interval, long duration){
		super(interval, duration);
	}

	@Override
	protected void onTick() {
		System.out.println("onTick called!");
	}

	@Override
	protected void onFinish() {
		System.out.println("onFinish called!");
	}
	
}
