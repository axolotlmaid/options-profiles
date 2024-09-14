package com.axolotlmaid.optionsprofiles.profiles;

import net.minecraft.network.chat.Component;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.api.ResourceDirectory;
import nl.enjarai.shared_resources.api.ResourceDirectoryBuilder;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;
import net.minecraft.resources.ResourceLocation;

public class SharedResourcesProfiles implements SharedResourcesEntrypoint {
    public static final ResourceDirectory SHARED_RESOURCES_PROFILES_DIRECTORY = new ResourceDirectoryBuilder("options-profiles")
            .setDisplayName(Component.translatable("gui.optionsprofiles"))
            .defaultEnabled(true)
            .isExperimental()
            .build();

    @Override
    public void registerResources(GameResourceRegistry registry) {
        registry.register(ResourceLocation.fromNamespaceAndPath("optionsprofiles", "profiles_directory"), SHARED_RESOURCES_PROFILES_DIRECTORY);
    }
}
