package com.hagenberg.fh.nomoretorchspam.core;

import com.hagenberg.fh.nomoretorchspam.common.block.GlowCrystal;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EvListener {

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent ev) {
        if (!ev.isCanceled() && ev.getPlacedBlock().getBlock() instanceof GlowCrystal) {
            GlowCrystal.placed();
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent ev) {
        if (!ev.isCanceled() && ev.getState().getBlock() instanceof GlowCrystal) {
            GlowCrystal.broken();
        }
    }
}
