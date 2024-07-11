package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.*;

public class EditProfileScreen extends Screen {
    private final Screen lastScreen;
    private final String profileName;

    private EditBox profileNameEdit;

    public EditProfileScreen(Screen screen, String profileName) {
        super(new TextComponent(new TranslatableComponent("gui.optionsprofiles.editing-profile-title").getString() + profileName));
        this.lastScreen = screen;
        this.profileName = profileName;
    }

    protected void init() {
        this.profileNameEdit = new EditBox(this.font, this.width / 2 - 102, this.height - 130, 204, 20, new TextComponent(Component.EMPTY.getString()));
        this.profileNameEdit.setValue(profileName);
        this.addRenderableWidget(this.profileNameEdit);

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 85, 100, 20, new TranslatableComponent("gui.optionsprofiles.overwrite-options"), (button) -> {
            Profiles.writeProfile(profileName, true);
            this.minecraft.setScreen(this.lastScreen);
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 65, 100, 20, new TranslatableComponent("gui.optionsprofiles.rename-profile"), (button) -> {
            Profiles.renameProfile(profileName, this.profileNameEdit.getValue());
            this.minecraft.setScreen(new EditProfileScreen(lastScreen, this.profileNameEdit.getValue()));
        }));

        this.addRenderableWidget(new Button(5, this.height - 25, 100, 20, new TranslatableComponent("gui.optionsprofiles.delete-profile").withStyle(ChatFormatting.RED), (button) -> {
            Profiles.deleteProfile(profileName);
            this.minecraft.setScreen(this.lastScreen);
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 40, 100, 20, CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }));
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
//        this.profileNameEdit.render(poseStack, mouseX, mouseY, delta);
        GuiComponent.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        GuiComponent.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.optionsprofiles.profile-name-text"), this.width / 2, this.height - 145, 16777215);
        super.render(poseStack, mouseX, mouseY, delta);
    }
}