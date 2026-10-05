package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.common.block.GlowCrystal;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, NoMoreTorchSpam.Mod_ID);

    public static final DeferredHolder<Block, GlowCrystal> GLOW_CRYSTAL = BLOCKS.register("glow_crystal",
            () -> new GlowCrystal(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.GLASS)
                    .instabreak()
                    .dynamicShape()
                    .lightLevel(lightLevel -> 15)
                    .noOcclusion()));


}