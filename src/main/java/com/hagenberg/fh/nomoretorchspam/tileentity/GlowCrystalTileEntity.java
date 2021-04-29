package com.hagenberg.fh.nomoretorchspam.tileentity;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class GlowCrystalTileEntity extends TileEntity {

    private final String pL = "positionList";
    private  ArrayList<BlockPos> positions;


    public GlowCrystalTileEntity() {
        super(TileEntityInit.GLOWCRYSTAL_TILE_ENTITY.get());
    }


    public void setBlockPositions(ArrayList<BlockPos> positions){
        this.positions = positions;
    }

    public ArrayList<BlockPos> getBlockPositions(){
        return positions;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = new CompoundNBT();
        save(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.load(state,tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Saving to compound");
        }
        //saves the pos in an NBT list
        ListNBT posList = new ListNBT();
        for(BlockPos pos : positions){

            posList.add(NBTUtil.writeBlockPos(pos));
        }

        //saves NBT list into the compound
        compound.put(pL,posList);
        return compound;
    }


    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Reading from compound");
        }
        super.load(blockState, compound);
        ArrayList<BlockPos> positions = new ArrayList<>();
        ListNBT list = compound.getList(pL, Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < list.size(); i++){
            positions.add(NBTUtil.readBlockPos(list.getCompound(i)));
        }
        this.positions = positions;
    }
}
