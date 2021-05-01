package com.hagenberg.fh.nomoretorchspam.tileentity;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
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

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        save(nbtTag);
        return new SUpdateTileEntityPacket(this.getBlockPos(),-1,nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        loadingOperation(pkt.getTag());
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        return(savingOperation(compound,positions));
    }


    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        super.load(blockState, compound);

        loadingOperation(compound);
    }

    private void loadingOperation(CompoundNBT nbt){
        ArrayList<BlockPos> positions = new ArrayList<>();
        ListNBT list = nbt.getList(pL, Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < list.size(); i++){
            positions.add(NBTUtil.readBlockPos(list.getCompound(i)));
        }
        this.positions = positions;
    }

    private CompoundNBT savingOperation(CompoundNBT nbt, ArrayList<BlockPos> positions){
        if(positions != null) {
            ListNBT posList = new ListNBT();
            for (BlockPos pos : positions) {
                posList.add(NBTUtil.writeBlockPos(pos != null ? pos : new BlockPos(0,0,0)));
            }
            NoMoreTorchSpam.LOGGER.info("Did Saving OP");
            nbt.put(pL, posList);
        }
        return nbt;
    }


}
