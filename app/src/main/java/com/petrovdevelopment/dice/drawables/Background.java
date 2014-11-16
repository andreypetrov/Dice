package com.petrovdevelopment.dice.drawables;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

/**
 * Class representing the background (mostly just an image on the back of the canvas)
 * It needs to be actually rendered before any other element otherwise it will not be a background (its z index won't be correct)
 * It also wipes the previous frame from the surface
 * @author andrey
 * 
 */
public class Background {
	private BitmapDrawable mBitmapDrawable;
	//private Matrix mBitmapMatrix;
	public Background(Resources resources, int resourceId, int width, int height) {
		Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
		mBitmapDrawable = new BitmapDrawable(resources, bitmap);
		mBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
		mBitmapDrawable.setTileModeY(Shader.TileMode.REPEAT);
		mBitmapDrawable.setBounds(0, 0, width, height);
	}

	public void render(Canvas canvas) {
		mBitmapDrawable.draw(canvas);
	}
}
