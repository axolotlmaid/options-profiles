package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.ProfileConfiguration;
import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EditProfileScreen extends Screen {
    private final Screen lastScreen;
    private final Component profileName;

    private EditBox profileNameEdit;
    private Checkbox keybindingsOnlyCheckbox;

    public EditProfileScreen(Screen screen, Component profileName) {
        super(Component.literal(Component.translatable("gui.optionsprofiles.editing-profile-title").getString() + profileName.getString()));
        this.lastScreen = screen;
        this.profileName = profileName;
    }

    protected void init() {
        ProfileConfiguration profileConfiguration = ProfileConfiguration.get(profileName.getString());

        this.profileNameEdit = new EditBox(this.font, this.width / 2 - 102, 116, 204, 20, Component.empty());
        this.profileNameEdit.setValue(profileName.getString());
        this.addWidget(this.profileNameEdit);

        this.keybindingsOnlyCheckbox = Checkbox.builder(
                        Component.translatable("gui.optionsprofiles.keybindings-only"),
                        this.font)
                .pos(5, this.height - 45)
                .selected(profileConfiguration.isKeybindingsOnly())
                .build();
        this.addRenderableWidget(this.keybindingsOnlyCheckbox);

        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("gui.optionsprofiles.overwrite-options"),
                                (button) -> {
                                    Profiles.writeProfile(profileName.getString(), true);
                                    this.minecraft.setScreen(this.lastScreen);
                                })
                        .size(100, 20)
                        .pos(this.width / 2 - 50, 145)
                        .build());

        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("gui.optionsprofiles.rename-profile"),
                                (button) -> {
                                    Profiles.renameProfile(profileName.getString(), this.profileNameEdit.getValue());
                                    this.minecraft.setScreen(new EditProfileScreen(lastScreen, Component.literal(this.profileNameEdit.getValue())));
                                })
                        .size(100, 20)
                        .pos(this.width / 2 - 50, 166)
                        .build());

        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("gui.optionsprofiles.delete-profile")
                                        .withStyle(ChatFormatting.RED),
                                (button) -> {
                                    Profiles.deleteProfile(profileName.getString());
                                    this.minecraft.setScreen(this.lastScreen);
                                })
                        .size(100, 20)
                        .pos(5, this.height - 25)
                        .build());

        this.addRenderableWidget(
                Button.builder(
                                CommonComponents.GUI_DONE,
                                (button) -> {
                                    profileConfiguration.setKeybindingsOnly(keybindingsOnlyCheckbox.selected());
                                    profileConfiguration.save();
                                    this.minecraft.setScreen(this.lastScreen);
                                })
                        .size(100, 20)
                        .pos(this.width / 2 - 50, this.height - 40)
                        .build());
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.profileNameEdit.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
        guiGraphics.drawCenteredString(this.font, Component.translatable("gui.optionsprofiles.profile-name-text"), this.width / 2, 100, 16777215);
    }
}