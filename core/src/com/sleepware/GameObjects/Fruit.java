package com.sleepware.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.NumberUtils;

public class Fruit {

	private final Texture texture;
	private final TextureRegion image;
	
	private final String name;
	private final float colour;
	
	public Fruit (String name, float r, float g, float b, int w, int h) {
		
		texture=new Texture(Gdx.files.internal(name+".png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		image = new TextureRegion(texture, 0, 0, w, h);

		this.name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

		//this.name = name; //.toUpperCase();
		
		int intBits = (int)(255 * 1/*a*/) << 24 | (int)(255 * b) << 16 | (int)(255 * g) << 8 | (int)(255 * r);
		colour = NumberUtils.intToFloatColor(intBits);
	}

	public String getName() {
		return name;
	}

	public TextureRegion getImage() {
		return image;
	}
	
	
	public float getColour() {
		return colour;
	}
}
