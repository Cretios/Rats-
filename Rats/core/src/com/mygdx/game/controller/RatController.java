package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.mygdx.game.gameconfig;
import com.mygdx.game.entities.Rat;

public class RatController {

	public Rat testrat;
	private TiledMap map;
	private TiledMapTileLayer wayLayer;
	// wird ersetzt durch geschlechter map
	public IntMap<Rat> rats = new IntMap<Rat>();

	public IntMap<Rat> malerats = new IntMap<Rat>();
	public IntMap<Rat> femalerats = new IntMap<Rat>();

	private IntMap<Integer> movement = new IntMap<Integer>();
	private Vector2 moveVec = new Vector2();
	private float speedold = 15f;
	private float speednew = 20f;
	private Sprite female;
	private Sprite male;

	private Vector2 spawnvec = new Vector2(24, 17);

	// gender 1 = male 2 = female
	private int genderFemale;
	private int genderMale;

	private int ratCounter;
	/**
	 * direction the rat have to move 0 = wait, 1 = up, 2 = right, 3 = down, 4 =
	 * left
	 */
	private int direction;

	public RatController() {
		ratCounter = 0;

		genderFemale = 2;
		genderMale = 1;

		this.female = new Sprite(new Texture("sprites/female.png"));
		this.male = new Sprite(new Texture("sprites/male.png"));

		map = new TmxMapLoader().load("map/RatsMap1280x720.tmx");
		wayLayer = (TiledMapTileLayer) map.getLayers().get("Way");

		int gender = 1;
		for (MapObject o : map.getLayers().get("Spawns").getObjects()) {
			// for (MapObject o :
			// map.getLayers().get("TestSpawns").getObjects()) {
			RectangleMapObject obj = (RectangleMapObject) o;
			// System.out.println(o.getProperties().get("y"));
			// System.out.println(o.getProperties().get("x"));
			if (gender == 3) {
				gender = 1;
			}
			createRat(gender,
					new Vector2(obj.getRectangle().x, obj.getRectangle().y));
			gender++;
		}

	}

	public Vector2 tiledTransformFromTiledToLbgdx(Vector2 vec) {
		return vec.cpy().set(vec.x, (gameconfig.mapHeight1280x720 - vec.y));
	}

	public Vector2 tiledTransformFromTiledToPixel(Vector2 vec) {
		return vec.cpy().set(vec.x * gameconfig.tilesize,
				vec.y * gameconfig.tilesize);
	}

	public Vector2 tiledTransformFromPixelToTiled(Vector2 vec) {
		return vec.cpy().set(vec.x / gameconfig.tilesize,
				vec.y / gameconfig.tilesize);
	}

	public void updateRats(float deltaTime) {
		ratMovement(deltaTime);
		ratCollision();
	}

	private void ratCollision() {
		// female rats
		for (Entry<Rat> ratEntryFemale : femalerats) {
			Rat femaleRat = ratEntryFemale.value;
			if (femaleRat.isGrownUp()) {
				// male rats
				for (Entry<Rat> ratEntryMale : malerats) {
					Rat maleRat = ratEntryMale.value;
					if (maleRat.isGrownUp()) {
						if (femaleRat.getCollider().collision(
								maleRat.getCollider())
								&& !maleRat.isRatSex()
								&& !femaleRat.isRatSex()
								&& !femaleRat.isBirthWait()) {
							maleRat.setWait(true);
							femaleRat.setWait(true);
							maleRat.setRatSex(true);
							femaleRat.setRatSex(true);
							// int birth = 1;
							int birth = MathUtils.random(2, 5);
							for (int i = 1; i <= birth; i++) {
								Rat child = createRat(new Vector2(
										femaleRat.getPosition().x,
										femaleRat.getPosition().y));
								child.setVelocity(femaleRat.getVelocity());
							}
						}

					}
				}
			}
		}

	}

	// TODO Spawn orte noch anpassen
	public Rat createRat(Vector2 pos) {
		int gender = MathUtils.random(1, 2);
		Rat rat;
		if (gender == genderFemale) {
			rat = new Rat(female, pos, genderFemale);
			femalerats.put(ratCounter, rat);
		} else {
			rat = new Rat(male, pos, genderMale);
			malerats.put(ratCounter, rat);
		}

		rats.put(ratCounter, rat);

		ratCounter++;
		return rat;
	}

	/**
	 * Create Methode für angaben des geschlechts
	 * 
	 * @param gender
	 * @return
	 */
	public Rat createRat(int gender, Vector2 pos) {
		// int gender = MathUtils.random(1, 2);
		Rat rat;
		if (gender == genderFemale) {
			rat = new Rat(female, pos, genderFemale);
			femalerats.put(ratCounter, rat);
		} else {
			rat = new Rat(male, pos, genderMale);
			malerats.put(ratCounter, rat);
		}

		rats.put(ratCounter, rat);
		ratCounter++;
		return rat;
	}

	public void ratMovement(float deltaTime) {

		for (Entry<Rat> rat : rats) {
			if (rat.value.getVelocity().isZero()) {
				int counter = 0;
				direction = 0;
				Vector2 pos = tiledTransformFromPixelToTiled(rat.value
						.getPosition());
				MapProperties properties = wayLayer
						.getCell(Math.round(pos.x), Math.round(pos.y))
						.getTile().getProperties();
				if (properties.containsKey("up")) {
					// System.out.println("up");
					movement.put(counter, 1);
					counter++;
				}
				if (properties.containsKey("right")) {

					// System.out.println("right");
					movement.put(counter, 2);
					counter++;
				}
				if (properties.containsKey("down")) {
					// System.out.println("down");
					movement.put(counter, 3);
					counter++;
				}
				if (properties.containsKey("left")) {
					// System.out.println("left");
					movement.put(counter, 4);
					counter++;
				}
				movement.put(counter, 0);

				if (!rat.value.isWait()) {
					direction = movement.get((MathUtils
							.random(movement.size - 1)));
					// System.out.println("d " + direction);
					switch (direction) {

					case 0:
						rat.value.setRotation((MathUtils.random(0, 360)));
						rat.value.setWait(true);
						break;
					case 1:

						rat.value.setRotation(180);
						if (rat.value.isGrownUp()) {
							moveVec.y = speedold;
						} else {
							moveVec.y = speednew;
						}

						break;
					case 2:
						rat.value.setRotation(90);
						if (rat.value.isGrownUp()) {
							moveVec.x = speedold;
						} else {
							moveVec.x = speednew;
						}
						break;
					case 3:
						rat.value.setRotation(0);
						if (rat.value.isGrownUp()) {
							moveVec.y = (-speedold);
						} else {
							moveVec.y = (-speednew);
						}
						break;
					case 4:
						rat.value.setRotation(-90);
						if (rat.value.isGrownUp()) {
							moveVec.x = (-speedold);
						} else {
							moveVec.x = (-speednew);
						}
						break;
					}

				}

				// System.out.println(moveVec);
				rat.value.setVelocity(moveVec.cpy());
				rat.value.setMoveCounter(0);
				moveVec.setZero();
				movement.clear();
				// moveRat(rat.value, deltaTime);

			}
			if (!rat.value.isRatSex())
				moveRat(rat.value, deltaTime);
		}

	}

	private boolean moveRat(Rat rat, float deltaTime) {
		boolean answer = true;
		rat.getTempPosition().x = rat.getPosition().x + rat.getVelocity().x
				* deltaTime;
		rat.getTempPosition().y = rat.getPosition().y + rat.getVelocity().y
				* deltaTime;

		float x = Math.abs(rat.getTempPosition().x % 16 - rat.getPosition().x
				% 16);
		float y = Math.abs(rat.getTempPosition().y % 16 - rat.getPosition().y
				% 16);

		// System.out.println(rat.getVelocity().x);

		// counter um in das nächste Tile zu kommen
		rat.setMoveCounter(rat.getMoveCounter() + 1);

		if (x >= 1) {
			rat.getVelocity().x = 0;

		}
		if (y >= 1) {
			rat.getVelocity().y = 0;

		}

		answer = !rat.getVelocity().isZero();

		if (answer == true) {
			rat.getPosition().x = rat.getTempPosition().x;
			rat.getPosition().y = rat.getTempPosition().y;
		} else {
			if (rat.getMoveCounter() <= 1) {
				rat.getPosition().x = rat.getTempPosition().x;
				rat.getPosition().y = rat.getTempPosition().y;
			}
		}

		/*
		 * // System.out.println(answer); if (rat.getMoveCounter() == 0) {
		 * rat.getPosition().x = rat.getTempPosition().x; rat.getPosition().y =
		 * rat.getTempPosition().y; } else { if (rat.getTempPosition().x % 16 <=
		 * 0.4 || rat.getTempPosition().x % 16 >= 15.6) {
		 * 
		 * rat.getVelocity().x = 0; }
		 * 
		 * if ((rat.getTempPosition().y % 16) <= 0.4 || (rat.getTempPosition().y
		 * % 16) >= 15.6) { rat.getVelocity().y = 0; } answer =
		 * !rat.getVelocity().isZero();
		 * 
		 * if (answer == true) { rat.getPosition().x = rat.getTempPosition().x;
		 * rat.getPosition().y = rat.getTempPosition().y; } }
		 */
		rat.setX(rat.getPosition().x);
		rat.setY(rat.getPosition().y);

		rat.getCollider().setPosition(rat.getPosition().x, rat.getPosition().y);
		rat.getCollider().updateEdges();

		return answer;
	}
}
