package net.ncpbails.culturaldelights.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.data.recipes.BambooMatRecipe;
import net.ncpbails.culturaldelights.data.recipes.ModRecipeTypes;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class CulturalDelightsJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CulturalDelights.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new BambooMatRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().world).getRecipeManager();
        registration.addRecipes(rm.getRecipesForType(ModRecipeTypes.MAT_ROLLING_RECIPE).stream()
                        .filter(r -> r instanceof BambooMatRecipe).collect(Collectors.toList()),
                BambooMatRecipeCategory.UID);
    }
}
