package com.axolotlmaid.optionsprofiles.profiles;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProfileConfiguration {
    private static final Path profilesDirectory = Profiles.PROFILES_DIRECTORY;

    private boolean keybindingsOnly = false;

    public ProfileConfiguration save(String profileName) {
        ProfileConfiguration configuration = new ProfileConfiguration();

        Path profile = profilesDirectory.resolve(profileName);
        Path configurationFile = profile.resolve("configuration.json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (BufferedWriter writer = Files.newBufferedWriter(configurationFile)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("Unable to write configuration.json to profile!", e);
        }

        return configuration;
    }

    public static ProfileConfiguration get(String profileName) {
        ProfileConfiguration configuration = new ProfileConfiguration();

        Path profile = profilesDirectory.resolve(profileName);
        Path configurationFile = profile.resolve("configuration.json");

        try (BufferedReader reader = Files.newBufferedReader(configurationFile)) {
            Gson gson = new Gson();
            configuration = gson.fromJson(reader, ProfileConfiguration.class);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '{}']: An error occurred when reading configuration.json", profileName, e);
        }

        return configuration;
    }

    public boolean isKeybindingsOnly() {
        return keybindingsOnly;
    }

    public void setKeybindingsOnly(boolean keybindingsOnly) {
        this.keybindingsOnly = keybindingsOnly;
    }
}
