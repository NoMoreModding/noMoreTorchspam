package com.hagenberg.fh.nomoretorchspam.common.block;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.config.Config;
import com.hagenberg.fh.nomoretorchspam.tileentity.GlowCrystalTileEntity;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

// This is the block class of GlowCrystal

public class GlowCrystal extends BaseEntityBlock {
    public static final MapCodec<GlowCrystal> CODEC = simpleCodec(GlowCrystal::new);

    //VoxelShapes :)
    //One
    protected static final VoxelShape Y_ONE_AABB = Block.box(5.0D, 0.0D, 5.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_ONE_AABB = Block.box(5.0D, 5.0D, 0.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape X_ONE_AABB = Block.box(0.0D, 5.0D, 5.0D, 16.0D, 10.0D, 10.0D);

    //Two
    protected static final VoxelShape Y_TWO_AABB = Block.box(0.0D, 0.0D, 5.0D, 12.0D, 16.0D, 11.0D);
    protected static final VoxelShape Z_TWO_AABB = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 12.0D, 16.0D);
    protected static final VoxelShape X_TWO_AABB = Block.box(0.0D, 5.0D, 0.0D, 16.0D, 11.0D, 12.0D);

    //Three
    protected static final VoxelShape Y_THREE_AABB = Block.box(0.0D, 0.0D, 4.0D, 13.0D, 16.0D, 15.0D);
    protected static final VoxelShape Z_THREE_AABB = Block.box(4.0D, 0.0D, 0.0D, 15.0D, 13.0D, 16.0D);
    protected static final VoxelShape X_THREE_AABB = Block.box(0.0D, 4.0D, 0.0D, 16.0D, 15.0D, 13.0D);

    //Four
    protected static final VoxelShape Y_FOUR_AABB = Block.box(0.0D, 0.0D, 2.0D, 13.0D, 16.0D, 15.0D);
    protected static final VoxelShape Z_FOUR_AABB = Block.box(2.0D, 0.0D, 0.0D, 15.0D, 13.0D, 16.0D);
    protected static final VoxelShape X_FOUR_AABB = Block.box(0.0D, 2.0D, 0.0D, 16.0D, 15.0D, 13.0D);

    //Blockstate that provides the number of crystals in the block
    public static final IntegerProperty CRYSTALS = IntegerProperty.create("crystals",1,4);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GlowCrystalTileEntity(pos,state);
    }

    public GlowCrystal(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    public MapCodec<? extends GlowCrystal> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer,stack);

        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was placed");
        }
        if(!world.isClientSide){
            //saves the positions of the placed glowlights
            ArrayList <BlockPos> positions = createGlowlights(world, pos, Config.RADIUSDIFF * state.getValue(CRYSTALS), Config.AMOUNTOFDISCS);


            //gets the tile Entity and sets the positions of Glowlights in it
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof GlowCrystalTileEntity) {
                if (NoMoreTorchSpam.DEBUGMODE) {
                    NoMoreTorchSpam.LOGGER.info("Setting positions in tile entity");
                }
                GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
                GlowTE.setBlockPositions(positions);

                world.sendBlockUpdated(pos, state, state, 2);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof GlowCrystalTileEntity) {
                if (NoMoreTorchSpam.DEBUGMODE) {
                    NoMoreTorchSpam.LOGGER.info("Glowcrystal was removed, cleaning up glowlights");
                }
                GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
                ArrayList<BlockPos> positions = GlowTE.getBlockPositions();

                destroyGlowlights(world, positions, pos);
            }
        }

        super.onRemove(state, world, pos, newState, movedByPiston);
    }


    //sets the glowlights and inputs the positions to the list for the tile entity
    private ArrayList<BlockPos> createGlowlights(Level world, BlockPos center, int radius, int height){

        ArrayList<BlockPos> positions = new ArrayList<>();
        int calcRadius = radius * radius + radius;
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Trying to place glowlights");
        }
        for(int z = -radius; z <= radius; z++){
            for(int x = - radius; x <= radius; x++){
                for(int y = center.getY()-Config.HEIGHTDIFF < 0 ? 0 : -Config.HEIGHTDIFF; y < height * Config.HEIGHTDIFF; y += Config.HEIGHTDIFF){
                    if(x*x+z*z < calcRadius && z % Config.DISTANCE == 0 && x % Config.DISTANCE == 0){
                        BlockPos pos = new BlockPos(x+center.getX(),y+center.getY(),z+center.getZ());
                        if(world.getBlockState(pos).getBlock() instanceof AirBlock) {
                            world.setBlock(pos, Blocks.LIGHT.defaultBlockState(), 3,0);
                            positions.add(pos);
                            }
                        }
                    }
                }
            }
        return positions;
        }


    private void destroyGlowlights(Level world, ArrayList<BlockPos> positions, BlockPos blockPos){
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Trying to destroy glowlights");
        }
        if(positions == null ||positions.size() == 0) {
            return;
        }

        for(BlockPos lightPos: positions){
            if(lightPos != null && world.getBlockState(lightPos).getBlock() instanceof LightBlock){
                world.setBlock(lightPos, Blocks.AIR.defaultBlockState(),3,0);
            }
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRYSTALS);
        builder.add(FACING);
        }

    //handling different VoxelShapes

    @SuppressWarnings( "deprecation" )
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction.Axis axis = state.getValue(FACING).getAxis();

        switch(state.getValue(CRYSTALS)) {
            case 1:
            default:
                switch(axis) {
                    case X:
                    default:
                        return X_ONE_AABB;
                    case Z:
                        return Z_ONE_AABB;
                    case Y:
                        return Y_ONE_AABB;
                }
            case 2:
                switch(axis) {
                    case X:
                    default:
                        return X_TWO_AABB;
                    case Z:
                        return Z_TWO_AABB;
                    case Y:
                        return Y_TWO_AABB;
                }
            case 3:
                switch(axis) {
                    case X:
                    default:
                        return X_THREE_AABB;
                    case Z:
                        return Z_THREE_AABB;
                    case Y:
                        return Y_THREE_AABB;
                }
            case 4:
                switch(axis) {
                    case X:
                    default:
                        return X_FOUR_AABB;
                    case Z:
                        return Z_FOUR_AABB;
                    case Y:
                        return Y_FOUR_AABB;
                }

        }
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (ctx.getItemInHand().getItem() == this.asItem() && state.getValue(CRYSTALS) < 4){
            Level world = ctx.getLevel();

            BlockEntity te = world.getBlockEntity(ctx.getClickedPos());
            //gets the tile Entity and the positions of Glowlights in it
            if (te instanceof GlowCrystalTileEntity) {
                if (NoMoreTorchSpam.DEBUGMODE) {
                    NoMoreTorchSpam.LOGGER.info("Getting positions in tile entity");
                }
                GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
                ArrayList <BlockPos> positions = GlowTE.getBlockPositions();

                destroyGlowlights(world,positions, ctx.getClickedPos());
            }
            return true;
        } else {
            return super.canBeReplaced(state, ctx);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getClickedFace();
        BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos());

        if (blockstate.is(this)) {
            return blockstate.setValue(CRYSTALS, Integer.valueOf(Math.min(4, blockstate.getValue(CRYSTALS) + 1)));
        } else {
            return this.defaultBlockState().setValue(FACING, direction);
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

}