package com.axolotlmaid.optionsprofiles.profiles;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptions;
import net.minecraft.resources.ResourceLocation;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class SodiumExtraConfigLoader {
    public static void load(Path file) {
        try (FileReader reader = new FileReader(file.toFile())) {
            Gson gson = new GsonBuilder().create();
            ConfigData configData = gson.fromJson(reader, ConfigData.class);

            apply(configData);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading Sodium Extra's configuration", e);
        }
    }

    private static void apply(ConfigData configData) {
        SodiumExtraClientMod.options().animationSettings.animation = configData.animationSettings.animation;
        SodiumExtraClientMod.options().animationSettings.water = configData.animationSettings.water;
        SodiumExtraClientMod.options().animationSettings.lava = configData.animationSettings.lava;
        SodiumExtraClientMod.options().animationSettings.fire = configData.animationSettings.fire;
        SodiumExtraClientMod.options().animationSettings.portal = configData.animationSettings.portal;
        SodiumExtraClientMod.options().animationSettings.blockAnimations = configData.animationSettings.block_animations;
        SodiumExtraClientMod.options().animationSettings.sculkSensor = configData.animationSettings.sculk_sensor;

        SodiumExtraClientMod.options().particleSettings.particles = configData.particleSettings.particles;
        SodiumExtraClientMod.options().particleSettings.rainSplash = configData.particleSettings.rain_splash;
        SodiumExtraClientMod.options().particleSettings.blockBreak = configData.particleSettings.block_break;
        SodiumExtraClientMod.options().particleSettings.blockBreaking = configData.particleSettings.block_breaking;
        SodiumExtraClientMod.options().particleSettings.otherMap = configData.particleSettings.other;

        SodiumExtraClientMod.options().detailSettings.sky = configData.detailSettings.sky;
        SodiumExtraClientMod.options().detailSettings.sunMoon = configData.detailSettings.sun_moon;
        SodiumExtraClientMod.options().detailSettings.stars = configData.detailSettings.stars;
        SodiumExtraClientMod.options().detailSettings.rainSnow = configData.detailSettings.rain_snow;
        SodiumExtraClientMod.options().detailSettings.biomeColors = configData.detailSettings.biome_colors;
        SodiumExtraClientMod.options().detailSettings.skyColors = configData.detailSettings.sky_colors;

        SodiumExtraClientMod.options().renderSettings.fogDistance = configData.renderSettings.fog_distance;
        SodiumExtraClientMod.options().renderSettings.fogStart = configData.renderSettings.fog_start;
        SodiumExtraClientMod.options().renderSettings.multiDimensionFogControl = configData.renderSettings.multi_dimension_fog_control;
        SodiumExtraClientMod.options().renderSettings.dimensionFogDistanceMap = configData.renderSettings.dimensionFogDistance;
        SodiumExtraClientMod.options().renderSettings.lightUpdates = configData.renderSettings.light_updates;
        SodiumExtraClientMod.options().renderSettings.itemFrame = configData.renderSettings.item_frame;
        SodiumExtraClientMod.options().renderSettings.armorStand = configData.renderSettings.armor_stand;
        SodiumExtraClientMod.options().renderSettings.painting = configData.renderSettings.painting;
        SodiumExtraClientMod.options().renderSettings.piston = configData.renderSettings.piston;
        SodiumExtraClientMod.options().renderSettings.beaconBeam = configData.renderSettings.beacon_beam;
        SodiumExtraClientMod.options().renderSettings.enchantingTableBook = configData.renderSettings.enchanting_table_book;
        SodiumExtraClientMod.options().renderSettings.itemFrameNameTag = configData.renderSettings.item_frame_name_tag;
        SodiumExtraClientMod.options().renderSettings.playerNameTag = configData.renderSettings.player_name_tag;

        SodiumExtraClientMod.options().extraSettings.overlayCorner = SodiumExtraGameOptions.OverlayCorner.valueOf(configData.extraSettings.overlay_corner);
        SodiumExtraClientMod.options().extraSettings.textContrast = SodiumExtraGameOptions.TextContrast.valueOf(configData.extraSettings.text_contrast);
        SodiumExtraClientMod.options().extraSettings.showFps = configData.extraSettings.show_fps;
        SodiumExtraClientMod.options().extraSettings.showFPSExtended = configData.extraSettings.show_f_p_s_extended;
        SodiumExtraClientMod.options().extraSettings.showCoords = configData.extraSettings.show_coords;
        SodiumExtraClientMod.options().extraSettings.reduceResolutionOnMac = configData.extraSettings.reduce_resolution_on_mac;
        SodiumExtraClientMod.options().extraSettings.useAdaptiveSync = configData.extraSettings.use_adaptive_sync;
        SodiumExtraClientMod.options().extraSettings.cloudHeight = configData.extraSettings.cloud_height;
        SodiumExtraClientMod.options().extraSettings.cloudDistance = configData.extraSettings.cloud_distance;
        SodiumExtraClientMod.options().extraSettings.toasts = configData.extraSettings.toasts;
        SodiumExtraClientMod.options().extraSettings.advancementToast = configData.extraSettings.advancement_toast;
        SodiumExtraClientMod.options().extraSettings.recipeToast = configData.extraSettings.recipe_toast;
        SodiumExtraClientMod.options().extraSettings.systemToast = configData.extraSettings.system_toast;
        SodiumExtraClientMod.options().extraSettings.tutorialToast = configData.extraSettings.tutorial_toast;
        SodiumExtraClientMod.options().extraSettings.instantSneak = configData.extraSettings.instant_sneak;
        SodiumExtraClientMod.options().extraSettings.preventShaders = configData.extraSettings.prevent_shaders;
        SodiumExtraClientMod.options().extraSettings.steadyDebugHud = configData.extraSettings.steady_debug_hud;
        SodiumExtraClientMod.options().extraSettings.steadyDebugHudRefreshInterval = configData.extraSettings.steady_debug_hud_refresh_interval;

        SodiumExtraClientMod.options().superSecretSettings.fetchSodiumExtraCrowdinTranslations = configData.superSecretSettings.fetch_sodium_extra_crowdin_translations;
        SodiumExtraClientMod.options().superSecretSettings.sodiumExtraCrowdinProjectIdentifier = configData.superSecretSettings.sodium_extra_crowdin_project_identifier;
        SodiumExtraClientMod.options().superSecretSettings.fetchSodiumCrowdinTranslations = configData.superSecretSettings.fetch_sodium_crowdin_translations;
        SodiumExtraClientMod.options().superSecretSettings.sodiumCrowdinProjectIdentifier = configData.superSecretSettings.sodium_crowdin_project_identifier;

        SodiumExtraClientMod.options().writeChanges();
    }

    public static class ConfigData {
        public AnimationSettings animationSettings;
        public ParticleSettings particleSettings;
        public DetailSettings detailSettings;
        public RenderSettings renderSettings;
        public ExtraSettings extraSettings;
        public SuperSecretSettings superSecretSettings;

        public static class AnimationSettings {
            public boolean animation;
            public boolean water;
            public boolean lava;
            public boolean fire;
            public boolean portal;
            public boolean block_animations;
            public boolean sculk_sensor;
        }

        public static class ParticleSettings {
            public boolean particles;
            public boolean rain_splash;
            public boolean block_break;
            public boolean block_breaking;
            public Map<ResourceLocation, Boolean> other;
        }

        public static class DetailSettings {
            public boolean sky;
            public boolean sun_moon;
            public boolean stars;
            public boolean rain_snow;
            public boolean biome_colors;
            public boolean sky_colors;
        }

        public static class RenderSettings {
            public int fog_distance;
            public int fog_start;
            public boolean multi_dimension_fog_control;
            public Map<ResourceLocation, Integer> dimensionFogDistance;
            public boolean light_updates;
            public boolean item_frame;
            public boolean armor_stand;
            public boolean painting;
            public boolean piston;
            public boolean beacon_beam;
            public boolean enchanting_table_book;
            public boolean item_frame_name_tag;
            public boolean player_name_tag;
        }

        public static class ExtraSettings {
            public String overlay_corner;
            public String text_contrast;
            public boolean show_fps;
            public boolean show_f_p_s_extended;
            public boolean show_coords;
            public boolean reduce_resolution_on_mac;
            public boolean use_adaptive_sync;
            public int cloud_height;
            public int cloud_distance;
            public boolean toasts;
            public boolean advancement_toast;
            public boolean recipe_toast;
            public boolean system_toast;
            public boolean tutorial_toast;
            public boolean instant_sneak;
            public boolean prevent_shaders;
            public boolean steady_debug_hud;
            public int steady_debug_hud_refresh_interval;
        }

        public static class SuperSecretSettings {
            public boolean fetch_sodium_extra_crowdin_translations;
            public String sodium_extra_crowdin_project_identifier;
            public boolean fetch_sodium_crowdin_translations;
            public String sodium_crowdin_project_identifier;
        }
    }
}
