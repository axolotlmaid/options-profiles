package com.trafficlunar.optionsprofiles;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OptionsProfilesMod implements ModInitializer {
	public static final String MOD_ID = "optionsprofiles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Path profilesDirectory = Paths.get("options-profiles");

		if (Files.notExists(profilesDirectory)) {
			try {
				Files.createDirectory(profilesDirectory);
			} catch (IOException e) {
				LOGGER.error("An error occurred when creating the 'options-profiles' directory.", e);
			}
		}


	}
}