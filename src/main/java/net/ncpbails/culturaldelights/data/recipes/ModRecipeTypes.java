package net.ncpbails.culturaldelights.data.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.ncpbails.culturaldelights.CulturalDelights;

public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CulturalDelights.MOD_ID);

    //                                                                                                                      recipe id
    public static final RegistryObject<BambooMatRecipe.Serializer> MAT_ROLLING_SERIALIZER = RECIPE_SERIALIZER.register("mat_rolling", BambooMatRecipe.Serializer::new);
    public static IRecipeType<BambooMatRecipe> MAT_ROLLING_RECIPE = new BambooMatRecipe.MatRollingRecipeType();


    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, BambooMatRecipe.TYPE_ID, MAT_ROLLING_RECIPE);
    }

}
