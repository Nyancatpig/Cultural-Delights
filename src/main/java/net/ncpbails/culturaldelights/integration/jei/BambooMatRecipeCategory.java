package net.ncpbails.culturaldelights.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.data.recipes.BambooMatRecipe;

public class BambooMatRecipeCategory implements IRecipeCategory<BambooMatRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(CulturalDelights.MOD_ID, "mat_rolling");
    public final static ResourceLocation TEXTURE = new ResourceLocation(CulturalDelights.MOD_ID, "textures/gui/bamboo_mat_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public BambooMatRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.BAMBOO_MAT.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends BambooMatRecipe> getRecipeClass() {
        return BambooMatRecipe.class;
    }

    @Override
    public String getTitle() {
        return ModBlocks.BAMBOO_MAT.get().getTranslatedName().getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(BambooMatRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BambooMatRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 38, 45);
        recipeLayout.getItemStacks().init(1, true, 56, 45);
        recipeLayout.getItemStacks().init(2, true, 29, 21);
        recipeLayout.getItemStacks().init(3, true, 47, 21);
        recipeLayout.getItemStacks().init(4, true, 65, 21);

        recipeLayout.getItemStacks().init(5, false, 128, 21);
        recipeLayout.getItemStacks().set(ingredients);
    }
}
