package com.hagenberg.fh.nomoretorchspam.common.block;

import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

// This is the block class of GlowCrystal

public class GlowCrystal extends Block {

        public GlowCrystal(Properties properties){
        super(properties);
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent ev){
            // TODO add other things that happen when block is placed
    }
    // TODO add what needs to be done when block is broken

}
