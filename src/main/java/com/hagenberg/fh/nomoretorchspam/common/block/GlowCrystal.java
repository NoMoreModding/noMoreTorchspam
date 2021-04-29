package com.hagenberg.fh.nomoretorchspam.common.block;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.core.init.BlockInit;
import com.hagenberg.fh.nomoretorchspam.tileentity.GlowCrystalTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;

// This is the block class of GlowCrystal

public class GlowCrystal extends Block  {

    private final int HEIGHTDIFF = 5;
    private final int RADIUSDIFF = 6;

    private ArrayList<BlockPos> positions;

    //Blockstate that provides the number of crystals in the block
    public static final IntegerProperty CRYSTALS = IntegerProperty.create("crystals",1,4);

    public GlowCrystal(Properties properties) {
        super(properties);
    }


    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer,stack);
        positions = new ArrayList<>();
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was placed");
        }

        //saves the positions of the placed glowlights
        createGlowlights(world, pos, 6,4);


        //gets the tile Entity and sets the positions of Glowlights in it
        TileEntity te = world.getBlockEntity(pos);
        if(te instanceof GlowCrystalTileEntity){
            if (NoMoreTorchSpam.DEBUGMODE) {
                NoMoreTorchSpam.LOGGER.info("Setting positions in tile entity");
            }
            GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
            GlowTE.setBlockPositions(positions);
            GlowTE.setChanged();
        }
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was broken");
        }
        TileEntity te = world.getBlockEntity(pos);
        //gets the tile Entity and the positions of Glowlights in it
        if(te instanceof GlowCrystalTileEntity){
            if (NoMoreTorchSpam.DEBUGMODE) {
                NoMoreTorchSpam.LOGGER.info("Getting positions in tile entity");
            }
            GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
            positions = GlowTE.getBlockPositions();
        }
        destroyGlowlights(world);

        super.playerWillDestroy(world, pos, state, player);
    }


    //sets the glowlights and inputs the positions to the list for the tile entity
    private void createGlowlights(World world, BlockPos center, int radius, int height){
        int calcRadius = radius * radius + radius;
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Trying to place glowlights");
        }
        for(int z = -radius; z <= radius; z++){
            for(int x = - radius; x <= radius; x++){
                for(int y = center.getY()-HEIGHTDIFF < 0 ? 0 : -HEIGHTDIFF; y < height * HEIGHTDIFF; y += HEIGHTDIFF){
                    if(x*x+z*z < calcRadius ){
                        BlockPos pos = new BlockPos(x+center.getX(),y+center.getY(),z+center.getZ());
                        if(world.getBlockState(pos).getBlock() instanceof AirBlock) {
                            world.setBlock(pos, BlockInit.GLOW_LIGHT.get().defaultBlockState(), 0);
                            positions.add(pos);
                            }
                        }
                    }
                }
            }
        }


    private void destroyGlowlights(World world){
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Trying to destroy glowlights");
        }
        if(positions == null) return;
        for(BlockPos pos: positions){
            if(pos != null && world.getBlockState(pos).getBlock() instanceof GlowLight){

                world.setBlock(pos,Blocks.AIR.defaultBlockState(),0);
            }
        }
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRYSTALS);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GlowCrystalTileEntity();
    }

}
