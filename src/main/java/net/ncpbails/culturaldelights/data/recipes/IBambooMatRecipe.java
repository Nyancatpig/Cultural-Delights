package net.ncpbails.culturaldelights.data.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.ncpbails.culturaldelights.CulturalDelights;

public interface IBambooMatRecipe extends IRecipe<IInventory> {

    ResourceLocation TYPE_ID = new ResourceLocation(CulturalDelights.MOD_ID, "mat_rolling");

    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    @Override
    default boolean isDynamic(){
        return true;
    }


}
