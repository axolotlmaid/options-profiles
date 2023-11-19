package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ProfilesScreen extends Screen {
    private final Screen lastScreen;
    private ProfilesList profilesList;

    public ProfilesScreen(Screen screen) {
        super(Component.translatable("gui.optionsprofiles.profiles-menu"));
        this.lastScreen = screen;
    }

    protected void init() {
        this.profilesList = new ProfilesList(this, this.minecraft);
        this.addWidget(this.profilesList);

        // buttons
        this.addRenderableWidget(Button.builder(Component.translatable("gui.optionsprofiles.save-current-options"), (button) -> {
            new Profiles().createProfile();
            this.profilesList.refreshEntries();
        }).size(150, 20).pos(this.width / 2 - 155, this.height - 29).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).size(150, 20).pos(this.width / 2 + 5, this.height - 29).build());
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.profilesList.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
    }
}