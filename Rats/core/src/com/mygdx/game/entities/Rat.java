package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.collision.ColliderRectangle;

public class Rat extends Sprite {

	/** movement velocity **/
	private Vector2 velocity;

	private Vector2 position;
	private Vector2 tempPosition;
	private Vector2 newPos;
	/**
	 * the gender of the rat 0 = baby 1 = male 2 = female
	 * **/
	private int gender;

	private boolean wait;
	private float waitTimer;
	private int moveCounter;

	private boolean birthWait;

	private float inkubtimer;
	private boolean grownUp;
	private ColliderRectangle collider;
	private Sprite sprite;
	private boolean ratSex;

	private float sexWaitTimer;

	private float birthWaitTimer;

	public Rat(Sprite sprite, Vector2 position, int gender) {

		super(new Sprite(new Texture("sprites/child.png")));

		this.setGrownUp(false);
		this.setBirthWait(false);
		this.sprite = sprite;
		this.setWait(false);
		this.setMoveCounter(0);
		this.setPosition(position);
		this.setTempPosition(position.cpy());
		this.setVelocity(new Vector2(0, 0));
		this.setRatSex(false);
		this.waitTimer = 0;
		this.inkubtimer = 5;
		this.setGender(gender);

		this.collider = new ColliderRectangle(position.x, position.y, 16, 16);

	}

	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());

		super.draw(spriteBatch);
	}

	public void update(float deltaTime) {

		if (inkubtimer != 0) {
			inkubtimer -= deltaTime;
			if (inkubtimer <= 0 && velocity.isZero()) {
				inkubtimer = 0;
				setGrownUp(true);
				super.set(sprite);
			}
		}
		// einfaches warten
		if (wait) {
			if (!ratSex) {
				waitTimer += deltaTime;
				if (waitTimer >= 0.5f) {
					wait = false;
					waitTimer = 0;
				}
			}
			// warten wärend des Sex
			else if (!isBirthWait() && ratSex && isGrownUp()) {
				sexWaitTimer += deltaTime;
				if (sexWaitTimer >= 5f) {
					wait = false;
					sexWaitTimer = 0;
					ratSex = false;
					if (gender == 2) {
						setBirthWait(true);
					}
				}
			}
		}
		// System.out.println(birthWait);
		// pause nach dem sex
		if (isBirthWait()) {
			birthWaitTimer += deltaTime;
			if (birthWaitTimer >= 20f) {
				setBirthWait(false);
				birthWaitTimer = 0;
			}
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getNewPos() {
		return newPos;
	}

	public void setNewPos(Vector2 newPos) {
		this.newPos = newPos;
	}

	public void setWait(boolean wait) {

		this.wait = wait;

	}

	public boolean isWait() {
		return wait;
	}

	public int getMoveCounter() {
		return moveCounter;
	}

	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	public ColliderRectangle getCollider() {

		return collider;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public boolean isRatSex() {
		return ratSex;
	}

	public void setRatSex(boolean ratSex) {
		this.ratSex = ratSex;
	}

	public boolean isGrownUp() {
		return grownUp;
	}

	public void setGrownUp(boolean grownUp) {
		this.grownUp = grownUp;
	}

	public boolean isBirthWait() {
		return birthWait;
	}

	public void setBirthWait(boolean birthWait) {
		this.birthWait = birthWait;
	}

	public Vector2 getTempPosition() {
		return tempPosition;
	}

	public void setTempPosition(Vector2 tempPosition) {
		this.tempPosition = tempPosition;
	}
}
