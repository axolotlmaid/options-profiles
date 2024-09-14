package com.axolotlmaid.optionsprofiles;

import com.axolotlmaid.optionsprofiles.profiles.Profiles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptionsProfilesMod {
    public static final String MOD_ID = "optionsprofiles";
    public static final Logger LOGGER = LogManager.getLogger("Options Profiles");

    public static final Profiles PROFILES_INSTANCE = new Profiles();

    public static void init() {}
}
