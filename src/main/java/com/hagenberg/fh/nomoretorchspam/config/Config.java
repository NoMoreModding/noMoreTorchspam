package com.hagenberg.fh.nomoretorchspam.config;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Config for the glow crystal.
 * <p>
 * The values are exposed as plain {@code int} fields that are refreshed whenever FML loads or
 * reloads the config. Reading the {@link ModConfigSpec} values directly is not possible from the
 * block, because blocks are constructed during registry events - long before a server config is
 * loaded.
 */
@EventBusSubscriber(modid = NoMoreTorchSpam.Mod_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ModConfigSpec.IntValue RADIUSDIFF_SPEC;
    public static final ModConfigSpec.IntValue HEIGHTDIFF_SPEC;
    public static final ModConfigSpec.IntValue AMOUNTOFDISCS_SPEC;
    public static final ModConfigSpec.IntValue DISTANCE_SPEC;

    public static final ModConfigSpec COMMEN_CONFIG;

    // Cached values, kept in sync with the config file by the event handlers below.
    public static int RADIUSDIFF = 6;
    public static int HEIGHTDIFF = 5;
    public static int AMOUNTOFDISCS = 3;
    public static int DISTANCE = 3;

    static {
        ModConfigSpec.Builder config = new ModConfigSpec.Builder();

        config.comment(NoMoreTorchSpam.Mod_ID + " Configuration").push("glow_crystal");

        RADIUSDIFF_SPEC = config.comment("Dictates the radius of light around the main crystal").defineInRange("RADIUSDIFF", 6, 1, 15);
        HEIGHTDIFF_SPEC = config.comment("Dictates the amount of blocks between 2 vertical light disks").defineInRange("HEIGHTDIFF", 5, 2, 10);
        AMOUNTOFDISCS_SPEC = config.comment("Dictates the amount of vertical disks that provide light").defineInRange("AMOUNTOFDISCS", 3, 1, 5);
        config.comment("AMOUNTOFDICS*HEIGHTDIFF = total height of the light");
        DISTANCE_SPEC = config.comment("Amount of space between 2 lightsources on the same disk").defineInRange("DISTANCE", 3, 2, 5);

        config.pop();
        COMMEN_CONFIG = config.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        update();
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        update();
    }

    private static void update() {
        RADIUSDIFF = RADIUSDIFF_SPEC.get();
        HEIGHTDIFF = HEIGHTDIFF_SPEC.get();
        AMOUNTOFDISCS = AMOUNTOFDISCS_SPEC.get();
        DISTANCE = DISTANCE_SPEC.get();
    }
}