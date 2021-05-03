package config;

import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.ForgeConfigSpec;

public class GlowCrystalConfig {
    public static ForgeConfigSpec.IntValue RADIUSDIFF;
    public static ForgeConfigSpec.IntValue HEIGHTDIFF;
    public static ForgeConfigSpec.IntValue AMOUNTOFDISCS;
    public static ForgeConfigSpec.IntValue DISTANCE;

    public static void init(ForgeConfigSpec.Builder config){
        config.comment("GlowCrystal Config");

         RADIUSDIFF= config.comment("Dictates the radius of light around the main crystal").defineInRange("GlowCrystal.RADIUSDIFF",6,1,15);
        HEIGHTDIFF = config.comment("Dictates the amount of blocks between 2 vertical light disks").defineInRange("GlowCrystal.HEIGHTDIFF",5,2,10);
        AMOUNTOFDISCS = config.comment("Dictates the amount of vertical disks that provide light").defineInRange("GlowCrystal.AMOUNTOFDISCS",3,1,5);
        config.comment("AMOUNTOFDICS*HEIGHTDIFF = total height of the light");
        DISTANCE = config.comment("Amount of space between 2 lightsources on the same disk").defineInRange("GlowCrystal.DISTANCE",3,2,5);
    }

}
