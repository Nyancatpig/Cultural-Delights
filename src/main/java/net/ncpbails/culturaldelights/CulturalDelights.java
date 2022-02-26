package net.ncpbails.culturaldelights;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.AxeItem;
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
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        RenderTypeLookup.setRenderLayer(ModBlocks.BAMBOO_MAT.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WILD_CUCUMBERS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WILD_CORN.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_LEAF_CARPET.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_SAPLING.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.AVOCADO_PIT.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CUCUMBERS.get(), RenderType.getCutoutMipped());


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
