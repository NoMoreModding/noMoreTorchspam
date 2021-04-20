package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.common.block.GlowCrystal;
import com.hagenberg.fh.nomoretorchspam.common.block.GlowLight;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NoMoreTorchSpam.Mod_ID);

    //This registers the block in the game
    public static final RegistryObject<GlowCrystal> GLOW_CRYSTAL = BLOCKS.register("glow_crystal",
            ()-> new GlowCrystal(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS)
                    .instabreak().dynamicShape().lightLevel(lightLevel ->15).noOcclusion()));

    public static final RegistryObject<GlowLight> GLOW_LIGHT = BLOCKS.register("glow_light",
            ()-> new GlowLight(AbstractBlock.Properties.of(Material.PLANT).lightLevel(lightLevel -> 15).noCollission()));
}
