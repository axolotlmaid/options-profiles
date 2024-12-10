package com.axolotlmaid.optionsprofiles.profiles.loaders;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptions;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class SodiumLoader {
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
        SodiumClientMod.options().quality.weatherQuality = SodiumGameOptions.GraphicsQuality.valueOf(configuration.quality.weather_quality);
        SodiumClientMod.options().quality.leavesQuality = SodiumGameOptions.GraphicsQuality.valueOf(configuration.quality.leaves_quality);
        SodiumClientMod.options().quality.enableVignette = configuration.quality.enable_vignette;

        SodiumClientMod.options().advanced.enableMemoryTracing = configuration.advanced.enable_memory_tracing;
        SodiumClientMod.options().advanced.useAdvancedStagingBuffers = configuration.advanced.use_advanced_staging_buffers;
        SodiumClientMod.options().advanced.cpuRenderAheadLimit = configuration.advanced.cpu_render_ahead_limit;

        SodiumClientMod.options().performance.chunkBuilderThreads = configuration.performance.chunk_builder_threads;
        SodiumClientMod.options().performance.alwaysDeferChunkUpdates = configuration.performance.always_defer_chunk_updates_v2;
        SodiumClientMod.options().performance.animateOnlyVisibleTextures = configuration.performance.animate_only_visible_textures;
        SodiumClientMod.options().performance.useEntityCulling = configuration.performance.use_entity_culling;
        SodiumClientMod.options().performance.useFogOcclusion = configuration.performance.use_fog_occlusion;
        SodiumClientMod.options().performance.useBlockFaceCulling = configuration.performance.use_block_face_culling;
        SodiumClientMod.options().performance.useNoErrorGLContext = configuration.performance.use_no_error_g_l_context;
        SodiumClientMod.options().performance.sortingEnabled = configuration.performance.sorting_enabled_v2;

        SodiumClientMod.options().notifications.hasClearedDonationButton = configuration.notifications.has_cleared_donation_button;
        SodiumClientMod.options().notifications.hasSeenDonationPrompt = configuration.notifications.has_seen_donation_prompt;

        try {
            SodiumGameOptions.writeToDisk(SodiumClientMod.options());
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading Sodium's configuration", e);
        }
    }

    public static class Configuration {
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
            public boolean enable_memory_tracing;
            public boolean use_advanced_staging_buffers;
            public int cpu_render_ahead_limit;
        }

        public static class Performance {
            public int chunk_builder_threads;
            public boolean always_defer_chunk_updates_v2;
            public boolean animate_only_visible_textures;
            public boolean use_entity_culling;
            public boolean use_fog_occlusion;
            public boolean use_block_face_culling;
            public boolean use_no_error_g_l_context;
            public boolean sorting_enabled_v2;
        }

        public static class Notifications {
            public boolean has_cleared_donation_button;
            public boolean has_seen_donation_prompt;
        }
    }
}