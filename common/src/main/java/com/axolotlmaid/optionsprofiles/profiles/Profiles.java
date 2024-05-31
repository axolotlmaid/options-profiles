package com.axolotlmaid.optionsprofiles.profiles;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Profiles {
    private static final Path PROFILES_DIRECTORY = Paths.get("options-profiles");
    private static final Path OPTIONS_FILE = Paths.get("options.txt");
    private static final Path OPTIFINE_OPTIONS_FILE = Paths.get("optionsof.txt");
    private static final Path SODIUM_OPTIONS_FILE = Paths.get("config/sodium-options.json");
    private static final Path SODIUM_EXTRA_OPTIONS_FILE = Paths.get("config/sodium-extra-options.json");

    public static void createProfile() {
        String profileName = "Profile 1";
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        // Increases the number in 'Profile 1' if it already exists
        for (int i = 1; Files.exists(profile); i++) {
            profileName = "Profile " + i;
            profile = Paths.get(PROFILES_DIRECTORY.toString(), profileName);
        }

        try {
            Files.createDirectory(profile);

            if (Files.exists(profile)) {
                OptionsProfilesMod.LOGGER.info("[Profile '{}']: created", profileName);
                writeProfile(profileName);
            } else {
                OptionsProfilesMod.LOGGER.warn("[Profile '{}']: Profile already exists?", profileName);
            }
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '{}']: An error occurred when creating a profile", profileName, e);
        }
    }

    private static void copyOptionFile(Path profile, Path options) {
        if (Files.exists(options)) {
            Path profileOptions = profile.resolve(options.getFileName());

            try {
                Files.copy(options, profileOptions);
            } catch (IOException e) {
                OptionsProfilesMod.LOGGER.error("[Profile '{}']: Unable to copy '{}'", profile.getFileName().toString(), options.getFileName().toString(), e);
            }
        }
    }

    public static void writeProfile(String profileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        try {
            // Removes old option files
            FileUtils.cleanDirectory(profile.toFile());
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '{}']: An error occurred when clearing old options files", profileName, e);
        }

        copyOptionFile(profile, OPTIONS_FILE);
        copyOptionFile(profile, OPTIFINE_OPTIONS_FILE);
        copyOptionFile(profile, SODIUM_OPTIONS_FILE);
        copyOptionFile(profile, SODIUM_EXTRA_OPTIONS_FILE);
    }

    public static boolean isProfileLoaded(String profileName) {
        boolean profileLoaded = false;

        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        List<Path> optionFiles = new ArrayList<>();
        optionFiles.add(OPTIONS_FILE);

        // The next few lines check if the specified file exists. If so, it adds it to the optionFiles ArrayList.
        Optional.of(OPTIFINE_OPTIONS_FILE).filter(Files::exists).ifPresent(optionFiles::add);
        Optional.of(SODIUM_OPTIONS_FILE).filter(Files::exists).ifPresent(optionFiles::add);
        Optional.of(SODIUM_EXTRA_OPTIONS_FILE).filter(Files::exists).ifPresent(optionFiles::add);

        // Check if the original option file and the profile option file have the same content
        try {
            for (Path optionFile : optionFiles) {
                Path profileOptions = profile.resolve(optionFile.getFileName());

                if (FileUtils.contentEquals(optionFile.toFile(), profileOptions.toFile())) {
                    profileLoaded = true;
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '{}']: An error occurred when checking if the profile is loaded", profileName, e);
            return false;
        }

        return profileLoaded;
    }

    private static void loadOptionFile(String profileName, Path options) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);
        Path profileOptions = profile.resolve(options.getFileName());

        if (Files.exists(profileOptions)) {
            try {
                // Replaces the original option file with the profile option file
                Files.copy(profileOptions, options, StandardCopyOption.REPLACE_EXISTING);
                OptionsProfilesMod.LOGGER.info("[Profile '{}']: '{}' loaded", profileName, options.getFileName());
            } catch (IOException e) {
                OptionsProfilesMod.LOGGER.error("[Profile '{}']: An error occurred when loading the profile", profileName, e);
            }
        }
    }

    private static void loadOptionFile(String profileName, Path options, Consumer<Path> loader) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);
        Path profileOptions = profile.resolve(options.getFileName());

        if (Files.exists(profileOptions)) {
            loader.accept(profileOptions);
            OptionsProfilesMod.LOGGER.info("[Profile '{}']: '{}' loaded", profileName, options.getFileName());
        }
    }

    public static void loadProfile(String profileName) {
        loadOptionFile(profileName, OPTIONS_FILE);
        loadOptionFile(profileName, OPTIFINE_OPTIONS_FILE);
        loadOptionFile(profileName, SODIUM_OPTIONS_FILE, SodiumConfigLoader::load);
        loadOptionFile(profileName, SODIUM_EXTRA_OPTIONS_FILE, SodiumExtraConfigLoader::load);
    }

    public static void renameProfile(String profileName, String newProfileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);
        Path newProfile = PROFILES_DIRECTORY.resolve(newProfileName);

        if (Files.exists(newProfile)) {
            OptionsProfilesMod.LOGGER.warn("[Profile '{}']: A profile with that name already exists!", profileName);
            return;
        }

        try {
            Files.move(profile, newProfile);
            OptionsProfilesMod.LOGGER.info("[Profile '{}']: renamed. Old name: {}", newProfileName, profileName);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '{}']: An error occurred when renaming the profile", profileName, e);
        }
    }

    public static void deleteProfile(String profileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        try {
            FileUtils.deleteDirectory(profile.toFile());
            OptionsProfilesMod.LOGGER.info("[Profile '{}']: deleted", profileName);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '{}']: Profile was not deleted", profileName, e);
        }
    }
}
