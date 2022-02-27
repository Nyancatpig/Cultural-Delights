package net.ncpbails.culturaldelights.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.item.ModItems;

public class CornUpperBlock extends CropsBlock {
    public static final IntegerProperty CORN_AGE;
    private static final VoxelShape[] SHAPE_BY_AGE;

    public CornUpperBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public IntegerProperty getAgeProperty() {
        return CORN_AGE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_AGE[(Integer)state.get(this.getAgeProperty())];
    }

    public int getMaxAge() {
        return 3;
    }

    protected IItemProvider getSeedsItem() {
        return (IItemProvider) ModItems.CORN_KERNELS.get();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CORN_AGE});
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() == net.ncpbails.culturaldelights.block.ModBlocks.CORN.get();
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return super.getBonemealAgeIncrease(worldIn) / 3;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.CORN.get();
    }

    static {
        CORN_AGE = BlockStateProperties.AGE_0_3;
        SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D), Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D), Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)};
    }
}
