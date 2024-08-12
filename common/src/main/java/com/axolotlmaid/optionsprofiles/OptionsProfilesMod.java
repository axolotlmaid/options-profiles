package com.axolotlmaid.optionsprofiles;

import com.axolotlmaid.optionsprofiles.gui.ProfilesList;
import com.axolotlmaid.optionsprofiles.profiles.ProfileConfiguration;
import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OptionsProfilesMod {
    public static final String MOD_ID = "optionsprofiles";
    public static final Logger LOGGER = LogManager.getLogger("Options Profiles");

    public static void init() {
        Path profilesDirectory = Paths.get("options-profiles");

        if (Files.notExists(profilesDirectory)) {
            try {
                Files.createDirectory(profilesDirectory);
            } catch (IOException e) {
                LOGGER.error("An error occurred when creating the 'options-profiles' directory.", e);
            }
        }

        // Add configuration for existing profiles that were made before v1.3
        Profiles.updateProfiles();

        // Loading profiles on startup
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Profiles.PROFILES_DIRECTORY)) {
            for (Path profile : directoryStream) {
                String profileName = profile.getFileName().toString();
                ProfileConfiguration profileConfiguration = ProfileConfiguration.get(profileName);

                if (profileConfiguration.isLoadOnStartup()) {
                    Profiles.loadProfile(profileName);
                }
            }
        } catch (Exception e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading startup profiles", e);
        }
    }
}
