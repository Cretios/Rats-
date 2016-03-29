package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.mygdx.game.controller.RatController;
import com.mygdx.game.entities.Rat;

public class Screens implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private IntMap<Rat> rats;
	private Rat testrat;
	private RatController ratController;
	ShapeRenderer shapeRenderer;

	public Screens(RatController ratController) {
		this.ratController = ratController;
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// map = new TmxMapLoader().load("map/RatsMap.tmx");
		map = new TmxMapLoader().load("map/RatsMap1280x720.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		shapeRenderer = new ShapeRenderer();

		rats = ratController.rats;
	}

	public void ratRender(IntMap<Rat> rats) {
		for (Entry<Rat> rat : rats) {
			renderer.getBatch().begin();
			rat.value.draw(renderer.getBatch());
			renderer.getBatch().end();

			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			// rat.value.getTexture().
			shapeRenderer.rect(rat.value.getPosition().x,
					rat.value.getPosition().y, rat.value.getWidth(),
					rat.value.getHeight());
			// ColliderRectangle coll = rat.value.getCollider();
			//
			// shapeRenderer.line(coll.getBL(), coll.getBR());
			// shapeRenderer.line(coll.getBL(), coll.getUL());
			// shapeRenderer.line(coll.getBR(), coll.getUR());
			// shapeRenderer.line(coll.getUL(), coll.getUR());
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.end();
		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.setView(camera);
		renderer.render();

		ratRender(rats);

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.zoom = 0.7f;
		camera.update();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		disposeRats();
	}

	public void disposeRats() {
		for (Entry<Rat> rat : rats) {
			rat.value.getTexture().dispose();
		}
	}

}
