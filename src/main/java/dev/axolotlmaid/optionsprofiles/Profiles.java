package dev.axolotlmaid.optionsprofiles;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Profiles {
    public File getDirectory() {
        File profilesDirectory = new File("options-profiles");
        if (!profilesDirectory.exists()) {
            profilesDirectory.mkdirs();
        }

        return profilesDirectory;
    }

    public void createProfile() {
        try {
            File profilesDirectory = getDirectory();
            String path = profilesDirectory.getAbsolutePath() + "/";

            String profileName = "Profile 1";
            File profile = new File(path + profileName + ".txt");

            for (int i = 1; profile.exists(); i++) {
                profileName = "Profile " + i;
                profile = new File(path + profileName + ".txt");
            }

            if (profile.createNewFile())
                System.out.println("Profile was created successfully.");

            writeOptionsFileToProfile(profileName);
        } catch (IOException e) {
            System.out.println("An error occurred when creating a profile.");
            e.printStackTrace();
        }
    }

    public void writeOptionsFileToProfile(String profileName) {
        try {
            File profilesDirectory = getDirectory();
            String path = profilesDirectory.getAbsolutePath() + "/";

            File options = new File("options.txt");
            File profile = new File(path + profileName + ".txt");

            Scanner reader = new Scanner(options);
            FileWriter writer = new FileWriter(profile);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                writer.write(data);
                writer.write("\n");
            }

            writer.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing a profile.");
            e.printStackTrace();
        }
    }

    public void editProfile(String profileName, String newProfileName) {
        File profilesDirectory = getDirectory();
        String path = profilesDirectory.getAbsolutePath() + "/";

        File profile = new File(path + profileName + ".txt");
        File newProfile = new File(path + newProfileName + ".txt");

        if (newProfile.exists())
            System.out.println("New profile already exists!");

        if (!profile.renameTo(newProfile))
            System.out.println("Profile was not renamed successfully.");
    }

    public void deleteProfile(String profileName) {
        File profilesDirectory = getDirectory();
        String path = profilesDirectory.getAbsolutePath() + "/";

        File profile = new File(path + profileName + ".txt");

        if (!profile.delete())
            System.out.println("Profile was not deleted successfully.");
    }

    public void overwriteOptionsFile(String profileName) {
        try {
            File profilesDirectory = getDirectory();
            String path = profilesDirectory.getAbsolutePath() + "/";

            File options = new File("options.txt");
            File profile = new File(path + profileName + ".txt");

            Scanner reader = new Scanner(profile);
            FileWriter writer = new FileWriter(options);
            new FileWriter(options, false).close();

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                writer.write(data);
                writer.write("\n");
            }

            writer.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred when creating a profile.");
            e.printStackTrace();
        }
    }
}
