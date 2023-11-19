package com.axolotlmaid.optionsprofiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OptionsProfilesMod {
    public static final String MOD_ID = "optionsprofiles";

    public static void init() {
        Path profilesDirectory = Paths.get("options-profiles");

        if (Files.notExists(profilesDirectory)) {
            try {
                Files.createDirectory(profilesDirectory);
            } catch (IOException e) {
                System.out.println("An error occurred when creating the 'options-profiles' directory.");
                e.printStackTrace();
            }
        }
    }
}
