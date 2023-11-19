package com.axolotlmaid.optionsprofiles.fabric;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import net.fabricmc.api.ModInitializer;

public class OptionsProfilesModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OptionsProfilesMod.init();
    }
}
