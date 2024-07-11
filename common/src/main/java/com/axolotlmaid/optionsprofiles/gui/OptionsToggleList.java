package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.axolotlmaid.optionsprofiles.profiles.ProfileConfiguration;
import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OptionsToggleList extends ContainerObjectSelectionList<OptionsToggleList.Entry> {
    private final String profileName;
    private final ProfileConfiguration profileConfiguration;

    public OptionsToggleList(OptionsToggleScreen optionsToggleScreen, Minecraft minecraft, String profileName) {
        super(minecraft, optionsToggleScreen.width, optionsToggleScreen.height, 20, optionsToggleScreen.height - 32, 20);
        this.profileConfiguration = optionsToggleScreen.profileConfiguration;
        this.profileName = profileName;

        refreshEntries(false, false);
    }

    // If overriding boolean is set to true then this function will set every option in the list to overrideToggle (false or true)
    public void refreshEntries(boolean overriding, boolean overrideToggle) {
        this.clearEntries();

        Path profile = Profiles.PROFILES_DIRECTORY.resolve(profileName);
        Path optionsFile = profile.resolve("options.txt");

        if (overriding) {
            if (!overrideToggle) {          // If set to false, just set the list to nothing instead of removing them one by one.
                profileConfiguration.setOptionsToLoad(new ArrayList<>());
            }
        }

        try (Stream<String> lines = Files.lines(optionsFile)) {
            lines.forEach((line) -> {
                String[] option = line.split(":");

                if (option.length > 1) {                        // If the option value exists (e.g. "lastServer")
                    if (overrideToggle) {                       // We don't need to check for the overriding boolean since this should never be true while the overriding boolean is false.
                        List<String> optionsToLoad = profileConfiguration.getOptionsToLoad();
                        optionsToLoad.add(option[0]);           // Add every option because overrideToggle is set to true

                        profileConfiguration.setOptionsToLoad(optionsToLoad);       // Configuration is saved in the OptionsToggleScreen.java when the player presses "Done"
                    }

                    // Add entry with option key and value and if the key is in the profile configuration
                    this.addEntry(new OptionEntry(option[0], option[1], profileConfiguration.getOptionsToLoad().contains(option[0])));
                } else {
                    this.addEntry(new OptionEntry(option[0], "", profileConfiguration.getOptionsToLoad().contains(option[0])));
                }
            });
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when listing options", e);
        }
    }

    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 15;
    }

    public int getRowWidth() {
        return 340;
    }

    public class OptionEntry extends Entry {
        private final Component optionKey;
        private final CycleButton<Boolean> toggleButton;

        OptionEntry(String optionKey, String optionValue, boolean toggled) {
            this.optionKey = new TextComponent(optionKey);

            this.toggleButton = CycleButton.onOffBuilder(toggled).displayOnlyValue().create(0, 0, 44, 20, TextComponent.EMPTY, (button, boolean_) -> {
                List<String> optionsToLoad = profileConfiguration.getOptionsToLoad();

                // If toggled to true
                if (boolean_) {
                    button.setMessage(button.getMessage().copy().withStyle(ChatFormatting.GREEN));      // Set the button's color to green
                    optionsToLoad.add(optionKey);
                } else {
                    button.setMessage(button.getMessage().copy().withStyle(ChatFormatting.RED));        // Set the button's color to red
                    optionsToLoad.remove(optionKey);
                }

                profileConfiguration.setOptionsToLoad(optionsToLoad);
            });

            // Set tooltip to the option value (e.g. "ao" will show "true")
//            this.toggleButton.renderToolTip(Tooltip.create(Component.literal(optionValue)));

            if (toggled) {
                this.toggleButton.setMessage(this.toggleButton.getMessage().copy().withStyle(ChatFormatting.GREEN));    // Set the button's color to green
            } else {
                this.toggleButton.setMessage(this.toggleButton.getMessage().copy().withStyle(ChatFormatting.RED));      // Set the button's color to red
            }
        }

        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            Font fontRenderer = OptionsToggleList.this.minecraft.font;

            int posX = OptionsToggleList.this.getScrollbarPosition() - this.toggleButton.getWidth() - 10;
            int posY = y - 2;
            int textY = y + entryHeight / 2;

            GuiComponent.drawString(poseStack, fontRenderer, this.optionKey, x, textY - 9 / 2, 16777215);

            this.toggleButton.x = posX;
            this.toggleButton.y = posY;
            this.toggleButton.render(poseStack, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.toggleButton);
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.toggleButton);
        }
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<OptionsToggleList.Entry> {
        public Entry() {
        }
    }
}