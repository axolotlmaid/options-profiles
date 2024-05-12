package com.axolotlmaid.optionsprofiles.mixin;

import com.axolotlmaid.optionsprofiles.gui.ProfilesScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
        this.addButton(new Button(5, 5, 100, 20, new TranslatableComponent("gui.optionsprofiles.profiles-menu"), (button -> {
            this.minecraft.setScreen(new ProfilesScreen(this));
        })));
    }
}