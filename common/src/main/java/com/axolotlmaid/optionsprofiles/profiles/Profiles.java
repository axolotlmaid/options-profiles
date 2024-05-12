package com.axolotlmaid.optionsprofiles.profiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Profiles {
    public static void createProfile() {
        String profileName = "Profile 1";
        Path profile = Paths.get("options-profiles/" + profileName);

        for (int i = 1; Files.exists(profile); i++) {
            profileName = "Profile " + i;
            profile = Paths.get("options-profiles/" + profileName);
        }

        try {
            Files.createDirectory(profile);

            if (Files.exists(profile)) {
                System.out.println("[Options Profiles] Profile created.");

                writeOptionsFilesIntoProfile(profileName);
            } else {
                System.out.println("[Options Profiles] Profile was not created successfully.");
            }
        } catch (IOException e) {
            System.out.println("[Options Profiles] An error occurred when creating a profile.");
            e.printStackTrace();
        }
    }

    private static void writeOptionFile(String profileName, String optionsFile, boolean isSodium) {
        Path profile = Paths.get("options-profiles/" + profileName);

        Path options;
        Path profileOptions = Paths.get(profile.toAbsolutePath() + "/" + optionsFile);

        if (isSodium) {
            options = Paths.get("config/" + optionsFile);
        } else {
            options = Paths.get(optionsFile);
        }

        try (Stream<String> paths = Files.lines(options)) {
            if (Files.exists(profileOptions))
                Files.newBufferedWriter(profileOptions, StandardOpenOption.TRUNCATE_EXISTING);

            paths.forEach(line -> {
                try {
                    Files.write(profileOptions, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    Files.write(profileOptions, "\n".getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("[Options Profiles] An error occurred when writing a profile.");
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("[Options Profiles] An error occurred when reading an options file.");
            e.printStackTrace();
        }
    }

    public static void writeOptionsFilesIntoProfile(String profileName) {
        // options.txt
        writeOptionFile(profileName, "options.txt", false);

        // sodium-options.json
        if (Files.exists(Paths.get("config/sodium-options.json"))) {
            writeOptionFile(profileName, "sodium-options.json", true);
        }

        // optionsof.txt
        if (Files.exists(Paths.get("optionsof.txt"))) {
            writeOptionFile(profileName, "optionsof.txt", false);
        }
    }

    public static boolean isProfileLoaded(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        Path options = Paths.get("options.txt");
        Path profileOptions = Paths.get(profile.toAbsolutePath() + "/options.txt");

        try {
            List<String> linesOptions = Files.readAllLines(options);
            List<String> linesProfileOptions = Files.readAllLines(profileOptions);

            // sodium-options.json
            Path sodiumConfiguration = Paths.get("config/sodium-options.json");
            Path sodiumConfigurationProfile = Paths.get(profile.toAbsolutePath() + "/sodium-options.json");

            if (Files.exists(sodiumConfigurationProfile)) {
                List<String> linesSodiumConfig = Files.readAllLines(sodiumConfiguration);
                List<String> linesSodiumConfigProfile = Files.readAllLines(sodiumConfigurationProfile);

                return linesOptions.equals(linesProfileOptions) && linesSodiumConfig.equals(linesSodiumConfigProfile);
            }

            // optionsof.txt
            Path optifineConfiguration = Paths.get("optionsof.txt");
            Path optifineConfigurationProfile = Paths.get(profile.toAbsolutePath() + "/optionsof.txt");

            if (Files.exists(optifineConfigurationProfile)) {
                List<String> linesOptifineConfig = Files.readAllLines(optifineConfiguration);
                List<String> linesOptifineConfigProfile = Files.readAllLines(optifineConfigurationProfile);

                return linesOptions.equals(linesProfileOptions) && linesOptifineConfig.equals(linesOptifineConfigProfile);
            }

            return linesOptions.equals(linesProfileOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void loadOptionFile(String profileName, String optionsFile) {
        Path profile = Paths.get("options-profiles/" + profileName);

        Path options = Paths.get(optionsFile);
        Path profileOptions = Paths.get(profile.toAbsolutePath() + "/" + optionsFile);

        try (Stream<String> paths = Files.lines(profileOptions)) {
            Files.newBufferedWriter(options, StandardOpenOption.TRUNCATE_EXISTING);

            paths.forEach(line -> {
                try {
                    Files.write(options, line.getBytes(), StandardOpenOption.APPEND);
                    Files.write(options, "\n".getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("[Options Profiles] An error occurred when loading a profile.");
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("[Options Profiles] An error occurred when loading a profile.");
            e.printStackTrace();
        }
    }

    public static void loadProfile(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        // options.txt
        loadOptionFile(profileName, "options.txt");

        // sodium-options.json
        Path sodiumConfigurationProfile = Paths.get(profile.toAbsolutePath() + "/sodium-options.json");

        if (Files.exists(Paths.get(profile.toAbsolutePath() + "/sodium-options.json")))
            SodiumConfigLoader.load(sodiumConfigurationProfile);

        // optifineof.txt
        if (Files.exists(Paths.get(profile.toAbsolutePath() + "/optionsof.txt")))
            loadOptionFile(profileName, "optifineof.txt");
    }

    public static void renameProfile(String profileName, String newProfileName) {
        Path profile = Paths.get("options-profiles/" + profileName);
        Path newProfile = Paths.get("options-profiles/" + newProfileName);

        if (Files.exists(newProfile))
            System.out.println("[Options Profiles] New profile already exists!");

        try {
            Files.move(profile, newProfile);

            if (Files.exists(newProfile)) {
                System.out.println("[Options Profiles] Profile renamed.");
            } else {
                System.out.println("[Options Profiles] Profile was not renamed successfully.");
            }
        } catch (IOException e) {
            System.out.println("[Options Profiles] Profile was not renamed successfully.");
            e.printStackTrace();
        }
    }

    public static void deleteProfile(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        try (Stream<Path> files = Files.walk(profile)) {
            files
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println("[Options Profiles] Profile was not deleted.");
            e.printStackTrace();
        }

        System.out.println("[Options Profiles] Profile deleted.");
    }
}