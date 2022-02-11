package net.ncpbails.culturaldelights.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.ncpbails.culturaldelights.block.ModBlocks;
import net.ncpbails.culturaldelights.item.ModItems;

import javax.annotation.Nullable;

public class BambooMatRecipe implements IBambooMatRecipe{

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public BambooMatRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }


    @Override
    public boolean matches(IInventory inv, World worldIn) {
        // Checks for correct focus
            if(recipeItems.get(0).test(inv.getStackInSlot(0))) {
                if(recipeItems.get(1).test(inv.getStackInSlot(1))) {
                    if(recipeItems.get(2).test(inv.getStackInSlot(2))) {
                        if(recipeItems.get(3).test(inv.getStackInSlot(3))) {
                            return recipeItems.get(4).test(inv.getStackInSlot(4));
                        }
                    }
                }
            }
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.MAT_ROLLING_SERIALIZER.get();
    }

    public static class MatRollingRecipeType implements IRecipeType<BambooMatRecipe> {
        @Override
        public String toString() {
            return BambooMatRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<BambooMatRecipe> {

        @Override
        public BambooMatRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));

            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(5, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.deserialize(ingredients.get(i)));
            }

            return new BambooMatRecipe(recipeId, output, inputs);
        }

        @Nullable
        @Override
        public BambooMatRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.read(buffer));
            }

            ItemStack output = buffer.readItemStack();
            return new BambooMatRecipe(recipeId, output, inputs);
        }

        @Override
        public void write(PacketBuffer buffer, BambooMatRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buffer);
            }
            buffer.writeItemStack(recipe.getRecipeOutput(), false);
        }
    }

}
