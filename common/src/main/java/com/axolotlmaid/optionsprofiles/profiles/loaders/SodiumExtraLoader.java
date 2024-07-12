package com.axolotlmaid.optionsprofiles.profiles.loaders;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptions;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.resources.ResourceLocation;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class SodiumExtraLoader {
    public static void load(Path file) {
        try (FileReader reader = new FileReader(file.toFile())) {
            Gson gson = new GsonBuilder().create();
            Configuration configuration = gson.fromJson(reader, Configuration.class);

            apply(configuration);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading Sodium's configuration", e);
        }
    }

    private static void apply(Configuration configuration) {
        SodiumExtraClientMod.options().animationSettings.animation = configuration.animation_settings.animation;
        SodiumExtraClientMod.options().animationSettings.water = configuration.animation_settings.water;
        SodiumExtraClientMod.options().animationSettings.lava = configuration.animation_settings.lava;
        SodiumExtraClientMod.options().animationSettings.fire = configuration.animation_settings.fire;
        SodiumExtraClientMod.options().animationSettings.portal = configuration.animation_settings.portal;
        SodiumExtraClientMod.options().animationSettings.blockAnimations = configuration.animation_settings.block_animations;
        SodiumExtraClientMod.options().animationSettings.sculkSensor = configuration.animation_settings.sculk_sensor;

        SodiumExtraClientMod.options().particleSettings.particles = configuration.particle_settings.particles;
        SodiumExtraClientMod.options().particleSettings.rainSplash = configuration.particle_settings.rain_splash;
        SodiumExtraClientMod.options().particleSettings.blockBreak = configuration.particle_settings.block_break;
        SodiumExtraClientMod.options().particleSettings.blockBreaking = configuration.particle_settings.block_breaking;
        SodiumExtraClientMod.options().particleSettings.otherMap = configuration.particle_settings.other;

        SodiumExtraClientMod.options().detailSettings.sky = configuration.detail_settings.sky;
        SodiumExtraClientMod.options().detailSettings.sunMoon = configuration.detail_settings.sun_moon;
        SodiumExtraClientMod.options().detailSettings.stars = configuration.detail_settings.stars;
        SodiumExtraClientMod.options().detailSettings.rainSnow = configuration.detail_settings.rain_snow;
        SodiumExtraClientMod.options().detailSettings.biomeColors = configuration.detail_settings.biome_colors;
        SodiumExtraClientMod.options().detailSettings.skyColors = configuration.detail_settings.sky_colors;

        SodiumExtraClientMod.options().renderSettings.fogDistance = configuration.render_settings.fog_distance;
        SodiumExtraClientMod.options().renderSettings.fogStart = configuration.render_settings.fog_start;
        SodiumExtraClientMod.options().renderSettings.multiDimensionFogControl = configuration.render_settings.multi_dimension_fog_control;
        SodiumExtraClientMod.options().renderSettings.dimensionFogDistanceMap = configuration.render_settings.dimensionFogDistance;
        SodiumExtraClientMod.options().renderSettings.useLinearFlatColorBlender = configuration.render_settings.use_linear_flat_color_blender;
        SodiumExtraClientMod.options().renderSettings.lightUpdates = configuration.render_settings.light_updates;
        SodiumExtraClientMod.options().renderSettings.itemFrame = configuration.render_settings.item_frame;
        SodiumExtraClientMod.options().renderSettings.armorStand = configuration.render_settings.armor_stand;
        SodiumExtraClientMod.options().renderSettings.painting = configuration.render_settings.painting;
        SodiumExtraClientMod.options().renderSettings.piston = configuration.render_settings.piston;
        SodiumExtraClientMod.options().renderSettings.beaconBeam = configuration.render_settings.beacon_beam;
        SodiumExtraClientMod.options().renderSettings.enchantingTableBook = configuration.render_settings.enchanting_table_book;
        SodiumExtraClientMod.options().renderSettings.itemFrameNameTag = configuration.render_settings.item_frame_name_tag;
        SodiumExtraClientMod.options().renderSettings.playerNameTag = configuration.render_settings.player_name_tag;

        SodiumExtraClientMod.options().extraSettings.overlayCorner = SodiumExtraGameOptions.OverlayCorner.valueOf(configuration.extra_settings.overlay_corner);
        SodiumExtraClientMod.options().extraSettings.textContrast = SodiumExtraGameOptions.TextContrast.valueOf(configuration.extra_settings.text_contrast);
        SodiumExtraClientMod.options().extraSettings.showFps = configuration.extra_settings.show_fps;
        SodiumExtraClientMod.options().extraSettings.showFPSExtended = configuration.extra_settings.show_f_p_s_extended;
        SodiumExtraClientMod.options().extraSettings.showCoords = configuration.extra_settings.show_coords;
        SodiumExtraClientMod.options().extraSettings.reduceResolutionOnMac = configuration.extra_settings.reduce_resolution_on_mac;
        SodiumExtraClientMod.options().extraSettings.useAdaptiveSync = configuration.extra_settings.use_adaptive_sync;
        SodiumExtraClientMod.options().extraSettings.cloudHeight = configuration.extra_settings.cloud_height;
        SodiumExtraClientMod.options().extraSettings.toasts = configuration.extra_settings.toasts;
        SodiumExtraClientMod.options().extraSettings.advancementToast = configuration.extra_settings.advancement_toast;
        SodiumExtraClientMod.options().extraSettings.recipeToast = configuration.extra_settings.recipe_toast;
        SodiumExtraClientMod.options().extraSettings.systemToast = configuration.extra_settings.system_toast;
        SodiumExtraClientMod.options().extraSettings.tutorialToast = configuration.extra_settings.tutorial_toast;
        SodiumExtraClientMod.options().extraSettings.instantSneak = configuration.extra_settings.instant_sneak;
        SodiumExtraClientMod.options().extraSettings.preventShaders = configuration.extra_settings.prevent_shaders;
        SodiumExtraClientMod.options().extraSettings.useFastRandom = configuration.extra_settings.use_fast_random;

        SodiumExtraClientMod.options().notificationSettings.hideRSORecommendation = configuration.notification_settings.hide_r_s_o_recommendation;

        SodiumExtraClientMod.options().superSecretSettings.fetchSodiumExtraCrowdinTranslations = configuration.super_secret_settings.fetch_sodium_extra_crowdin_translations;
        SodiumExtraClientMod.options().superSecretSettings.sodiumExtraCrowdinProjectIdentifier = configuration.super_secret_settings.sodium_extra_crowdin_project_identifier;
        SodiumExtraClientMod.options().superSecretSettings.fetchSodiumCrowdinTranslations = configuration.super_secret_settings.fetch_sodium_crowdin_translations;
        SodiumExtraClientMod.options().superSecretSettings.sodiumCrowdinProjectIdentifier = configuration.super_secret_settings.sodium_crowdin_project_identifier;

        SodiumExtraClientMod.options().writeChanges();
    }

    public static class Configuration {
        public AnimationSettings animation_settings;
        public ParticleSettings particle_settings;
        public DetailSettings detail_settings;
        public RenderSettings render_settings;
        public ExtraSettings extra_settings;
        public NotificationSettings notification_settings;
        public SuperSecretSettings super_secret_settings;

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
            public boolean use_linear_flat_color_blender;
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
            public boolean toasts;
            public boolean advancement_toast;
            public boolean recipe_toast;
            public boolean system_toast;
            public boolean tutorial_toast;
            public boolean instant_sneak;
            public boolean prevent_shaders;
            public boolean use_fast_random;
        }

        public static class NotificationSettings {
            public boolean hide_r_s_o_recommendation;
        }

        public static class SuperSecretSettings {
            public boolean fetch_sodium_extra_crowdin_translations;
            public String sodium_extra_crowdin_project_identifier;
            public boolean fetch_sodium_crowdin_translations;
            public String sodium_crowdin_project_identifier;
        }
    }
}
