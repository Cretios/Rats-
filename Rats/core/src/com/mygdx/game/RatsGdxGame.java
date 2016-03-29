package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Screens.Screens;
import com.mygdx.game.controller.RatController;

public class RatsGdxGame extends Game {

	private RatController ratController;

	@Override
	public void create() {
		ratController = new RatController();
		setScreen(new Screens(ratController));

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		ratController.updateRats(Gdx.graphics.getDeltaTime());
		if (ratController.rats.size != 0) {
			System.out.println(ratController.rats.size);

		}
		if (ratController.rats.size >= 100) {
			System.out.println("cleared");
			ratController.rats.clear();
		}
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
