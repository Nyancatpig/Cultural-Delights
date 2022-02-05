package net.ncpbails.culturaldelights.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.block.custom.BambooMatBlock;
import net.ncpbails.culturaldelights.item.ModItemGroup;
import net.ncpbails.culturaldelights.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CulturalDelights.MOD_ID);

    //blocks                                                                        block id
    public static final RegistryObject<Block> AVOCADO_BUNDLE = registerBlock("avocado_bundle",
            () -> new Block(AbstractBlock.Properties.create(Material.GOURD)
                    .harvestLevel(0).harvestTool(ToolType.HOE).hardnessAndResistance(1f)
                    .sound(SoundType.WOOD)));

    public static final RegistryObject<Block> BAMBOO_MAT = registerBlock("bamboo_mat",
            () -> new BambooMatBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .harvestLevel(0).harvestTool(ToolType.AXE).hardnessAndResistance(1f)
                    .sound(SoundType.BAMBOO)));



    //MAKE ITEMS FROM BLOCKS AUTOMATICALLY
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.CULTURAL_GROUP)));
    }




    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
