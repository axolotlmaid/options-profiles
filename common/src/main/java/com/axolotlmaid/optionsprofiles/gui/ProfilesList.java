package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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
        super(minecraft, profilesScreen.width + 45, profilesScreen.height - 52, 20, 20);
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
            OptionsProfilesMod.LOGGER.error("An error occurred when listing profiles", e);
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

        ProfileEntry(Component profileName) {
            this.profileName = profileName;

            this.editButton = Button.builder(Component.translatable("gui.optionsprofiles.edit-profile"), (button) -> {
                minecraft.setScreen(new EditProfileScreen(profilesScreen, profileName));
            }).size(75, 20).createNarration((supplier) -> Component.translatable("gui.optionsprofiles.edit-profile")).build();

            this.loadButton = Button.builder(Component.translatable("gui.optionsprofiles.load-profile"), (button) -> {
                new Profiles().loadProfile(profileName.getString());

                minecraft.options.load();
                minecraft.options.loadSelectedResourcePacks(minecraft.getResourcePackRepository());
                minecraft.reloadResourcePacks();

                minecraft.options.save();

                button.active = false;
            }).size(75, 20).createNarration((supplier) -> Component.translatable("gui.optionsprofiles.load-profile")).build();

            this.loadButton.active = !new Profiles().isProfileLoaded(profileName.getString());
        }

        public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            Font fontRenderer = ProfilesList.this.minecraft.font;

            int textY = y + entryHeight / 2;

            Objects.requireNonNull(ProfilesList.this.minecraft.font);
            guiGraphics.drawString(fontRenderer, this.profileName, x - 50, textY - 9 / 2, 16777215, false);

            this.editButton.setX(x + 115);
            this.editButton.setY(y);
            this.editButton.render(guiGraphics, mouseX, mouseY, tickDelta);

            this.loadButton.setX(x + 190);
            this.loadButton.setY(y);
            this.loadButton.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editButton, this.loadButton);
        }
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ProfilesList.Entry> {
        public Entry() {
        }
    }
}