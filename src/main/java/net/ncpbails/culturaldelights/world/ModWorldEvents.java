package net.ncpbails.culturaldelights.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.world.gen.ModPlantGeneration;
import net.ncpbails.culturaldelights.world.gen.ModTreeGeneration;

@Mod.EventBusSubscriber(modid = CulturalDelights.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModTreeGeneration.generateTrees(event);
        ModPlantGeneration.generatePlants(event);
    }

}
