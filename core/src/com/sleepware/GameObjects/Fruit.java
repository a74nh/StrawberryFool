package com.sleepware.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fruit {

	private final Texture texture;
	private final TextureRegion image;
	
	private final String name;
	
	public Fruit (String name) {
		
		texture=new Texture(Gdx.files.internal(name+"small.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		image = new TextureRegion(texture, 0, 0, 32, 32);

		this.name = name.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public TextureRegion getImage() {
		return image;
	}
	
	
	
}
