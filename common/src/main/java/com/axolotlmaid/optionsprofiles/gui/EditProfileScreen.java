package com.axolotlmaid.optionsprofiles.gui;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class EditProfileScreen extends Screen {
    private final Screen lastScreen;
    private final Component profileName;

    private EditBox profileNameEdit;

    public EditProfileScreen(Screen screen, Component profileName) {
        super(new TextComponent(new TranslatableComponent("gui.optionsprofiles.editing-profile-title").getString() + profileName.getString()));
        this.lastScreen = screen;
        this.profileName = profileName;
    }

    protected void init() {
        this.profileNameEdit = new EditBox(
                this.font,
                this.width / 2 - 102,
                70,
                204,
                20,
                new TextComponent(Component.EMPTY.getString())
        );
        this.profileNameEdit.setValue(profileName.getString());
        this.addRenderableWidget(this.profileNameEdit);

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 75,
                        100,
                        150,
                        20,
                        new TranslatableComponent("gui.optionsprofiles.overwrite-options"),
                        (button) -> {
                            Profiles.writeProfile(profileName.getString(), true);
                            this.minecraft.setScreen(this.lastScreen);
                        },
                        (button, poseStack, i, j) -> EditProfileScreen.this.renderTooltip(poseStack, new TranslatableComponent("gui.optionsprofiles.overwrite-options.tooltip"), i, j)
                )
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 75,
                        121,
                        150,
                        20,
                        new TranslatableComponent("gui.optionsprofiles.rename-profile"),
                        (button) -> {
                            Profiles.renameProfile(profileName.getString(), this.profileNameEdit.getValue());
                            this.minecraft.setScreen(new EditProfileScreen(lastScreen, new TextComponent(this.profileNameEdit.getValue())));
                        }
                )
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 75,
                        142,
                        150,
                        20,
                        new TranslatableComponent("gui.optionsprofiles.options-toggle").append("..."),
                        (button) -> {
                            this.minecraft.setScreen(new OptionsToggleScreen(this, profileName));
                        },
                        (button, poseStack, i, j) -> EditProfileScreen.this.renderTooltip(poseStack, new TranslatableComponent("gui.optionsprofiles.options-toggle.tooltip"), i, j)
                )
        );

        this.addRenderableWidget(
                new Button(
                        10,
                        this.height - 29,
                        50,
                        20,
                        new TranslatableComponent("gui.optionsprofiles.delete-profile").withStyle(ChatFormatting.RED),
                        (button) -> {
                            Profiles.deleteProfile(profileName.getString());
                            this.minecraft.setScreen(this.lastScreen);
                        }
                )
        );

        this.addRenderableWidget(
                new Button(
                        this.width / 2 - 100,
                        this.height - 29,
                        200,
                        20,
                        CommonComponents.GUI_DONE,
                        (button) -> this.minecraft.setScreen(this.lastScreen)
            )
        );
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        this.profileNameEdit.render(poseStack, mouseX, mouseY, delta);
        GuiComponent.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        GuiComponent.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.optionsprofiles.profile-name-text"), this.width / 2, 50, 16777215);
        super.render(poseStack, mouseX, mouseY, delta);
    }
}