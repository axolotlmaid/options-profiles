package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class ProfilesScreen extends Screen {
    private final Screen parent;
    private ProfilesList profilesList;

    public ProfilesScreen(Screen parent) {
        super(Component.translatable("gui.optionsprofiles.profiles-menu"));
        this.parent = parent;
    }

    protected void init() {
        this.profilesList = new ProfilesList(this, this.minecraft);
        this.addWidget(this.profilesList);

        // buttons
        this.addRenderableWidget(new Button(this.width / 2 - 155, this.height - 29, 150, 20, Component.translatable("gui.optionsprofiles.save-current-options"), (button) -> {
            Profiles.createProfile();
            this.profilesList.refreshEntries();
        }));

        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - 29, 150, 20, CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(this.parent);
        }));
    }

    public void render(PoseStack p_96249_, int p_96250_, int p_96251_, float p_96252_) {
        this.renderBackground(p_96249_);
        this.profilesList.render(p_96249_, p_96250_, p_96251_, p_96252_);
        drawCenteredString(p_96249_, this.font, this.title, this.width / 2, 8, 16777215);
        super.render(p_96249_, p_96250_, p_96251_, p_96252_);
    }
}