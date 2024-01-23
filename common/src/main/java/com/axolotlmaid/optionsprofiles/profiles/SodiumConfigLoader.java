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
        SodiumClientMod.options().quality.weatherQuality = SodiumGameOptions.GraphicsQuality.valueOf(configData.quality.weather_quality);
        SodiumClientMod.options().quality.leavesQuality = SodiumGameOptions.GraphicsQuality.valueOf(configData.quality.leaves_quality);
        SodiumClientMod.options().quality.enableVignette = configData.quality.enable_vignette;

        SodiumClientMod.options().advanced.arenaMemoryAllocator = SodiumGameOptions.ArenaMemoryAllocator.valueOf(configData.advanced.arena_memory_allocator);
        SodiumClientMod.options().advanced.allowDirectMemoryAccess = configData.advanced.allow_direct_memory_access;
        SodiumClientMod.options().advanced.enableMemoryTracing = configData.advanced.enable_memory_tracing;
        SodiumClientMod.options().advanced.useAdvancedStagingBuffers = configData.advanced.use_advanced_staging_buffers;
        SodiumClientMod.options().advanced.cpuRenderAheadLimit = configData.advanced.cpu_render_ahead_limit;

        SodiumClientMod.options().performance.chunkBuilderThreads = configData.performance.chunk_builder_threads;
        SodiumClientMod.options().performance.alwaysDeferChunkUpdates = configData.performance.always_defer_chunk_updates;
        SodiumClientMod.options().performance.animateOnlyVisibleTextures = configData.performance.animate_only_visible_textures;
        SodiumClientMod.options().performance.useEntityCulling = configData.performance.use_entity_culling;
        SodiumClientMod.options().performance.useParticleCulling = configData.performance.use_particle_culling;
        SodiumClientMod.options().performance.useFogOcclusion = configData.performance.use_fog_occlusion;
        SodiumClientMod.options().performance.useBlockFaceCulling = configData.performance.use_block_face_culling;
//        SodiumClientMod.options() = configData.performance.use_no_error_g_l_context;

        SodiumClientMod.options().notifications.hideDonationButton = configData.notifications.hide_donation_button;

        try {
            SodiumClientMod.options().writeChanges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ConfigData {
        public Quality quality;
        public Advanced advanced;
        public Performance performance;
        public Notifications notifications;

        public static class Quality {
            public String weather_quality;
            public String leaves_quality;
            public boolean enable_vignette;
        }

        public static class Advanced {
            public String arena_memory_allocator;
            public boolean allow_direct_memory_access;
            public boolean enable_memory_tracing;
            public boolean use_advanced_staging_buffers;
            public int cpu_render_ahead_limit;
        }

        public static class Performance {
            public int chunk_builder_threads;
            public boolean always_defer_chunk_updates;
            public boolean animate_only_visible_textures;
            public boolean use_entity_culling;
            public boolean use_particle_culling;
            public boolean use_fog_occlusion;
            public boolean use_block_face_culling;
        }

        public static class Notifications {
            public boolean hide_donation_button;
        }
    }
}