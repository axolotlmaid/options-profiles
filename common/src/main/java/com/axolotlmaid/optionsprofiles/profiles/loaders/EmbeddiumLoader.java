package com.axolotlmaid.optionsprofiles.profiles.loaders;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;

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
        SodiumClientMod.options().quality.weatherQuality = SodiumGameOptions.GraphicsQuality.valueOf(configuration.quality.weather_quality);
        SodiumClientMod.options().quality.leavesQuality = SodiumGameOptions.GraphicsQuality.valueOf(configuration.quality.leaves_quality);
        SodiumClientMod.options().quality.enableVignette = configuration.quality.enable_vignette;

        SodiumClientMod.options().advanced.arenaMemoryAllocator = SodiumGameOptions.ArenaMemoryAllocator.valueOf(configuration.advanced.arena_memory_allocator);
        SodiumClientMod.options().advanced.allowDirectMemoryAccess = configuration.advanced.allow_direct_memory_access;
        SodiumClientMod.options().advanced.enableMemoryTracing = configuration.advanced.enable_memory_tracing;
        SodiumClientMod.options().advanced.useAdvancedStagingBuffers = configuration.advanced.use_advanced_staging_buffers;
        SodiumClientMod.options().advanced.disableIncompatibleModWarnings = configuration.advanced.disable_incompatible_mod_warnings;
        SodiumClientMod.options().advanced.cpuRenderAheadLimit = configuration.advanced.cpu_render_ahead_limit;

        SodiumClientMod.options().performance.chunkBuilderThreads = configuration.performance.chunk_builder_threads;
        SodiumClientMod.options().performance.alwaysDeferChunkUpdates = configuration.performance.always_defer_chunk_updates;
        SodiumClientMod.options().performance.animateOnlyVisibleTextures = configuration.performance.animate_only_visible_textures;
        SodiumClientMod.options().performance.useEntityCulling = configuration.performance.use_entity_culling;
        SodiumClientMod.options().performance.useParticleCulling = configuration.performance.use_particle_culling;
        SodiumClientMod.options().performance.useFogOcclusion = configuration.performance.use_fog_occlusion;
        SodiumClientMod.options().performance.useBlockFaceCulling = configuration.performance.use_block_face_culling;
        SodiumClientMod.options().performance.useCompactVertexFormat = configuration.performance.use_compact_vertex_format;
        SodiumClientMod.options().performance.useTranslucentFaceSorting = configuration.performance.use_translucent_face_sorting;

        SodiumClientMod.options().notifications.hideDonationButton = configuration.notifications.hide_donation_button;

        try {
            SodiumClientMod.options().writeChanges();
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
            public boolean use_translucent_face_sorting;
        }
    }
}