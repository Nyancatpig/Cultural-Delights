package net.ncpbails.culturaldelights;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.container.ModContainers;
import net.ncpbails.culturaldelights.data.recipes.ModRecipeTypes;
import net.ncpbails.culturaldelights.item.ModItems;
import net.ncpbails.culturaldelights.screen.BambooMatScreen;
import net.ncpbails.culturaldelights.tileentity.ModTileEntities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.mixin.accessors.ChickenEntityAccessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CulturalDelights.MOD_ID)
public class CulturalDelights
{
    public static final String MOD_ID = "culturaldelights";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public CulturalDelights() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModTileEntities.register((eventBus));
        ModContainers.register((eventBus));
        ModRecipeTypes.register((eventBus));

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            AxeItem.BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.BLOCK_STRIPPING_MAP)
                .put(ModBlocks.AVOCADO_LOG.get(), Blocks.STRIPPED_JUNGLE_LOG)
                .put(ModBlocks.AVOCADO_WOOD.get(), Blocks.STRIPPED_JUNGLE_WOOD).build();
        });

        event.enqueueWork(() -> {
            registerCompostables();
            List<ItemStack> chickenFood = new ArrayList();
            Collections.addAll(chickenFood, ChickenEntityAccessor.getFoodItems().getMatchingStacks());
            chickenFood.add(new ItemStack((IItemProvider) ModItems.CUCUMBER_SEEDS.get()));
            chickenFood.add(new ItemStack((IItemProvider) ModItems.CORN_KERNELS.get()));
            chickenFood.add(new ItemStack((IItemProvider) ModItems.EGGPLANT_SEEDS.get()));
            chickenFood.add(new ItemStack((IItemProvider) ModItems.WHITE_EGGPLANT_SEEDS.get()));
            ChickenEntityAccessor.setFoodItems(Ingredient.fromStacks(chickenFood.stream()));
        });
    }

        public static void registerCompostables() {
            // 30% chance
            ComposterBlock.CHANCES.put(ModItems.CUCUMBER_SEEDS.get(), 0.3F);
            ComposterBlock.CHANCES.put(ModItems.CORN_KERNELS.get(), 0.3F);
            ComposterBlock.CHANCES.put(ModItems.EGGPLANT_SEEDS.get(), 0.3F);
            ComposterBlock.CHANCES.put(ModItems.WHITE_EGGPLANT_SEEDS.get(), 0.3F);
            ComposterBlock.CHANCES.put(ModBlocks.AVOCADO_PIT.get(), 0.3F);

            // 50% chance
            ComposterBlock.CHANCES.put(ModItems.CUT_CUCUMBER.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModItems.CUT_AVOCADO.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModItems.CUT_EGGPLANT.get(), 0.65F);

            // 65% chance
            ComposterBlock.CHANCES.put(ModItems.AVOCADO.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModItems.CUCUMBER.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModItems.CORN_COB.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModItems.EGGPLANT.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModItems.WHITE_EGGPLANT.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModBlocks.WILD_CUCUMBERS.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModBlocks.WILD_CORN.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModBlocks.WILD_EGGPLANTS.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModBlocks.AVOCADO_LEAVES.get(), 0.65F);
            ComposterBlock.CHANCES.put(ModBlocks.AVOCADO_SAPLING.get(), 0.65F);

            // 85% chance
            ComposterBlock.CHANCES.put(ModItems.POPCORN.get(), 0.85F);

            // 100% chance
            ComposterBlock.CHANCES.put(ModBlocks.AVOCADO_BUNDLE.get(), 0.65F);
        }



    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        RenderTypeLookup.setRenderLayer(ModBlocks.BAMBOO_MAT.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WILD_CUCUMBERS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WILD_CORN.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WILD_EGGPLANTS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_LEAVES.get(), RenderType.getCutoutMipped());
        //RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_LEAF_CARPET.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_SAPLING.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_PIT.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CUCUMBERS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.EGGPLANTS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WHITE_EGGPLANTS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CORN.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CORN_UPPER.get(), RenderType.getCutoutMipped());


        ScreenManager.registerFactory(ModContainers.BAMBOO_MAT_CONTAINER.get(), BambooMatScreen::new);
    }



    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
