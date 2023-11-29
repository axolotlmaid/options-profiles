package com.axolotlmaid.optionsprofiles.gui;

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
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class ProfilesList extends ContainerObjectSelectionList<ProfilesList.ProfileEntry> {
    final ProfilesScreen profilesScreen;

    public ProfilesList(ProfilesScreen profilesScreen, Minecraft minecraft) {
        super(minecraft, profilesScreen.width + 45, profilesScreen.height, 20, profilesScreen.height - 32, 20);
        this.profilesScreen = profilesScreen;

        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();

        Path profilesDirectory = Paths.get("options-profiles/");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(profilesDirectory)) {
            for (Path profile : directoryStream) {
                this.addEntry(new ProfilesList.ProfileEntry(profile.getFileName().toString()));
            }
        } catch (Exception e) {
            System.out.println("An error occurred when listing profiles.");
            e.printStackTrace();
        }
    }

    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 15;
    }

    public int getRowWidth() {
        return super.getRowWidth() + 32;
    }

    public class ProfileEntry extends ContainerObjectSelectionList.Entry<ProfilesList.ProfileEntry> {
        private final String profileName;
        private final Button editButton;
        private final Button loadButton;

        ProfileEntry(String profileName) {
            this.profileName = profileName;

            this.editButton = new Button(0, 0, 75, 20, new TranslatableComponent("gui.optionsprofiles.edit-profile"), (button) -> {
                minecraft.setScreen(new EditProfileScreen(profilesScreen, profileName));
            });

            this.loadButton = new Button(0, 0, 75, 20, new TranslatableComponent("gui.optionsprofiles.load-profile"), (button) -> {
                Profiles.loadProfile(profileName);

                minecraft.options.load();
                minecraft.options.loadSelectedResourcePacks(minecraft.getResourcePackRepository());
                minecraft.reloadResourcePacks();

                minecraft.options.save();

                button.active = false;
            });

            this.loadButton.active = !Profiles.isProfileLoaded(profileName);
        }

        @Override
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            Font fontRenderer = ProfilesList.this.minecraft.font;

            int textY = y + entryHeight / 2;

            Objects.requireNonNull(ProfilesList.this.minecraft.font);
            GuiComponent.drawString(poseStack, fontRenderer, this.profileName, x - 50, textY - 9 / 2, 16777215);

            this.editButton.x = x + 115;
            this.editButton.y = y;
            this.editButton.render(poseStack, mouseX, mouseY, tickDelta);

            this.loadButton.x = x + 190;
            this.loadButton.y = y;
            this.loadButton.render(poseStack, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }
    }

}