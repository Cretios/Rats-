package com.mygdx.game.collision;

import com.badlogic.gdx.math.Vector2;

public class ColliderRectangle {

	private float centerx;

	public float getCenterx() {
		return centerx;
	}

	public float getCentery() {
		return centery;
	}

	private float centery;
	private int height;
	private int width;

	// die eckpunkte für die collision
	private Vector2 UL, UR, BL, BR, min, max;

	public ColliderRectangle(float x, float y, int height, int width) {
		centerx = x;
		centery = y;
		this.setHeight(height);
		this.setWidth(width);
		this.UL = new Vector2();
		this.UR = new Vector2();
		this.BL = new Vector2();
		this.BR = new Vector2();
		this.max = new Vector2();
		this.min = new Vector2();
		updateEdges();
	}

	public ColliderRectangle() {
	}

	/**
	 * Setzt die Eckpunkte fuer die Kollision. MUSS IMMER BEI POSITIONS
	 * VERAENDERUNGEN AUFGERUFEN WERDEN
	 */
	public void updateEdges() {
		UR.set(centerx + (getWidth() / 2), centery + (getHeight() / 2));
		// Max x und y für schnelles abrufen
		max.set(getUR());
		UL.set(centerx - (getWidth() / 2), centery + (getHeight() / 2));
		BR.set(centerx + (getWidth() / 2), centery - (getHeight() / 2));
		BL.set(centerx - (getWidth() / 2), centery - (getHeight() / 2));
		// Min x und min y für schnelles Abrufen
		min.set(getBL());
	}

	// setzt den mittelpunkt (für die bewegung)
	public void setPosition(float x, float y) {
		centerx = x;
		centery = y;
	}

	// Hier kommt die schnittbrechnung hin
	public boolean collision(ColliderRectangle r) {
		/*
		 * Gibt true aus wenn wenn man in der box eines anderen ist
		 */
		// System.out.println("x center " + (centerx));
		// System.out.println("y center " + centery);
		// System.out.println("max " + r.centerx);
		// System.out.println("x andere " + );

		return (centerx >= r.getMin().x && centerx <= r.getMax().x
				&& centery >= r.getMin().y && centery <= r.getMax().y);
		// return false;
	}

	public Vector2 getMin() {
		return min;
	}

	public void setMin(Vector2 min) {
		this.min = min;
	}

	public Vector2 getMax() {
		return max;
	}

	public void setMax(Vector2 max) {
		this.max = max;
	}

	public Vector2 getUL() {
		return UL;
	}

	public Vector2 getUR() {
		return UR;
	}

	public Vector2 getBL() {
		return BL;
	}

	public Vector2 getBR() {
		return BR;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
