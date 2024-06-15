package com.axolotlmaid.optionsprofiles.mixin;

import com.axolotlmaid.optionsprofiles.gui.ProfilesScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    protected MixinOptionsScreen(Component component) {
        super(component);
    }

    @Inject(at = @At("HEAD"), method = "init")
    private void init(CallbackInfo info) {
        this.addRenderableWidget(Button.builder(Component.translatable("gui.optionsprofiles.profiles-menu"), (button) -> {
            this.minecraft.setScreen(new ProfilesScreen(this));
        }).width(100).pos(5, 5).build());
    }
}