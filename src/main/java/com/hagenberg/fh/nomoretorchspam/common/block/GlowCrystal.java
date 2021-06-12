package com.hagenberg.fh.nomoretorchspam.common.block;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import com.hagenberg.fh.nomoretorchspam.config.Config;
import com.hagenberg.fh.nomoretorchspam.core.init.BlockInit;
import com.hagenberg.fh.nomoretorchspam.tileentity.GlowCrystalTileEntity;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

// This is the block class of GlowCrystal

public class GlowCrystal extends Block  {

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

    private final int HEIGHTDIFF = Config.HEIGHTDIFF.get();
    private final int RADIUSDIFF = Config.RADIUSDIFF.get();
    private final int AMOUNTOFDISCS = Config.AMOUNTOFDISCS.get();
    private final int DISTANCE = Config.DISTANCE.get();


    //Blockstate that provides the number of crystals in the block
    public static final IntegerProperty CRYSTALS = IntegerProperty.create("crystals",1,4);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public GlowCrystal(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer,stack);

        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was placed");
        }
        if(!world.isClientSide){
            //saves the positions of the placed glowlights
            ArrayList <BlockPos> positions = createGlowlights(world, pos, RADIUSDIFF * state.getValue(CRYSTALS), AMOUNTOFDISCS);


            //gets the tile Entity and sets the positions of Glowlights in it
            TileEntity te = world.getBlockEntity(pos);
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
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {


        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Glowcrystal was broken");
        }
        if(!world.isClientSide) {

            TileEntity te = world.getBlockEntity(pos);
            //gets the tile Entity and the positions of Glowlights in it
            if (te instanceof GlowCrystalTileEntity) {
                if (NoMoreTorchSpam.DEBUGMODE) {
                    NoMoreTorchSpam.LOGGER.info("Getting positions in tile entity");
                }
                GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
                ArrayList<BlockPos> positions = GlowTE.getBlockPositions();

                destroyGlowlights(world, positions);
            }
        }

            super.playerWillDestroy(world, pos, state, player);

    }


    //sets the glowlights and inputs the positions to the list for the tile entity
    private ArrayList<BlockPos> createGlowlights(World world, BlockPos center, int radius, int height){

        ArrayList<BlockPos> positions = new ArrayList<>();
        int calcRadius = radius * radius + radius;
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Trying to place glowlights");
        }
        for(int z = -radius; z <= radius; z++){
            for(int x = - radius; x <= radius; x++){
                for(int y = center.getY()-HEIGHTDIFF < 0 ? 0 : -HEIGHTDIFF; y < height * HEIGHTDIFF; y += HEIGHTDIFF){
                    if(x*x+z*z < calcRadius && z % DISTANCE == 0 && x % DISTANCE == 0){
                        BlockPos pos = new BlockPos(x+center.getX(),y+center.getY(),z+center.getZ());
                        if(world.getBlockState(pos).getBlock() instanceof AirBlock) {
                            world.setBlock(pos, BlockInit.GLOW_LIGHT.get().defaultBlockState(), 3,0);
                            positions.add(pos);
                            }
                        }
                    }
                }
            }
        return positions;
        }


    private void destroyGlowlights(World world, ArrayList<BlockPos> positions){
        if (NoMoreTorchSpam.DEBUGMODE) {
            NoMoreTorchSpam.LOGGER.info("Trying to destroy glowlights");
        }
        if(positions == null ||positions.size() == 0) {NoMoreTorchSpam.LOGGER.error("NO POSITIONS FOUND"); return;}
        for(BlockPos pos: positions){
            if(pos != null && world.getBlockState(pos).getBlock() instanceof GlowLight){
                if(world.isClientSide){
                    LOGGER.info(pos.toShortString());
                }
                world.setBlock(pos,Blocks.AIR.defaultBlockState(),3,0);
            }
        }
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRYSTALS);
        builder.add(FACING);
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


    //handling different VoxelShapes

    @SuppressWarnings( "deprecation" )
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
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
    public boolean canBeReplaced(BlockState state, BlockItemUseContext ctx) {
        if (ctx.getItemInHand().getItem() == this.asItem() && state.getValue(CRYSTALS) < 4){
            World world = ctx.getLevel();

            TileEntity te = world.getBlockEntity(ctx.getClickedPos());
            //gets the tile Entity and the positions of Glowlights in it
            if (te instanceof GlowCrystalTileEntity) {
                if (NoMoreTorchSpam.DEBUGMODE) {
                    NoMoreTorchSpam.LOGGER.info("Getting positions in tile entity");
                }
                GlowCrystalTileEntity GlowTE = (GlowCrystalTileEntity) te;
                ArrayList <BlockPos> positions = GlowTE.getBlockPositions();

                destroyGlowlights(world,positions);
            }
            return true;
        } else {
            return super.canBeReplaced(state, ctx);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        Direction direction = ctx.getClickedFace();
        BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos());

        if (blockstate.is(this)) {
            return blockstate.setValue(CRYSTALS, Integer.valueOf(Math.min(4, blockstate.getValue(CRYSTALS) + 1)));
        } else {
            return this.defaultBlockState().setValue(FACING, direction);
        }
    }

    @SuppressWarnings( "deprecation" )
    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    @SuppressWarnings( "deprecation" )
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    @SuppressWarnings( "deprecation" )
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.setValue(FACING, p_185471_2_.mirror(p_185471_1_.getValue(FACING)));
    }

}
