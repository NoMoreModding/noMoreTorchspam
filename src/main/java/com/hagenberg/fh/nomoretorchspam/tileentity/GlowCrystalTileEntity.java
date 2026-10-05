package com.hagenberg.fh.nomoretorchspam.tileentity;

import com.hagenberg.fh.nomoretorchspam.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class GlowCrystalTileEntity extends BlockEntity {

    private final String pL = "positionList";
    private ArrayList<BlockPos> positions = new ArrayList<>();


    public GlowCrystalTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GLOW_CRYSTAL_TILE_ENTITY.get(), pos, state);
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
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        savingOperation(tag, this.positions);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        loadingOperation(compound);
    }

    private void loadingOperation(CompoundTag nbt){
        ArrayList<BlockPos> positions = new ArrayList<>();
        ListTag list = nbt.getList(pL, Tag.TAG_COMPOUND);
        for(int i = 0; i < list.size(); i++){
            CompoundTag entry = list.getCompound(i);
            positions.add(new BlockPos(entry.getInt("x"), entry.getInt("y"), entry.getInt("z")));
        }
        this.positions = positions;
    }

    private CompoundTag savingOperation(CompoundTag nbt, ArrayList<BlockPos> positions){
        if(positions != null) {
            ListTag posList = new ListTag();
            for (BlockPos pos : positions) {
                BlockPos toWrite = pos != null ? pos : new BlockPos(0, 0, 0);
                CompoundTag entry = new CompoundTag();
                entry.putInt("x", toWrite.getX());
                entry.putInt("y", toWrite.getY());
                entry.putInt("z", toWrite.getZ());
                posList.add(entry);
            }
            nbt.put(pL, posList);
        }
        return nbt;
    }


}