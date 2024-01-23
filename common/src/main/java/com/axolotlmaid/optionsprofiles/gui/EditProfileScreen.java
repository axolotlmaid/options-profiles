package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EditProfileScreen extends Screen {
    private final Screen parent;
    private final Component profileName;

    private EditBox profileNameEditbox;

    public EditProfileScreen(Screen screen, Component profileName) {
        super(Component.literal(Component.translatable("gui.optionsprofiles.editing-profile-title").getString() + profileName.getString()));
        this.parent = screen;
        this.profileName = profileName;
    }

    protected void init() {
        this.profileNameEditbox = new EditBox(this.font, this.width / 2 - 102, this.height - 130, 204, 20, Component.empty());
        this.profileNameEditbox.setValue(profileName.getString());
        this.addWidget(this.profileNameEditbox);

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 85, 100, 20, Component.translatable("gui.optionsprofiles.overwrite-options"), (button) -> {
            Profiles.writeOptionsFilesIntoProfile(profileName.getString());
            this.minecraft.setScreen(this.parent);
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 65, 100, 20, Component.translatable("gui.optionsprofiles.rename-profile"), (button) -> {
            Profiles.renameProfile(profileName.getString(), this.profileNameEditbox.getValue());
            this.minecraft.setScreen(new EditProfileScreen(parent, Component.literal(this.profileNameEditbox.getValue())));
        }));

        this.addRenderableWidget(new Button(5, this.height - 25, 100, 20, Component.translatable("gui.optionsprofiles.delete-profile").withStyle(ChatFormatting.RED), (button) -> {
            Profiles.deleteProfile(profileName.getString());
            this.minecraft.setScreen(this.parent);
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 40, 100, 20, CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen(this.parent);
        }));
    }

    public void render(PoseStack p_96249_, int p_96250_, int p_96251_, float delta) {
        this.renderBackground(p_96249_);
        this.profileNameEditbox.render(p_96249_, p_96250_, p_96251_, delta);
        drawCenteredString(p_96249_, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(p_96249_, this.font, Component.translatable("gui.optionsprofiles.profile-name-text"), this.width / 2, this.height - 145, 16777215);
        super.render(p_96249_, p_96250_, p_96251_, delta);
    }
}