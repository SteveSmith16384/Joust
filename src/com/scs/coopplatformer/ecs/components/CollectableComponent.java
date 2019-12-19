package com.scs.coopplatformer.ecs.components;

public class CollectableComponent {
	
	public enum Type {Coin}
	
	public Type type;
	
	public CollectableComponent(Type _type) {
		type = _type;
	}
	

}
