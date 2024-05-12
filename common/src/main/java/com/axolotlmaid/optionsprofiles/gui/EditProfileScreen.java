package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class EditProfileScreen extends Screen {
    private final Screen lastScreen;
    private final Component profileName;

    private EditBox profileNameEdit;

    public EditProfileScreen(Screen screen, Component profileName) {
        super(Component.nullToEmpty(new TranslatableComponent("gui.optionsprofiles.editing-profile-title").getString() + profileName.getString()));
        this.lastScreen = screen;
        this.profileName = profileName;
    }

    protected void init() {
        this.profileNameEdit = new EditBox(this.font, this.width / 2 - 100, 116, 200, 20, new TranslatableComponent("gui.optionsprofiles.profile-name-text"));
        this.profileNameEdit.setValue(profileName.getString());
        this.addWidget(this.profileNameEdit);

        this.addButton(new Button(this.width / 2 - 50, 145, 100, 20, new TranslatableComponent("gui.optionsprofiles.overwrite-options"), (button -> {
            Profiles.writeOptionsFilesIntoProfile(profileName.getString());
            this.minecraft.setScreen(this.lastScreen);
        })));

        this.addButton(new Button(this.width / 2 - 50, 166, 100, 20, new TranslatableComponent("gui.optionsprofiles.rename-profile"), (button -> {
            Profiles.renameProfile(profileName.getString(), this.profileNameEdit.getValue());
            this.minecraft.setScreen(new EditProfileScreen(lastScreen, Component.nullToEmpty(this.profileNameEdit.getValue())));
        })));

        this.addButton(new Button(5, this.height - 25, 100, 20, new TranslatableComponent("gui.optionsprofiles.delete-profile").withStyle(ChatFormatting.RED), (button -> {
            Profiles.deleteProfile(profileName.getString());
            this.minecraft.setScreen(this.lastScreen);
        })));

        this.addButton(new Button(this.width / 2 - 75, this.height - 40, 150, 20, CommonComponents.GUI_DONE, (button -> {
            this.minecraft.setScreen(this.lastScreen);
        })));
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(poseStack);
        this.profileNameEdit.render(poseStack, mouseX, mouseY, delta);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.optionsprofiles.profile-name-text"), this.width / 2, 100, 16777215);
        super.render(poseStack, mouseX, mouseY, delta);
    }
}