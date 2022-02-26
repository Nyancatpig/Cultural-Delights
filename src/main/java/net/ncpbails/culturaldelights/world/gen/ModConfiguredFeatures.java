package net.ncpbails.culturaldelights.world.gen;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.world.gen.treedecorator.AvocadoBundleTreeDecorator;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> AVOCADO =
            register("avocado", Feature.TREE.withConfiguration((
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(ModBlocks.AVOCADO_LOG.get().getDefaultState()),
                            new SimpleBlockStateProvider(ModBlocks.AVOCADO_LEAVES.get().getDefaultState()),
                            new AcaciaFoliagePlacer(FeatureSpread.create(3), FeatureSpread.create(0)),
                            new StraightTrunkPlacer(3, 2, 0),
                            new TwoLayerFeature(1, 0, 1)))
                    .setDecorators(ImmutableList.of(new AvocadoBundleTreeDecorator(1))).setIgnoreVines().build()));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> AVOCADOPIT =
            register("avocadopit", Feature.TREE.withConfiguration((
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(ModBlocks.AVOCADO_SAPLING.get().getDefaultState()),
                            new SimpleBlockStateProvider(ModBlocks.AVOCADO_SAPLING.get().getDefaultState()),
                            new AcaciaFoliagePlacer(FeatureSpread.create(0), FeatureSpread.create(0)),
                            new StraightTrunkPlacer(1, 1, 0),
                            new TwoLayerFeature(1, 0, 1))).build()));

    public static final ConfiguredFeature<?, ?> WILD_CUCUMBERS_CONFIG = Feature.FLOWER.withConfiguration((
            new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.WILD_CUCUMBERS.get().getDefaultState()), SimpleBlockPlacer.PLACER))
            .tries(1).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(3);

    public static final ConfiguredFeature<?, ?> WILD_CORN_CONFIG = Feature.FLOWER.withConfiguration((
            new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.WILD_CORN.get().getDefaultState()), SimpleBlockPlacer.PLACER))
            .tries(3).build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(20);

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

}