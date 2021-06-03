package com.hagenberg.fh.nomoretorchspam.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {
    private static final ForgeConfigSpec.Builder CONFIG = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMEN_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> RADIUSDIFF;
    public static ForgeConfigSpec.ConfigValue<Integer> HEIGHTDIFF;
    public static ForgeConfigSpec.ConfigValue<Integer> AMOUNTOFDISCS;
    public static ForgeConfigSpec.ConfigValue<Integer> DISTANCE;

    static {
        initConfig();
    }

    public static void initConfig() {
        CONFIG.comment(NoMoreTorchSpam.Mod_ID + " Configuration").push("glow_crystal");

        RADIUSDIFF = CONFIG.comment("Dictates the radius of light around the main crystal").defineInRange("RADIUSDIFF", 6, 1, 15);
        HEIGHTDIFF = CONFIG.comment("Dictates the amount of blocks between 2 vertical light disks").defineInRange("HEIGHTDIFF", 5, 2, 10);
        AMOUNTOFDISCS = CONFIG.comment("Dictates the amount of vertical disks that provide light").defineInRange("AMOUNTOFDISCS", 3, 1, 5);
        CONFIG.comment("AMOUNTOFDICS*HEIGHTDIFF = total height of the light");
        DISTANCE = CONFIG.comment("Amount of space between 2 lightsources on the same disk").defineInRange("DISTANCE", 3, 2, 5);

        CONFIG.pop();
        COMMEN_CONFIG = CONFIG.build();
    }

    public static void setup(Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        COMMEN_CONFIG.setConfig(configData);
    }
}
