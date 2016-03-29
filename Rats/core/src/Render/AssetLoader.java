package Render;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetLoader {

	public static TiledMap tiledMap;

	public static void load() {

		tiledMap = new TmxMapLoader().load("map/RatsMap.tmx");

	}

	// TextureAltas wird nciht mehr gebraucht und kann disposed werden
	public static void dispose() {

	}

}
