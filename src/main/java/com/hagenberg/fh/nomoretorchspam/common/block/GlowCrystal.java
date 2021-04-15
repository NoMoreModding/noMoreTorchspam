package com.hagenberg.fh.nomoretorchspam.common.block;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.minecraft.block.Block;

// This is the block class of GlowCrystal

public class GlowCrystal extends Block {

    public GlowCrystal(Properties properties) {
        super(properties);

    }
    //is called by EvListener when this has been placed
    public static void placed() {
        //TODO add placement of other blocks
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was placed");
        }
    }
    //is called by EvListener when this has been broken
    public static void broken() {
        // TODO add destruction of other blocks
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was broken");
        }
    }


}
