package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.ProfileConfiguration;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class OptionsToggleScreen extends Screen {
    private final Screen lastScreen;
    private final Component profileName;
    private OptionsToggleList optionsToggleList;

    public ProfileConfiguration profileConfiguration;

    public OptionsToggleScreen(Screen lastScreen, Component profileName) {
        super(new TranslatableComponent("gui.optionsprofiles.options-toggle").append(": ").append(profileName));
        this.lastScreen = lastScreen;
        this.profileName = profileName;
        this.profileConfiguration = ProfileConfiguration.get(profileName.getString());
    }

    protected void init() {
        this.optionsToggleList = new OptionsToggleList(this, this.minecraft, profileName.getString());
        this.addWidget(this.optionsToggleList);

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 80,
                        this.height - 29,
                        75,
                        20,
                        new TranslatableComponent("gui.optionsprofiles.all-off").withStyle(ChatFormatting.RED),
                        (button) -> this.optionsToggleList.refreshEntries(true, false)
                )
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 155,
                        this.height - 29,
                        75,
                        20,
                        new TranslatableComponent("gui.optionsprofiles.all-on").withStyle(ChatFormatting.GREEN),
                        (button) -> this.optionsToggleList.refreshEntries(true, true)
                )
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 + 5,
                        this.height - 29,
                        150,
                        20,
                        CommonComponents.GUI_DONE,
                        (button) -> {
                            profileConfiguration.save();
                            this.minecraft.setScreen(this.lastScreen);
                        }
                )
        );
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.optionsToggleList.render(poseStack, mouseX, mouseY, delta);
        GuiComponent.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        super.render(poseStack, mouseX, mouseY, delta);
    }
}
