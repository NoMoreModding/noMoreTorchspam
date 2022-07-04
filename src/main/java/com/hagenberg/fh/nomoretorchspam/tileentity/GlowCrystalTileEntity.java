package com.hagenberg.fh.nomoretorchspam.tileentity;

import com.hagenberg.fh.nomoretorchspam.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class GlowCrystalTileEntity extends BlockEntity {

    private final String pL = "positionList";
    private  ArrayList<BlockPos> positions;


    public GlowCrystalTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GLOW_CRYSTAL_TILE_ENTITY.get(),pos,state);
    }


    public void setBlockPositions(ArrayList<BlockPos> positions){
        this.positions = positions;
    }

    public ArrayList<BlockPos> getBlockPositions(){
        return positions;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbtTag = new CompoundTag();
        saveAdditional(nbtTag);
        return ClientboundBlockEntityDataPacket.create(this);
    }

//    @Override
//    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
//        loadingOperation(pkt.getTag());
//    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        savingOperation(p_187471_,this.positions);
        super.saveAdditional(p_187471_);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        loadingOperation(compound);
    }

    private void loadingOperation(CompoundTag nbt){
        ArrayList<BlockPos> positions = new ArrayList<>();
        ListTag list = nbt.getList(pL, 10);
        for(int i = 0; i < list.size(); i++){
            positions.add(NbtUtils.readBlockPos(list.getCompound(i)));
        }
        this.positions = positions;
    }

    private CompoundTag savingOperation(CompoundTag nbt, ArrayList<BlockPos> positions){
        if(positions != null) {
            ListTag posList = new ListTag();
            for (BlockPos pos : positions) {
                posList.add(NbtUtils.writeBlockPos(pos != null ? pos : new BlockPos(0,0,0)));
            }
            //NoMoreTorchSpam.LOGGER.info("Did Saving OP");
            nbt.put(pL, posList);
        }
        return nbt;
    }


}
