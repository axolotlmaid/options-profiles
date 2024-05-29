package com.axolotlmaid.optionsprofiles.profiles;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Profiles {
    private static final Path PROFILES_DIRECTORY = Paths.get("options-profiles");
    private static final Path OPTIONS_FILE = Paths.get("options.txt");
    private static final Path OPTIFINE_OPTIONS_FILE = Paths.get("optionsof.txt");
    private static final Path SODIUM_OPTIONS_FILE = Paths.get("config/sodium-options.json");
    private static final Path SODIUM_EXTRA_OPTIONS_FILE = Paths.get("config/sodium-extra-options.json");

    public static void createProfile() {
        String profileName = "Profile 1";
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        for (int i = 1; Files.exists(profile); i++) {
            profileName = "Profile " + i;
            profile = Paths.get(PROFILES_DIRECTORY.toString(), profileName);
        }

        try {
            Files.createDirectory(profile);

            if (Files.exists(profile)) {
                OptionsProfilesMod.LOGGER.info("[Profile '" + profileName + "']: created");
                writeProfile(profileName);
            } else {
                OptionsProfilesMod.LOGGER.warn("[Profile '" + profileName + "']: Profile already exists?");
            }
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '" + profileName + "']: An error occurred when creating a profile", e);
        }
    }

    private static void copyOptionFile(Path profile, Path options) {
        if (Files.exists(options)) {
            Path profileOptions = profile.resolve(options.getFileName());

            try {
                Files.copy(options, profileOptions);
            } catch (IOException e) {
                OptionsProfilesMod.LOGGER.error("[Profile '" + profile.getFileName().toString() + "']: Unable to copy '" + options.getFileName().toString() + "'", e);
            }
        }
    }

    public static void writeProfile(String profileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        try {
            FileUtils.cleanDirectory(profile.toFile());
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '" + profileName + "']: An error occurred when clearing old options files", e);
        }

        copyOptionFile(profile, OPTIONS_FILE);
        copyOptionFile(profile, OPTIFINE_OPTIONS_FILE);
        copyOptionFile(profile, SODIUM_OPTIONS_FILE);
        copyOptionFile(profile, SODIUM_EXTRA_OPTIONS_FILE);
    }

    private static boolean compareFileContent(Path file1, Path file2) throws IOException {
        return
                Files.exists(file1)
                && Files.exists(file2)
                && Files.readAllLines(file1).equals(Files.readAllLines(file2));
    }

    public static boolean isProfileLoaded(String profileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        try {
            return compareFileContent(OPTIONS_FILE, profile.resolve(OPTIONS_FILE))
                    || compareFileContent(OPTIFINE_OPTIONS_FILE, profile.resolve(OPTIFINE_OPTIONS_FILE))
                    || compareFileContent(SODIUM_OPTIONS_FILE, profile.resolve(SODIUM_OPTIONS_FILE))
                    || compareFileContent(SODIUM_EXTRA_OPTIONS_FILE, profile.resolve(SODIUM_EXTRA_OPTIONS_FILE));
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '" + profileName + "']: An error occurred when checking if the profile is loaded", e);
            return false;
        }
    }

    private static void loadOptionFile(String profileName, Path options) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);
        Path profileOptions = profile.resolve(options.getFileName());

        if (Files.exists(profileOptions)) {
            try {
                Files.copy(profileOptions, options, StandardCopyOption.REPLACE_EXISTING);
                OptionsProfilesMod.LOGGER.info("[Profile '" + profileName + "']: loaded");
            } catch (IOException e) {
                OptionsProfilesMod.LOGGER.error("[Profile '" + profileName + "']: An error occurred when loading the profile", e);
            }
        }
    }

    private static void loadOptionFile(String profileName, Path options, Consumer<Path> loader) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);
        Path profileOptions = profile.resolve(options.getFileName());

        if (Files.exists(profileOptions)) {
            loader.accept(profileOptions);
            OptionsProfilesMod.LOGGER.info("[Profile '" + profileName + "']: loaded");
        }
    }

    public static void loadProfile(String profileName) {
        loadOptionFile(profileName, OPTIONS_FILE);
        loadOptionFile(profileName, OPTIFINE_OPTIONS_FILE);
        loadOptionFile(profileName, SODIUM_OPTIONS_FILE, SodiumConfigLoader::load);
//        loadOptionFile(profileName, SODIUM_EXTRA_OPTIONS_FILE, SodiumExtraConfigLoader::load);
    }

    public static void renameProfile(String profileName, String newProfileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);
        Path newProfile = PROFILES_DIRECTORY.resolve(newProfileName);

        if (Files.exists(newProfile)) {
            OptionsProfilesMod.LOGGER.warn("[Profile '" + profileName + "']: A profile with that name already exists!");
            return;
        }

        try {
            Files.move(profile, newProfile);
            OptionsProfilesMod.LOGGER.info("[Profile '" + newProfileName + "']: renamed. Old name: " + profileName);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '" + profileName + "']: An error occurred when renaming the profile", e);
        }
    }

    public static void deleteProfile(String profileName) {
        Path profile = PROFILES_DIRECTORY.resolve(profileName);

        try (Stream<Path> files = Files.walk(profile)) {
            files
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);

            OptionsProfilesMod.LOGGER.info("[Profile '" + profileName + "']: deleted");
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("[Profile '" + profileName + "']: Profile was not deleted", e);
        }
    }
}
