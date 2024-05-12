package com.axolotlmaid.optionsprofiles.profiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class SodiumConfigLoader {
    public static void load(Path file) {
        try (FileReader reader = new FileReader(file.toFile())) {
            Gson gson = new GsonBuilder().create();
            ConfigData configData = gson.fromJson(reader, ConfigData.class);

            apply(configData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void apply(ConfigData configData) {
        SodiumClientMod.options().notifications.hideDonationButton = configData.notifications.hide_donation_button;

        SodiumClientMod.options().quality.cloudQuality = SodiumGameOptions.GraphicsQuality.valueOf(configData.quality.cloud_quality);
        SodiumClientMod.options().quality.weatherQuality = SodiumGameOptions.GraphicsQuality.valueOf(configData.quality.weather_quality);
        SodiumClientMod.options().quality.enableVignette = configData.quality.enable_vignette;
        SodiumClientMod.options().quality.enableClouds = configData.quality.enable_clouds;
        SodiumClientMod.options().quality.smoothLighting = SodiumGameOptions.LightingQuality.valueOf(configData.quality.smooth_lighting);

        SodiumClientMod.options().advanced.useVertexArrayObjects = configData.advanced.use_vertex_array_objects;
        SodiumClientMod.options().advanced.useChunkMultidraw = configData.advanced.use_chunk_multidraw;
        SodiumClientMod.options().advanced.animateOnlyVisibleTextures = configData.advanced.animate_only_visible_textures;
        SodiumClientMod.options().advanced.useEntityCulling = configData.advanced.use_entity_culling;
        SodiumClientMod.options().advanced.useParticleCulling = configData.advanced.use_particle_culling;
        SodiumClientMod.options().advanced.useFogOcclusion = configData.advanced.use_fog_occlusion;
        SodiumClientMod.options().advanced.useCompactVertexFormat = configData.advanced.use_compact_vertex_format;
        SodiumClientMod.options().advanced.useBlockFaceCulling = configData.advanced.use_block_face_culling;
        SodiumClientMod.options().advanced.allowDirectMemoryAccess = configData.advanced.allow_direct_memory_access;
        SodiumClientMod.options().advanced.ignoreDriverBlacklist = configData.advanced.ignore_driver_blacklist;

        try {
            SodiumClientMod.options().writeChanges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ConfigData {
        public Quality quality;
        public Advanced advanced;
        public Notifications notifications;

        public static class Quality {
            public String cloud_quality;
            public String weather_quality;
            public boolean enable_vignette;
            public boolean enable_clouds;
            public String smooth_lighting;
        }

        public static class Advanced {
            public boolean use_vertex_array_objects;
            public boolean use_chunk_multidraw;
            public boolean animate_only_visible_textures;
            public boolean use_entity_culling;
            public boolean use_particle_culling;
            public boolean use_fog_occlusion;
            public boolean use_compact_vertex_format;
            public boolean use_block_face_culling;
            public boolean allow_direct_memory_access;
            public boolean ignore_driver_blacklist;
        }

        public static class Notifications {
            public boolean hide_donation_button;
        }
    }
}