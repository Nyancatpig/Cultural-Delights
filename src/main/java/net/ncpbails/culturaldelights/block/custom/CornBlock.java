package net.ncpbails.culturaldelights.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.item.ModItems;


import javax.annotation.Nullable;
import java.util.Random;

public class CornBlock extends BushBlock implements IGrowable{
    public static final IntegerProperty AGE;
    public static final BooleanProperty SUPPORTING;
    private static final VoxelShape[] SHAPE_BY_AGE;

    public CornBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(AGE, 0)).with(SUPPORTING, false));
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (worldIn.isAreaLoaded(pos, 1)) {
            if (worldIn.getLightSubtracted(pos.up(), 0) >= 6) {
                int age = this.getAge(state);
                if (age <= this.getMaxAge()) {
                    float chance = 10.0F;
                    if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / chance) + 1) == 0)) {
                        if (age == this.getMaxAge()) {
                            CornUpperBlock cornUpper = (CornUpperBlock) ModBlocks.CORN_UPPER.get();
                            if (cornUpper.getDefaultState().isValidPosition(worldIn, pos.up()) && worldIn.isAirBlock(pos.up())) {
                                worldIn.setBlockState(pos.up(), cornUpper.getDefaultState());
                                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                            }
                        } else {
                            worldIn.setBlockState(pos, this.withAge(age + 1), 2);
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                        }
                    }
                }
            }

        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_AGE[(Integer)state.get(this.getAgeProperty())];
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    protected int getAge(BlockState state) {
        return (Integer)state.get(this.getAgeProperty());
    }

    public int getMaxAge() {
        return 3;
    }

    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack((IItemProvider) ModItems.CORN_KERNELS.get());
    }

    public BlockState withAge(int age) {
        return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
    }

    public boolean isMaxAge(BlockState state) {
        return (Integer)state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE, SUPPORTING});
    }

    public boolean isSupportingCornUpper(BlockState topState) {
        return topState.getBlock() == net.ncpbails.culturaldelights.block.ModBlocks.CORN_UPPER.get();
    }


    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        BlockState upperState = worldIn.getBlockState(pos.up());
        if (upperState.getBlock() instanceof CornUpperBlock) {
            return !((CornUpperBlock)upperState.getBlock()).isMaxAge(upperState);
        } else {
            return true;
        }
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 1, 4);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(worldIn), 7);
        if (ageGrowth <= this.getMaxAge()) {
            worldIn.setBlockState(pos, (BlockState)state.with(AGE, ageGrowth));
        } else {
            BlockState top = worldIn.getBlockState(pos.up());
            if (top.getBlock() == net.ncpbails.culturaldelights.block.ModBlocks.CORN_UPPER.get()) {
                IGrowable growable = (IGrowable)worldIn.getBlockState(pos.up()).getBlock();
                if (growable.canGrow(worldIn, pos.up(), top, false)) {
                    growable.grow(worldIn, worldIn.rand, pos.up(), top);
                }
            } else {
                CornUpperBlock cornUpper = (CornUpperBlock) ModBlocks.CORN_UPPER.get();
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (cornUpper.getDefaultState().isValidPosition(worldIn, pos.up()) && worldIn.isAirBlock(pos.up())) {
                    worldIn.setBlockState(pos, (BlockState)state.with(AGE, this.getMaxAge()));
                    worldIn.setBlockState(pos.up(), (BlockState)cornUpper.getDefaultState().with(CornUpperBlock.CORN_AGE, remainingGrowth), 2);
                }
            }
        }

    }

    static {
        AGE = BlockStateProperties.AGE_0_3;
        SUPPORTING = BooleanProperty.create("supporting");
        SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D), Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D), Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)};
    }
}
