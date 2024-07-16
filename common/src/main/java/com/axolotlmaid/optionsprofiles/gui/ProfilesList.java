package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.axolotlmaid.optionsprofiles.profiles.ProfileConfiguration;
import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProfilesList extends ContainerObjectSelectionList<ProfilesList.ProfileEntry> {
    final ProfilesScreen profilesScreen;

    public ProfilesList(ProfilesScreen profilesScreen, Minecraft minecraft) {
        super(minecraft, profilesScreen.width + 45, profilesScreen.height, 20, profilesScreen.height - 32, 20);
        this.profilesScreen = profilesScreen;

        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Profiles.PROFILES_DIRECTORY)) {
            List<Path> profileList = new ArrayList<>();
            for (Path profile : directoryStream) {
                profileList.add(profile);
            }

            // Sort the list alphabetically based on the profile names
            profileList.sort(Comparator.comparing(p -> p.getFileName().toString()));

            for (Path profile : profileList) {
                this.addEntry(new ProfilesList.ProfileEntry(new TextComponent(profile.getFileName().toString())));
            }
        } catch (Exception e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when listing profiles", e);
        }

        checkEntriesLoaded();
    }

    public void checkEntriesLoaded() {
        this.children().forEach(ProfileEntry::checkLoaded);
    }

    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 15;
    }

    public int getRowWidth() {
        return 340;
    }

    public class ProfileEntry extends ContainerObjectSelectionList.Entry<ProfilesList.ProfileEntry> {
        private final Component profileName;
        private final Button editButton;
        private final Button loadButton;

        ProfileEntry(Component profileName) {
            this.profileName = profileName;

            this.editButton = new Button(
                    0,
                    0,
                    75,
                    20,
                    new TranslatableComponent("gui.optionsprofiles.edit-profile"),
                    (button) -> {
                        minecraft.setScreen(new EditProfileScreen(profilesScreen, profileName));
                    }
            );

            this.loadButton = new Button(
                    0,
                    0,
                    75,
                    20,
                    new TranslatableComponent("gui.optionsprofiles.load-profile"),
                    (button) -> {
                        Profiles.loadProfile(profileName.getString());

                        minecraft.options.load();

                        if (ProfileConfiguration.get(profileName.getString()).getOptionsToLoad().contains("resourcePacks")) {
                            minecraft.options.loadSelectedResourcePacks(minecraft.getResourcePackRepository());
                            minecraft.reloadResourcePacks();
                        }

                        minecraft.options.save();

                        ProfilesList.this.checkEntriesLoaded();
                        button.active = false;
                    }
            );

            this.loadButton.active = !Profiles.isProfileLoaded(profileName.getString());
        }

        @Override
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            Font fontRenderer = ProfilesList.this.minecraft.font;

            int posX = ProfilesList.this.getScrollbarPosition() - this.loadButton.getWidth() - 10;
            int posY = y - 2;
            int textY = y + entryHeight / 2;

            GuiComponent.drawString(poseStack, fontRenderer, this.profileName, x, textY - 9 / 2, 16777215);

            this.editButton.x = posX - this.editButton.getWidth();
            this.editButton.y = posY;
            this.editButton.render(poseStack, mouseX, mouseY, tickDelta);

            this.loadButton.x = posX;
            this.loadButton.y = posY;
            this.loadButton.render(poseStack, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }

        protected void checkLoaded() {
            this.loadButton.active = !Profiles.isProfileLoaded(profileName.getString());
        }
    }

}