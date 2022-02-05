package net.ncpbails.culturaldelights.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.block.ModBlocks;

public class ModTileEntities {

    public  static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CulturalDelights.MOD_ID);

    //tile entities                                                                                             tile id
    public static RegistryObject<TileEntityType<BambooMatTile>> BAMBOO_MAT_TILE = TILE_ENTITIES.register("bamboo_mat_tile",
            () -> TileEntityType.Builder.create(BambooMatTile::new, ModBlocks.BAMBOO_MAT.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
