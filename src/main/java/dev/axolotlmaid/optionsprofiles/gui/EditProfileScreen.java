package dev.axolotlmaid.optionsprofiles.gui;

import dev.axolotlmaid.optionsprofiles.Profiles;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class EditProfileScreen extends Screen {
    private final Screen parent;
    private final Text profileName;

    public EditProfileScreen(Screen parent, Text profileName) {
        super(Text.translatable("gui.options-profiles.edit-profiles-screen-text"));
        this.parent = parent;
        this.profileName = profileName;
    }

    @Override
    protected void init() {
        TextFieldWidget textfield = new TextFieldWidget(textRenderer, this.width / 2 - 102, this.height / 4 + 24, 204, 20, Text.translatable("profile-name-text-field"));
        this.addDrawableChild(textfield);

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 50, this.height / 4 + 50, 100, 20, Text.translatable("gui.options-profiles.update-profile-text"), (button) -> {
            new Profiles().writeOptionsFileToProfile(profileName.getString());
            this.client.setScreen(this.parent);
        }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 65, 200, 20, Text.translatable("gui.options-profiles.save-profile-text"), (button) -> {
            new Profiles().editProfile(profileName.getString(), textfield.getText());
            this.client.setScreen(this.parent);
        }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 40, 200, 20, ScreenTexts.CANCEL, (button) -> {
            this.client.setScreen(this.parent);
        }));

        this.addDrawableChild(new ButtonWidget(5, this.height - 25, 100, 20, Text.translatable("gui.options-profiles.delete-profile-text"), (button) -> {
            new Profiles().deleteProfile(profileName.getString());
            this.client.setScreen(this.parent);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, Text.of(this.title.getString() + profileName.getString()), this.width / 2  , 8, 16777215);
        drawCenteredText(matrices, this.textRenderer, "Profile Name", this.width / 2 - 70, this.height / 4 + 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
