package dev.axolotlmaid.optionsprofiles.mixin;

import dev.axolotlmaid.optionsprofiles.gui.ProfilesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
	protected OptionsScreenMixin(Text title) {
		super(title);
	}

	@Inject(at = @At("TAIL"), method = "init")
	private void init(CallbackInfo info) {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		this.addDrawableChild(new ButtonWidget(5, 5, 100, 20, Text.translatable("gui.options-profiles.profiles-menu-text"), (button -> {
			minecraft.setScreen(new ProfilesScreen(this));
		})));
	}
}