package com.axolotlmaid.optionsprofiles.profiles;

import dev.architectury.platform.Platform;

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
    public void createProfile() {
        String profileName = "Profile 1";
        Path profile = Paths.get("options-profiles/" + profileName);

        for (int i = 1; Files.exists(profile); i++) {
            profileName = "Profile " + i;
            profile = Paths.get("options-profiles/" + profileName);
        }

        try {
            Files.createDirectory(profile);

            if (Files.exists(profile)) {
                System.out.println("Profile created.");

                writeOptionsFilesIntoProfile(profileName);
            } else {
                System.out.println("Profile was not created successfully.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred when creating a profile.");
            e.printStackTrace();
        }
    }

    public void writeOptionsFilesIntoProfile(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        // options.txt
        Path options = Paths.get("options.txt");
        Path profileOptions = Paths.get(profile.toAbsolutePath() + "/options.txt");

        try (Stream<String> paths = Files.lines(options)) {
            if (Files.exists(profileOptions))
                Files.newBufferedWriter(profileOptions, StandardOpenOption.TRUNCATE_EXISTING);

            paths.forEach(line -> {
                try {
                    Files.write(profileOptions, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    Files.write(profileOptions, "\n".getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("An error occurred when writing a profile.");
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("An error occurred when reading options.txt.");
            e.printStackTrace();
        }

        // sodium-options.json
        if (Platform.isFabric()) {
            if (Platform.isModLoaded("sodium")) {
                Path sodiumConfiguration = Paths.get("config/sodium-options.json");
                Path sodiumConfigurationProfile = Paths.get(profile.toAbsolutePath() + "/sodium-options.json");

                try (Stream<String> paths = Files.lines(sodiumConfiguration)) {
                    if (Files.exists(sodiumConfigurationProfile))
                        Files.newBufferedWriter(sodiumConfigurationProfile, StandardOpenOption.TRUNCATE_EXISTING);

                    paths.forEach(line -> {
                        try {
                            Files.write(sodiumConfigurationProfile, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                            Files.write(sodiumConfigurationProfile, "\n".getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            System.out.println("An error occurred when writing a profile.");
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    System.out.println("An error occurred when reading options.txt.");
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isProfileLoaded(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        Path options = Paths.get("options.txt");
        Path profileOptions = Paths.get(profile.toAbsolutePath() + "/options.txt");

        try {
            List<String> linesOptions = Files.readAllLines(options);
            List<String> linesProfileOptions = Files.readAllLines(profileOptions);

            if (Platform.isFabric()) {
                if (Platform.isModLoaded("sodium")) {
                    Path sodiumConfiguration = Paths.get("config/sodium-options.json");
                    Path sodiumConfigurationProfile = Paths.get(profile.toAbsolutePath() + "/sodium-options.json");

                    if (Files.exists(sodiumConfigurationProfile)) {
                        List<String> linesSodiumConfig = Files.readAllLines(sodiumConfiguration);
                        List<String> linesSodiumConfigProfile = Files.readAllLines(sodiumConfigurationProfile);

                        return linesOptions.equals(linesProfileOptions) && linesSodiumConfig.equals(linesSodiumConfigProfile);
                    }
                }
            }

            return linesOptions.equals(linesProfileOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void loadProfile(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        // options.txt
        Path options = Paths.get("options.txt");
        Path profileOptions = Paths.get(profile.toAbsolutePath() + "/options.txt");

        try (Stream<String> paths = Files.lines(profileOptions)) {
            Files.newBufferedWriter(options, StandardOpenOption.TRUNCATE_EXISTING);

            paths.forEach(line -> {
                try {
                    Files.write(options, line.getBytes(), StandardOpenOption.APPEND);
                    Files.write(options, "\n".getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("An error occurred when loading a profile.");
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("An error occurred when loading a profile.");
            e.printStackTrace();
        }

        // sodium-options.json
        if (Platform.isFabric()) {
            if (Platform.isModLoaded("sodium")) {
                Path sodiumConfigurationProfile = Paths.get(profile.toAbsolutePath() + "/sodium-options.json");

                if (Files.exists(sodiumConfigurationProfile)) {
                    SodiumConfigLoader.load(sodiumConfigurationProfile);
                }
            }
        }
    }

    public void renameProfile(String profileName, String newProfileName) {
        Path profile = Paths.get("options-profiles/" + profileName);
        Path newProfile = Paths.get("options-profiles/" + newProfileName);

        if (Files.exists(newProfile))
            System.out.println("New profile already exists!");

        try {
            Files.move(profile, newProfile);

            if (Files.exists(newProfile)) {
                System.out.println("Profile renamed.");
            } else {
                System.out.println("Profile was not renamed successfully.");
            }
        } catch (IOException e) {
            System.out.println("Profile was not renamed successfully.");
            e.printStackTrace();
        }
    }

    public void deleteProfile(String profileName) {
        Path profile = Paths.get("options-profiles/" + profileName);

        try (Stream<Path> files = Files.walk(profile)) {
            files
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println("Profile was not deleted.");
            e.printStackTrace();
        }

        System.out.println("Profile deleted.");
    }
}