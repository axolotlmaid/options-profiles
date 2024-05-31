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
        SodiumExtraClientMod.options().animationSettings.animation = configData.animation_settings.animation;
        SodiumExtraClientMod.options().animationSettings.water = configData.animation_settings.water;
        SodiumExtraClientMod.options().animationSettings.lava = configData.animation_settings.lava;
        SodiumExtraClientMod.options().animationSettings.fire = configData.animation_settings.fire;
        SodiumExtraClientMod.options().animationSettings.portal = configData.animation_settings.portal;
        SodiumExtraClientMod.options().animationSettings.blockAnimations = configData.animation_settings.block_animations;
        SodiumExtraClientMod.options().animationSettings.sculkSensor = configData.animation_settings.sculk_sensor;

        SodiumExtraClientMod.options().particleSettings.particles = configData.particle_settings.particles;
        SodiumExtraClientMod.options().particleSettings.rainSplash = configData.particle_settings.rain_splash;
        SodiumExtraClientMod.options().particleSettings.blockBreak = configData.particle_settings.block_break;
        SodiumExtraClientMod.options().particleSettings.blockBreaking = configData.particle_settings.block_breaking;
        SodiumExtraClientMod.options().particleSettings.otherMap = configData.particle_settings.other;

        SodiumExtraClientMod.options().detailSettings.sky = configData.detail_settings.sky;
        SodiumExtraClientMod.options().detailSettings.sunMoon = configData.detail_settings.sun_moon;
        SodiumExtraClientMod.options().detailSettings.stars = configData.detail_settings.stars;
        SodiumExtraClientMod.options().detailSettings.rainSnow = configData.detail_settings.rain_snow;
        SodiumExtraClientMod.options().detailSettings.biomeColors = configData.detail_settings.biome_colors;
        SodiumExtraClientMod.options().detailSettings.skyColors = configData.detail_settings.sky_colors;

        SodiumExtraClientMod.options().renderSettings.fogDistance = configData.render_settings.fog_distance;
        SodiumExtraClientMod.options().renderSettings.fogStart = configData.render_settings.fog_start;
        SodiumExtraClientMod.options().renderSettings.multiDimensionFogControl = configData.render_settings.multi_dimension_fog_control;
        SodiumExtraClientMod.options().renderSettings.dimensionFogDistanceMap = configData.render_settings.dimensionFogDistance;
        SodiumExtraClientMod.options().renderSettings.lightUpdates = configData.render_settings.light_updates;
        SodiumExtraClientMod.options().renderSettings.itemFrame = configData.render_settings.item_frame;
        SodiumExtraClientMod.options().renderSettings.armorStand = configData.render_settings.armor_stand;
        SodiumExtraClientMod.options().renderSettings.painting = configData.render_settings.painting;
        SodiumExtraClientMod.options().renderSettings.piston = configData.render_settings.piston;
        SodiumExtraClientMod.options().renderSettings.beaconBeam = configData.render_settings.beacon_beam;
        SodiumExtraClientMod.options().renderSettings.enchantingTableBook = configData.render_settings.enchanting_table_book;
        SodiumExtraClientMod.options().renderSettings.itemFrameNameTag = configData.render_settings.item_frame_name_tag;
        SodiumExtraClientMod.options().renderSettings.playerNameTag = configData.render_settings.player_name_tag;

        SodiumExtraClientMod.options().extraSettings.overlayCorner = SodiumExtraGameOptions.OverlayCorner.valueOf(configData.extra_settings.overlay_corner);
        SodiumExtraClientMod.options().extraSettings.textContrast = SodiumExtraGameOptions.TextContrast.valueOf(configData.extra_settings.text_contrast);
        SodiumExtraClientMod.options().extraSettings.showFps = configData.extra_settings.show_fps;
        SodiumExtraClientMod.options().extraSettings.showFPSExtended = configData.extra_settings.show_f_p_s_extended;
        SodiumExtraClientMod.options().extraSettings.showCoords = configData.extra_settings.show_coords;
        SodiumExtraClientMod.options().extraSettings.reduceResolutionOnMac = configData.extra_settings.reduce_resolution_on_mac;
        SodiumExtraClientMod.options().extraSettings.useAdaptiveSync = configData.extra_settings.use_adaptive_sync;
        SodiumExtraClientMod.options().extraSettings.cloudHeight = configData.extra_settings.cloud_height;
        SodiumExtraClientMod.options().extraSettings.cloudDistance = configData.extra_settings.cloud_distance;
        SodiumExtraClientMod.options().extraSettings.toasts = configData.extra_settings.toasts;
        SodiumExtraClientMod.options().extraSettings.advancementToast = configData.extra_settings.advancement_toast;
        SodiumExtraClientMod.options().extraSettings.recipeToast = configData.extra_settings.recipe_toast;
        SodiumExtraClientMod.options().extraSettings.systemToast = configData.extra_settings.system_toast;
        SodiumExtraClientMod.options().extraSettings.tutorialToast = configData.extra_settings.tutorial_toast;
        SodiumExtraClientMod.options().extraSettings.instantSneak = configData.extra_settings.instant_sneak;
        SodiumExtraClientMod.options().extraSettings.preventShaders = configData.extra_settings.prevent_shaders;
        SodiumExtraClientMod.options().extraSettings.steadyDebugHud = configData.extra_settings.steady_debug_hud;
        SodiumExtraClientMod.options().extraSettings.steadyDebugHudRefreshInterval = configData.extra_settings.steady_debug_hud_refresh_interval;

        SodiumExtraClientMod.options().writeChanges();
    }

    public static class ConfigData {
        public AnimationSettings animation_settings;
        public ParticleSettings particle_settings;
        public DetailSettings detail_settings;
        public RenderSettings render_settings;
        public ExtraSettings extra_settings;

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
    }
}