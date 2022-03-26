package net.ncpbails.culturaldelights.world;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.item.ModItems;
import net.ncpbails.culturaldelights.world.gen.ModPlantGeneration;
import net.ncpbails.culturaldelights.world.gen.ModTreeGeneration;
import vectorwing.farmersdelight.mixin.accessors.ChickenEntityAccessor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = CulturalDelights.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModTreeGeneration.generateTrees(event);
        ModPlantGeneration.generatePlants(event);
    }


}
