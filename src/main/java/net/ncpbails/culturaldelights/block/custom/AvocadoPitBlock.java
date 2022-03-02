package net.ncpbails.culturaldelights.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class AvocadoPitBlock extends SaplingBlock {

    private static final VoxelShape SHAPE_PIT = Stream.of(
            Block.makeCuboidShape(6, 0, 6, 10, 3, 10)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_PIT;

    }

    public AvocadoPitBlock(Tree treeIn, Properties properties) {
        super(treeIn, properties);
    }
}
