package com.oxology;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.oxology.Runagate;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Runagate");
		config.setIdleFPS(30);
		Map<String, Object> values = new HashMap<>();


		File gameDataFolder = new File(System.getenv("APPDATA"), "Runagate");
		if(!gameDataFolder.exists()) gameDataFolder.mkdirs();

		File configFile = new File(gameDataFolder, "config.cfg");
		JSONObject configValues = new JSONObject();

		if(configFile.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(configFile);
				StringBuilder fileData = new StringBuilder();

				int next;
				while((next = inputStream.read()) != -1) {
					fileData.append((char) next);
				}
				inputStream.close();

				configValues = new JSONObject(fileData.toString());
			} catch(FileNotFoundException e) {

			} catch(JSONException e) {

			} catch(IOException e) {

			}
		} else {
			try {
				configValues.put("fps", Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
				configValues.put("fullscreen", true);
				configValues.put("width", Lwjgl3ApplicationConfiguration.getDisplayMode().width);
				configValues.put("height", Lwjgl3ApplicationConfiguration.getDisplayMode().height);

				FileOutputStream outputStream = new FileOutputStream(configFile);
				outputStream.write(configValues.toString(4).getBytes());
				outputStream.close();
			} catch(JSONException e) {
				System.err.println("There was an error processing json file");
			} catch(FileNotFoundException e) {
				System.err.println("Can't create config file");
			} catch(IOException e) {
				System.err.println("There was an error on saving config file");
			}
		}

		Iterator<String> keys = configValues.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			try {
				values.put(key, configValues.get(key));
			} catch(JSONException e) {
				System.out.println("There was an error processing json file");
            }
        }

		if((boolean) values.get("fullscreen")) config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		else config.setMaximized(true);

		System.out.println(values.values());

		new Lwjgl3Application(new Runagate(), config);
	}
}
