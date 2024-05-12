package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class ProfilesScreen extends Screen {
    private final Screen lastScreen;
    private ProfilesList profilesList;

    public ProfilesScreen(Screen screen) {
        super(new TranslatableComponent("gui.optionsprofiles.profiles-menu"));
        this.lastScreen = screen;
    }

    protected void init() {
        this.profilesList = new ProfilesList(this, this.minecraft);
        this.addWidget(this.profilesList);

        // buttons
        this.addButton(new Button(this.width / 2 - 155, this.height - 29, 150, 20, new TranslatableComponent("gui.optionsprofiles.save-current-options"), (button -> {
            Profiles.createProfile();
            this.profilesList.refreshEntries();
        })));

        this.addButton(new Button(this.width / 2 + 5, this.height - 29, 150, 20, CommonComponents.GUI_DONE, (button -> {
            this.minecraft.setScreen(this.lastScreen);
        })));
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.profilesList.render(poseStack, mouseX, mouseY, delta);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        super.render(poseStack, mouseX, mouseY, delta);
    }
}