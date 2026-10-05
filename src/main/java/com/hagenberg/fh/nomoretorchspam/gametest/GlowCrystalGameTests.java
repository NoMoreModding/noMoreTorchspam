package com.hagenberg.fh.nomoretorchspam.gametest;

import com.hagenberg.fh.nomoretorchspam.common.block.GlowCrystal;
import com.hagenberg.fh.nomoretorchspam.core.init.BlockInit;
import com.hagenberg.fh.nomoretorchspam.core.init.ItemInit;
import com.hagenberg.fh.nomoretorchspam.tileentity.GlowCrystalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;

import java.util.ArrayList;

/**
 * Regression test for the glow lights that were left behind when the crystal
 * was destroyed by something other than a player.
 */
@GameTestHolder("nomoretorchspam")
public class GlowCrystalGameTests {

    private static final BlockPos CRYSTAL = new BlockPos(8, 9, 8);

    // NeoForge resolves the template to <lowercased class name>.<template>, i.e.
    // nomoretorchspam:glowcrystalgametests.empty -> structure/glowcrystalgametests.empty.nbt
    @GameTest(template = "empty")
    public static void glowlightsAreRemovedOnExplosion(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos crystalPos = helper.absolutePos(CRYSTAL);

        // place the crystal and create its glow lights. setPlacedBy is only called when placing
        // with an item, so it has to be triggered by hand here.
        BlockState crystalState = BlockInit.GLOW_CRYSTAL.get().defaultBlockState()
                .setValue(GlowCrystal.CRYSTALS, 1);
        level.setBlock(crystalPos, crystalState, 3);
        BlockInit.GLOW_CRYSTAL.get().setPlacedBy(level, crystalPos, crystalState, null,
                new ItemStack(ItemInit.GLOW_CRYSTAL.get()));

        BlockEntity blockEntity = level.getBlockEntity(crystalPos);
        if (!(blockEntity instanceof GlowCrystalTileEntity)) {
            helper.fail("GlowCrystalTileEntity was not created");
            return;
        }
        ArrayList<BlockPos> lights = new ArrayList<>(((GlowCrystalTileEntity) blockEntity).getBlockPositions());
        if (lights.isEmpty()) {
            helper.fail("The crystal did not create any glowlights");
            return;
        }

        level.explode(null, crystalPos.getX() + 0.5D, crystalPos.getY() + 0.5D, crystalPos.getZ() + 0.5D,
                4.0F, Level.ExplosionInteraction.BLOCK);

        if (level.getBlockState(crystalPos).getBlock() != Blocks.AIR) {
            helper.fail("The explosion did not destroy the glow crystal");
            return;
        }

        for (BlockPos light : lights) {
            if (level.getBlockState(light).getBlock() instanceof LightBlock) {
                helper.fail("Glowlight was left behind at " + light.toShortString());
                return;
            }
        }

        helper.succeed();
    }
}