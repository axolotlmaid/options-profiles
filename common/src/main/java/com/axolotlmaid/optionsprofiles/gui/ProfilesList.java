package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class ProfilesList extends ContainerObjectSelectionList<ProfilesList.Entry> {
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
                this.addEntry(new ProfilesList.ProfileEntry(Component.literal(profile.getFileName().toString())));
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

    public class ProfileEntry extends Entry {
        private final Component profileName;
        private final Button editButton;
        private final Button loadButton;

        ProfileEntry(final Component profileName) {
            this.profileName = profileName;

            this.editButton = new Button(0, 0, 75, 20, Component.translatable("gui.optionsprofiles.edit-profile"), (button) -> {
                ProfilesList.this.minecraft.setScreen(new EditProfileScreen(profilesScreen, profileName));
            });

            this.loadButton = new Button(0, 0, 75, 20, Component.translatable("gui.optionsprofiles.load-profile"), (button) -> {
                Profiles.loadProfile(profileName.getString());

                minecraft.options.load();
                minecraft.options.loadSelectedResourcePacks(minecraft.getResourcePackRepository());
                minecraft.reloadResourcePacks();

                minecraft.options.save();

                button.active = false;
            });

            this.loadButton.active = !Profiles.isProfileLoaded(profileName.getString());
        }

        public void render(PoseStack p_193923_, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            ProfilesList.this.minecraft.font.draw(p_193923_, this.profileName, p_193926_, (float)(p_193925_ + p_193928_ / 2 - 9 / 2), 16777215);

            this.loadButton.x = p_193926_ + 190;
            this.loadButton.y = p_193925_;
            this.loadButton.render(p_193923_, p_193929_, p_193930_, p_193932_);

            this.editButton.x = p_193926_ + 115;
            this.editButton.y = p_193925_;
            this.editButton.render(p_193923_, p_193929_, p_193930_, p_193932_);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }

        public boolean mouseClicked(double p_193919_, double p_193920_, int p_193921_) {
            if (this.editButton.mouseClicked(p_193919_, p_193920_, p_193921_)) {
                return true;
            } else {
                return this.loadButton.mouseClicked(p_193919_, p_193920_, p_193921_);
            }
        }

        public boolean mouseReleased(double p_193941_, double p_193942_, int p_193943_) {
            return this.editButton.mouseReleased(p_193941_, p_193942_, p_193943_) || this.loadButton.mouseReleased(p_193941_, p_193942_, p_193943_);
        }
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ProfilesList.Entry> {
        public Entry() {
        }
    }
}