package com.hagenberg.fh.nomoretorchspam;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class mod_blocks {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, nomoretorchspam.MODID);

    //basic light block
    public static final RegistryObject<Block> NMTS_MAIN = BLOCKS.register("nmts_light",()->new Block(AbstractBlock.Properties.of(Material.WOOD)
            .lightLevel(lightLevel -> 15)));

}
