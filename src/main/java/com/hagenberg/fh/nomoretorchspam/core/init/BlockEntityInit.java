package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.tileentity.GlowCrystalTileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NoMoreTorchSpam.Mod_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GlowCrystalTileEntity>> GLOW_CRYSTAL_TILE_ENTITY =

            BLOCK_ENTITIES.register("glow_crystal_tile_entity", () -> BlockEntityType.Builder.of(GlowCrystalTileEntity::new, BlockInit.GLOW_CRYSTAL.get()).build(null));
}