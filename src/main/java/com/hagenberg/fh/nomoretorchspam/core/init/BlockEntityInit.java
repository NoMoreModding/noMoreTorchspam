package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.tileentity.GlowCrystalTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NoMoreTorchSpam.Mod_ID);

    public static final RegistryObject<BlockEntityType<GlowCrystalTileEntity>> GLOW_CRYSTAL_TILE_ENTITY =

            BLOCK_ENTITIES.register("glow_crystal_tile_entity", () -> BlockEntityType.Builder.of(GlowCrystalTileEntity::new, BlockInit.GLOW_CRYSTAL.get()).build(null));
}