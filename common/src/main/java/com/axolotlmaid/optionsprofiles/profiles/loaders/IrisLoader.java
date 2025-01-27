package com.axolotlmaid.optionsprofiles.profiles.loaders;

import com.axolotlmaid.optionsprofiles.OptionsProfilesMod;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.gui.option.IrisVideoSettings;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class IrisLoader {
    public static void load(Path file) {
        try (FileReader reader = new FileReader(file.toFile())) {
            Properties properties = new Properties();
            properties.load(reader);

            apply(properties);
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when reading and loading Iris's configuration", e);
        }
    }

    private static void apply(Properties properties) {
        Iris.getIrisConfig().setShaderPackName(properties.getProperty("shaderPack"));
        Iris.getIrisConfig().setDebugEnabled("true".equals(properties.getProperty("enableDebugOptions")));
        IrisApi.getInstance().getConfig().setShadersEnabledAndApply("true".equals(properties.getProperty("enableShaders")));

        try {
            Iris.getIrisConfig().save();
        } catch (IOException e) {
            OptionsProfilesMod.LOGGER.error("An error occurred when loading Sodium's configuration", e);
        }
    }
}
