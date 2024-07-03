package com.axolotlmaid.optionsprofiles.profiles.loaders;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.embeddedt.embeddium.impl.Embeddium;
import org.embeddedt.embeddium.impl.gui.EmbeddiumOptions;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class EmbeddiumLoader {
    public static void load(Path file) {
        try (FileReader reader = new FileReader(file.toFile())) {
            Gson gson = new GsonBuilder().create();
            Configuration configData = gson.fromJson(reader, Configuration.class);
            
            apply(configData);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading Sodium's configuration", e);
        }
    }

    private static void apply(Configuration configuration) {
        Embeddium.options().quality.weatherQuality = EmbeddiumOptions.GraphicsQuality.valueOf(configuration.quality.weather_quality);
        Embeddium.options().quality.leavesQuality = EmbeddiumOptions.GraphicsQuality.valueOf(configuration.quality.leaves_quality);
        Embeddium.options().quality.enableVignette = configuration.quality.enable_vignette;

        Embeddium.options().advanced.enableMemoryTracing = configuration.advanced.enable_memory_tracing;
        Embeddium.options().advanced.useAdvancedStagingBuffers = configuration.advanced.use_advanced_staging_buffers;
        Embeddium.options().advanced.disableIncompatibleModWarnings = configuration.advanced.disable_incompatible_mod_warnings;
        Embeddium.options().advanced.cpuRenderAheadLimit = configuration.advanced.cpu_render_ahead_limit;

        Embeddium.options().performance.chunkBuilderThreads = configuration.performance.chunk_builder_threads;
        Embeddium.options().performance.alwaysDeferChunkUpdates = configuration.performance.always_defer_chunk_updates_v2;
        Embeddium.options().performance.animateOnlyVisibleTextures = configuration.performance.animate_only_visible_textures;
        Embeddium.options().performance.useEntityCulling = configuration.performance.use_entity_culling;
        Embeddium.options().performance.useFogOcclusion = configuration.performance.use_fog_occlusion;
        Embeddium.options().performance.useBlockFaceCulling = configuration.performance.use_block_face_culling;
        Embeddium.options().performance.useCompactVertexFormat = configuration.performance.use_compact_vertex_format;
        Embeddium.options().performance.useTranslucentFaceSorting = configuration.performance.use_translucent_face_sorting_v2;
        Embeddium.options().performance.useNoErrorGLContext = configuration.performance.use_no_error_g_l_context;

        Embeddium.options().notifications.hasClearedDonationButton = configuration.notifications.has_cleared_donation_button;
        Embeddium.options().notifications.hasSeenDonationPrompt = configuration.notifications.has_seen_donation_prompt;

        try {
            EmbeddiumOptions.writeToDisk(Embeddium.options());
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading Embeddium's configuration", e);
        }
    }

    public static class Configuration {
        public SodiumLoader.Configuration.Quality quality;
        public Advanced advanced;
        public Performance performance;
        public SodiumLoader.Configuration.Notifications notifications;

        public static class Advanced extends SodiumLoader.Configuration.Advanced {
            public boolean disable_incompatible_mod_warnings;
        }

        public static class Performance extends SodiumLoader.Configuration.Performance {
            public boolean use_compact_vertex_format;
            public boolean use_translucent_face_sorting_v2;
        }
    }
}
